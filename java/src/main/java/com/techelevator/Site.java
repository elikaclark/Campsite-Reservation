package com.techelevator;

public class Site {

	private long siteid;
	private long campGroundid;
	private long siteNumber;
	private long maxOccupancy;
	private boolean accessible;
	private long maxRVLength;
	private boolean utilities;
	
	
	public long getSiteid() {
		return siteid;
	}
	public void setSiteid(long siteid) {
		this.siteid = siteid;
	}
	public long getCampGroundid() {
		return campGroundid;
	}
	public void setCampGroundid(long campGroundid) {
		this.campGroundid = campGroundid;
	}
	public long getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(long siteNumber) {
		this.siteNumber = siteNumber;
	}
	public long getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public long getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(long maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	
	
	
	
	
}
