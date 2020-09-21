package com.techelevator;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAOInterface {

	private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;
	LocalDate localdate;

	public JDBCCampgroundDAO(DataSource ds) {
		this.dataSource = ds;
	}

	@Override
	public List<Campground> getAllCampgrounds() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Campground> campgroundList = new ArrayList<Campground>();
		String sqlCampground = "SELECT * FROM campground";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampground);
		while (results.next()) {
			
//			long resultCampId = results.getLong("campground_id");
//			long resultParkId = results.getLong("park_id");
//			String resultName = results.getString("name");
//			int resultOpenFrom = results.getInt("open_from_mm");
//			int resultOpenTo = results.getInt("open_to_mm");
//			double resultFee = results.getDouble("daily_fee");

			//Campground campground = new Campground();
         Campground campground = mapRowToCampground(results);
//			campground.setCampgroundId(resultCampId);
//			campground.setParkId(resultParkId);
//			campground.setName(resultName);
//			campground.setOpenFrom(resultOpenFrom);
//			campground.setOpenTo(resultOpenTo);
//			campground.setFee(resultFee);

			campgroundList.add(campground);

		}



		// TODO Auto-generated method stub

			// TODO Auto-generated method stub

		return campgroundList;
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground campground;
		campground = new Campground();
		
		campground.setCampgroundId(results.getLong("campground_id"));
		campground.setParkId(results.getLong("park_id"));
		campground.setName(results.getString("name"));
		campground.setOpenFrom(results.getInt("open_from_mm"));
		campground.setOpenTo(results.getInt("open_to_mm"));
		campground.setFee(results.getDouble("daily_fee"));
        return campground;
		
	}

public String toString(Campground campground) {
	
	String campDetailsString = campground.getName() + "\t" + "  " + Month.of(campground.getOpenFrom())+ "  " + Month.of(campground.getOpenTo()) + "  " + campground.getFee();
	return campDetailsString;
	
}
//	@Override
//	public List<Campground> campgroundByPark(long parkID) {
//		List<Campground> allCampgrounds = new ArrayList<Campground>();
//		String sqlAllCamps = "SELECT * from campground C join park P on P.park_id = c.park_id WHERE c.park_id = ?";
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllCamps, parkID);
//
//		while (results.next()) {
//			Campground campground = new Campground();
//			allCampgrounds.add(campground);
//		}
//
//		return allCampgrounds;
//
//	}

}
