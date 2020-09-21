package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JBDCSiteDAO implements SiteInterface {

	DataSource dataSource;
	private NamedParameterJdbcTemplate jdbcSpecial;
	
	public JBDCSiteDAO(DataSource dataSource) {

		this.dataSource = dataSource;
		jdbcSpecial = new NamedParameterJdbcTemplate(dataSource);
	}

	
	@Override
	public List<Site> getAllSites(long campGroundid, String fromDate, String toDate) {
		
		NamedParameterJdbcTemplate specialTemplate = new NamedParameterJdbcTemplate(dataSource);

		int arrivalYear = Integer.parseInt(fromDate.substring(0, 4));
		int arrivalMonth = Integer.parseInt(fromDate.substring(4, 6));
		int arrivalDay = Integer.parseInt(fromDate.substring(6));

		int depYear = Integer.parseInt(toDate.substring(0, 4));
		int depMonth = Integer.parseInt(toDate.substring(4, 6));
		int depDay = Integer.parseInt(toDate.substring(6));

		Set<LocalDate> dates = new HashSet<LocalDate>();
		dates.add(LocalDate.of(arrivalYear, arrivalMonth, arrivalDay));
		dates.add(LocalDate.of(depYear, depMonth, depDay));

		Set<Long> anId = new HashSet<Long>();
		anId.add(campGroundid);

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("dates", dates);
		parameters.addValue("id", anId);
		
		
		String sqlSite = "SELECT * FROM site WHERE campground_id = :id AND site_id " + 
				"NOT IN (SELECT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS ( :dates )) LIMIT 5";
				
		ArrayList<Site> availableSites = new ArrayList<Site>();

		SqlRowSet results = specialTemplate.queryForRowSet(sqlSite, parameters);

		while (results.next()) {
			Site theSite = mapRowToSite(results);
			availableSites.add(theSite);
		}
		return availableSites;
		

	}

	@Override
	public Site getSiteById(long siteId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Site site = null;
		String sqlGetSiteById = "SELECT * FROM site WHERE site_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetSiteById, siteId);
		if (results.next()) {
			site = mapRowToSite(results);
		}

		return site;
	}

	public List<Site> getPopularSites(long campgroundId, String fromDate, String toDate) {
		NamedParameterJdbcTemplate specialTemplate = new NamedParameterJdbcTemplate(dataSource);

		int arrivalYear = Integer.parseInt(fromDate.substring(0, 4));
		int arrivalMonth = Integer.parseInt(fromDate.substring(4, 6));
		int arrivalDay = Integer.parseInt(fromDate.substring(6));

		int depYear = Integer.parseInt(toDate.substring(0, 4));
		int depMonth = Integer.parseInt(toDate.substring(4, 6));
		int depDay = Integer.parseInt(toDate.substring(6));

		Set<LocalDate> dates = new HashSet<LocalDate>();
		dates.add(LocalDate.of(arrivalYear, arrivalMonth, arrivalDay));
		dates.add(LocalDate.of(depYear, depMonth, depDay));

		Set<Long> anId = new HashSet<Long>();
		anId.add(campgroundId);

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("dates", dates);
		parameters.addValue("id", anId);

		String sqlSelect = "SELECT * FROM site " + "JOIN reservation ON reservation.site_id = site.site_id " +
				"WHERE campground_id = :id AND reservation.site_id NOT IN " +
				"(SELECT reservation.site_id " +
				"FROM reservation WHERE (from_date, to_date) OVERLAPS ( :dates )) " +
				"GROUP BY site.site_id, reservation.reservation_id " +
				"ORDER BY COUNT(reservation.site_id) DESC " +
				"LIMIT 5";

		
		ArrayList<Site> availableSites = new ArrayList<Site>();

		SqlRowSet results = specialTemplate.queryForRowSet(sqlSelect, parameters);

		while (results.next()) {
			Site theSite = mapRowToSite(results);
			availableSites.add(theSite);
		}
		return availableSites;
	}

	
	
	private Site mapRowToSite(SqlRowSet results) {
		Site site;
		site = new Site();
		site.setSiteid(results.getLong("site_id"));
		site.setCampGroundid(results.getLong("campground_id"));
		site.setSiteNumber(results.getLong("site_number"));
		site.setMaxOccupancy(results.getLong("max_occupancy"));
		site.setAccessible(results.getBoolean("accessible"));
		site.setMaxRVLength(results.getLong("max_rv_length"));
		site.setUtilities(results.getBoolean("utilities"));

		return site;
	}
}



//"SELECT site.site_id, site.site_number, max_occupancy, accessible, max_rv_length, utilities, "
//+ "COUNT(reservation_id ) AS reservation_count from site "
//+ "JOIN reservation ON reservation.site_id = site.site_id "
//+ "JOIN campground ON site.campground_id = campground.campground_id "
//+ "WHERE campground.campground_id = :id AND (site.site_id NOT IN (SELECT reservation.site_id FROM reservation WHERE (from_date, to_date) "
//+ "OVERLAPS ( :dates ))) "
//+ "GROUP BY site.site_id "
//+ "ORDER BY reservation_count DESC "
//+ "LIMIT 5";


//"SELECT s.*, (COUNT(r.site_id)) from reservation r "
//+ "JOIN site s on r.site_id = s.site_id JOIN campground c on s.campground_id = c.campground_id "
//+ "WHERE s.campground_id = :id AND (s.site_id NOT IN (SELECT r.site_id FROM reservation WHERE (from_date, to_date)"
//+ "OVERLAPS ( :dates ))) " + "GROUP BY r.site_id, s.site_id " + "ORDER BY COUNT desc " + "LIMIT 5";

//"SELECT d.park_id, s.campground_id , s.site_id, count(*) AS the_count"
//+ "from reservation r "
//+ "JOIN site s on r.site_id = s.site_id "
//+ "JOIN campground c on s.campground_id = c.campground_id "
//+ "JOIN park d on c.park_id = d.park_id"
//+ "WHERE c.campground_id = :id AND s.site_id NOT IN ("
//+ "SELECT site_id FROM reservation"
//+ "WHERE (from_date, to_date)"
//+ "OVERLAPS ( :dates )) " 
//+ "GROUP BY d.park_id, s.campground_id, s.site_id"
//+ "ORDER BY the_count DESC"
//+ "LIMIT 5";


//"SELECT * FROM site WHERE campground_id = :id AND site_id "
//+ "NOT IN (SELECT site_id FROM reservation WHERE (from_date, to_date) OVERLAPS ( :dates ) )";


//"SELECT s.*, (COUNT(r.site_id)) from reservation r "
//+ "JOIN site s on r.site_id = s.site_id JOIN campground c on s.campground_id = c.campground_id "
//+ "WHERE s.campground_id = :id AND (s.site_id NOT IN (SELECT r.site_id FROM reservation WHERE (from_date, to_date)"
//+ "OVERLAPS ( :dates ))) " + "GROUP BY r.site_id, s.site_id " + "ORDER BY COUNT desc " + "LIMIT 5";