package com.techelevator;

import java.time.LocalDate;

public class Campground {

	private long campgroundId;
	private long parkId;
	private String name;
	private int openFromMonth;
	private int openToMonth;
	private double fee;
	
	
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenFrom() {
		return openFromMonth;
	}
	public void setOpenFrom(int openFrom) {
		this.openFromMonth = openFrom;
	}
	public int getOpenTo() {
		return openToMonth;
	}
	public void setOpenTo(int openTo) {
		this.openToMonth = openTo;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	
	
	
}
