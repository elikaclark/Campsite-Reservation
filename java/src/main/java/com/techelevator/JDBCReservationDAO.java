package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationInterface {
	
	private NamedParameterJdbcTemplate jdbcSpecial;
	DataSource dataSource;
	
	JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	

	@Override
	public List<Reservation> getAllReservations() {
		
		List<Reservation> reservationList = new ArrayList<Reservation>();
		String sqlReservation = "SELECT * FROM reservation";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReservation);
		while (results.next()) {
			long resultResId = results.getLong("reservation_id");
			long restultSiteId = results.getLong("site_id");
			String resultName = results.getString("name");
			String fromDate = results.getString("from_date");
			String toDate = results.getString("to_date");
			String createDate = results.getString("create_date");

			Reservation newReservation = new Reservation();

			newReservation.setReservationid(resultResId);
			newReservation.setSiteid(restultSiteId);
			newReservation.setName(resultName);
			newReservation.setFromDate(resultName);
			newReservation.setToDate(resultName);
			newReservation.setCreateDate(createDate);

			reservationList.add(newReservation);

		}

		// TODO Auto-generated method stub
		return reservationList;

	}

	@Override
	public Long newReservation(long siteid, String name, String fromDate, String toDate) {
	
		// convert from date to localdate
		LocalDate arrivalDate = LocalDate.parse(fromDate);
		
		
		//convert todate to localdate
		LocalDate deptDate = LocalDate.parse(toDate);
		
		Long reservationid = (long)0;
		String sqlInsertReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES (?, ?, ?, ?, CURRENT_DATE) RETURNING reservation_id";
			
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlInsertReservation, siteid, name, arrivalDate, deptDate);
		
		if (result.next()) {
			reservationid = result.getLong("reservation_id");
			
		}
		return reservationid;// TODO Auto-generated method stub
	}


	@Override
	public Reservation searchResAvailability(String selectedCampground, String fromDate, String toDate) {
		Reservation reservationB = new Reservation();
		
		String sqlSearchResAvail = "  ";
		// need to build this out

		return reservationB;

		// TODO Auto-generated method stub

	}

	public Reservation getReservationbyID(long reservationid) {
		Reservation reservationA = new Reservation();
		
		String sqlGetReservationById = "SELECT * FROM reservation WHERE reservation_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationById, reservationid);

		while (results.next()) {
			reservationA = mapRowToReservation(results);
			// throw new Error("Did not find expected result");
		}

		return reservationA;
	}

	private Reservation mapRowToReservation(SqlRowSet result) {
		Reservation reservation = new Reservation();

		reservation.setReservationid(result.getLong("reservation_id"));
		reservation.setSiteid(result.getLong("site_id"));
		reservation.setName(result.getString("name"));
		reservation.setFromDate(result.getString("from_date"));
		reservation.setToDate(result.getString("to_date"));
		reservation.setCreateDate(result.getString("create_date"));

		return reservation;
	}


}
