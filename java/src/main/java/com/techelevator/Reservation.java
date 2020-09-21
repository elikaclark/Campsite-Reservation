package com.techelevator;



public class Reservation {

	private long reservationid;
	private long siteid;
	private String name;
	private String fromDate;
	private String toDate;
	private String createDate;
	
	
	public long getReservationid() {
		return reservationid;
	}
	public void setReservationid(long reservationid) {
		this.reservationid = reservationid;
	}
	public long getSiteid() {
		return siteid;
	}
	public void setSiteid(long siteid) {
		this.siteid = siteid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
}
