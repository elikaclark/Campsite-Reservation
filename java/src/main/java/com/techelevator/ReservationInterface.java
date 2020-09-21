package com.techelevator;

import java.util.List;

public interface ReservationInterface {

	
	public List<Reservation> getAllReservations();
	public Long newReservation(long siteid, String name, String fromDate, String toDate);
	public Reservation searchResAvailability(String selectedCampground, String fromDate, String toDate);
	public Reservation getReservationbyID(long reservationid);

	
}
