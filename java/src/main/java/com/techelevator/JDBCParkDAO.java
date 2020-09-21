package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAOInterface {


	
	DataSource dataSource ;
	List<Park> parkList = new ArrayList<Park>();
	
	
	
	//constructor
	public JDBCParkDAO(DataSource ds) {
		this.dataSource = ds;
	}
	
	@Override
	public List<Park> getAllParks() {
		
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	String sqlPark = "SELECT * FROM park";
	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlPark);
	while (results.next()) {
		long resultId = results.getLong("park_id");
		String resultName = results.getString("name");
		String resultLocation = results.getString("location");
		String resultEstabDate = results.getString("establish_date");
		long resultArea = results.getLong("area");
		long resultVisitor = results.getLong("visitors");
		String resultDescription = results.getString("description");
		
		Park park = new Park();
		
		park.setId(resultId);
		park.setName(resultName);
		park.setLocation(resultLocation);
		park.setEstablishedDate(resultEstabDate);
		park.setArea(resultArea);
		park.setAnnualVisitorCount(resultVisitor);
		park.setDescription(resultDescription);  
		
        parkList.add(park);
	
		
	}
		return parkList;
	}

	public void displayParks() {
		//System.out.println(String.format("%10s %20s %13s %12s %12s", "Slot", "Name", "Price", "Type", "Inventory\n"));

		for (Park park : parkList) {
			System.out.println(park.getName());
		}
	}
	
	
}
