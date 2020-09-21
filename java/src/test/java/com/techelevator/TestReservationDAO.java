package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class TestReservationDAO {

	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	private static JDBCReservationDAO dao;

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;

	
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
		dao = new JDBCReservationDAO(dataSource);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}

	@Test
	public void getAllReservationsTest() {
	
		List<Reservation> reservation = dao.getAllReservations();
		SqlRowSet rowCount = jdbcTemplate.queryForRowSet("SELECT COUNT(reservation_id) FROM reservation");
		int rowCountInt = 0;

		if (rowCount.next()) {
			rowCountInt = rowCount.getInt("count");
		}

		assertEquals(reservation.size(), rowCountInt);
		
	}
	
	@Test 
	public void reservationBYIDTest() {
		
	Reservation testReservation = dao.getReservationbyID(22);
	
	String sqlInsert = "SELECT * from reservation where reservation_id = 22";
	
	SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT name from reservation where reservation_id = 22");
	String name = null;
	
	if(results.next()) {
		name = results.getString("name");
	}
	assertEquals(testReservation.getName(), name);
	
	}
	
	@Test
	public void newReservationTest() {
	List<Reservation> allreservations = dao.getAllReservations();
	
	Reservation testReservation = new Reservation();
	testReservation.setSiteid(15);
	testReservation.setName("TESTING IS SO FUNNNN");
	testReservation.setFromDate("2020-01-10");
	testReservation.setToDate("2020-01-10");
	dao.newReservation(testReservation.getSiteid(), testReservation.getName(), testReservation.getFromDate(), testReservation.getToDate());
	List<Reservation> newreservation = dao.getAllReservations();		
	assertEquals(allreservations.size() + 1 , newreservation.size());
	}
	
	
}
