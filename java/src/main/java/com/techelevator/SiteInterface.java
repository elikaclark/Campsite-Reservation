package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface SiteInterface {

	public List<Site> getAllSites(long campGroundid, String fromDate, String toDate);
	public List<Site> getPopularSites(long campGroundid, String fromDate, String toDate);
	public Site getSiteById(long siteId);
}
