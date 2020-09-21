package com.techelevator;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;

import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.davidmoten.text.utils.WordWrap;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.view.Menu;

public class CampgroundCLI {


	private static final String PARK_OPTIONS_ACADIA = "Acadia";

	private static final String PARK_OPTIONS_ARCHES = "Arches";

	private static final String PARK_OPTIONS_CUYAHOG = "Cuyahoga Valley";

	private static final String PARK_OPTIONS_QUIT = "QUIT";

	private static final String[] PARK_OPTIONS = { PARK_OPTIONS_ACADIA, PARK_OPTIONS_ARCHES, PARK_OPTIONS_CUYAHOG,

			PARK_OPTIONS_QUIT };

	private static final String VIEW_CAMPGROUNDS = "View Campgrounds";

	private static final String SEARCH_RESERVATION = "Seach for Reservation";

	private static final String PREVIOUS_SCREEN = "Return to Previous Screen";

	private static final String[] MENU_OPTIONS = { VIEW_CAMPGROUNDS, SEARCH_RESERVATION, PREVIOUS_SCREEN };

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Select a Park for Further Details";

	private static final String AVAILABLE_RESERVATIONS = "Search for Available Reservations";

	private static final String[] RESERVATION_MENU = { AVAILABLE_RESERVATIONS, PREVIOUS_SCREEN };

	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_PURCHASE };

	private static final String[] PURCHASE_MENU = { "Feed Money", "Select Product", "Finish Transaction", "Back" };

	private static final String[] MONEY_MENU = { "$1 Bill", "$2 Bill", "$5 Bill", "$10 Bill", "Back" };

	private static final String CAMPGROUND_MENU_OPTIONS_ALL_CAMPGROUNDS = null;
	private static final String CAMPGROUND_MENU_OPTIONS_AVAILABLE_RESERVATIONS = null;
	private static final String CAMPGROUND_MENU_OPTIONS_ALL_CAMPGROUNDS_BACK = null;
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_OPTIONS_ALL_CAMPGROUNDS,
			CAMPGROUND_MENU_OPTIONS_AVAILABLE_RESERVATIONS, CAMPGROUND_MENU_OPTIONS_ALL_CAMPGROUNDS_BACK };

	private Menu menu = new Menu(System.in, System.out);

	private JDBCCampgroundDAO jdbcCampgroundDAO;

	private JDBCParkDAO jdbcParkDAO;

	private JBDCSiteDAO jdbcSiteDAO;

	private JDBCReservationDAO jdbcReservationDAO;
	
// Park parks = new Park();

// Campground campground = new Campground();

	public static void main(String[] args) {

		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");

		dataSource.setUsername("postgres");

		dataSource.setPassword("postgres1");


	

		CampgroundCLI application = new CampgroundCLI(dataSource);

		application.run();

	
	}

	public CampgroundCLI(DataSource datasource) {

// create your DAOs here

		jdbcParkDAO = new JDBCParkDAO(datasource);

		jdbcCampgroundDAO = new JDBCCampgroundDAO(datasource);

		jdbcSiteDAO = new JBDCSiteDAO(datasource);

		jdbcReservationDAO = new JDBCReservationDAO(datasource);

	}

	public void run() {

		while (true) {

			System.out.println("Select a Park for Further Details\n");

			String choice = (String) menu.getChoiceFromOptions(PARK_OPTIONS);
			//If statement to caputre the selected Park to pass throughout application
			if (choice.equals(PARK_OPTIONS_ACADIA)) {

				Park chosenPark = displayParksInfo(PARK_OPTIONS_ACADIA);

				handleCampground(chosenPark);

			} else if (choice.equals(PARK_OPTIONS_ARCHES)) {

				Park chosenPark = displayParksInfo(PARK_OPTIONS_ARCHES);
				handleCampground(chosenPark);

			} else if (choice.equals(PARK_OPTIONS_CUYAHOG)) {

				Park chosenPark = displayParksInfo(PARK_OPTIONS_CUYAHOG);
				handleCampground(chosenPark);

			} else if (choice.equals(PARK_OPTIONS_QUIT)) {

				System.exit(0);

			}

		}

	}

	public Park displayParksInfo(String parkName) {

		List<Park> allParks = jdbcParkDAO.getAllParks();
// declaring new park
		Park newPark = new Park();
		for (Park parks : allParks) {

			if (parkName.equals(parks.getName())) {
// stores the answer I get from loop
				newPark = parks;

			}

		}
		
		String desc = newPark.getDescription();
		String wrapped = WordWrap.from(desc).maxWidth(100).insertHyphens(true).wrap();
			
		System.out.println(newPark.getName() + " National Park\n" + "Location: " + newPark.getLocation() + "\n"

				+ "Established: " + newPark.getEstablishedDate() + "\n" + "Area: " + newPark.getArea() + "\n"

				+ "Annual Visitors: " + newPark.getAnnualVisitorCount() + "\n" + "Description: "

				+ wrapped);

		return newPark;
	}

	public List<Campground> handleCampground(Park chosenPark) {


	
		List<Campground> campgroundsList = jdbcCampgroundDAO.getAllCampgrounds();
		
		List<Campground> selectedCampGrounds = new ArrayList<Campground>();
		
		for (Campground campground : campgroundsList) {

			if (chosenPark.getId() == campground.getParkId()) {

				selectedCampGrounds.add(campground);
			}

		}


		displayCampgrounds(selectedCampGrounds);
		return selectedCampGrounds;

	}

	public Campground displayCampgrounds(List<Campground> selectedCampGrounds) {
		Campground choosenCampground = new Campground();
		String choice = (String) menu.getChoiceFromOptions(MENU_OPTIONS);

		if (choice.equals(VIEW_CAMPGROUNDS)) {

			String[] CAMP_ARRAY = new String[selectedCampGrounds.size()];

			for (int i = 0; i < selectedCampGrounds.size(); i++) {
				String details = jdbcCampgroundDAO.toString(selectedCampGrounds.get(i));
				CAMP_ARRAY[i] = details;

			}

			String choice1 = (String) menu.getChoiceFromOptions(CAMP_ARRAY);
			String[] nameArray = choice1.split("\\t");
			String nameOnly = nameArray[0];

			for (Campground camp : selectedCampGrounds) {

				if (nameOnly.contains(camp.getName())) {

					choosenCampground = camp;

				}

			}

			System.out.println("YOU SELECTED " + choosenCampground.getName());
		System.out.println("");
			System.out.println("What is the arrival date? YYYYMMDD");
			Scanner arrivalScanner = new Scanner(System.in);
			String userArrivalDate = arrivalScanner.nextLine();

			System.out.println("What is the departure date? YYYYMMDD");
			Scanner departScanner = new Scanner(System.in);
			String userDepartDate = departScanner.nextLine();
			
			handleSitesSelection(choosenCampground, userArrivalDate, userDepartDate);
			
		} 
		else if (choice.equals(SEARCH_RESERVATION)) {
			
			retrieveReservation();
		}


		return choosenCampground;
	}

	public Reservation handleSitesSelection(Campground ChoosenCampground, String arrivalDate, String departDate) {

		List<Site> popularSites = jdbcSiteDAO.getPopularSites(ChoosenCampground.getCampgroundId(), arrivalDate,
				departDate);

		
		if (popularSites.size() == 0) {
			displayAllSites(ChoosenCampground, arrivalDate, departDate);
			
		}
		else {
	
		System.out.println("These are the most popular sites for the dates you selected");
		System.out.println("");
		System.out.printf("\n %-15s %-15s \t%-15s %-15s \t%-15s", "Site No.", "Max Occup.", "Accessible?", "RV Length",
				"Utility");

		for (Site sites : popularSites) {
			System.out.printf("\n %-15s %-15s \t%-15s %-15s \t%-15s", sites.getSiteid(), sites.getMaxOccupancy(),
					sites.isAccessible(), sites.getMaxRVLength(), sites.isUtilities());
		}
		}

		Scanner reservationInput = new Scanner(System.in);
		System.out.println(" "
				+ ""
				+ "");
		System.out.println("Which site would you like to reserve?");
		String siteReserve = reservationInput.nextLine();

		System.out.println("What is your name?");
		String reserveName = reservationInput.nextLine();

		
		long y = Long.parseLong(siteReserve);

		String arrivalDateTrans = arrivalDate.substring(0, 4) + "-" + arrivalDate.substring(4, 6) + "-"
				+ arrivalDate.substring(6, 8);
		String departDateTrans = departDate.substring(0, 4) + "-" + departDate.substring(4, 6) + "-"
				+ departDate.substring(6, 8);

		

		Reservation reservation = new Reservation();
		reservation.setSiteid(y);
		reservation.setName(reserveName);
		reservation.setFromDate(arrivalDateTrans);
		reservation.setToDate(departDateTrans);

		
		Long reservationid = jdbcReservationDAO.newReservation(reservation.getSiteid(), reservation.getName(), reservation.getFromDate(),
				reservation.getToDate());

		System.out.println("CONGRATS YOUR RESERVATION IS BOOKED\n");
		System.out.println("Your reservation id is: " + reservationid);

		return reservation;
	}

	public void displayAllSites(Campground ChoosenCampground, String arrivalDate, String departDate) {

		List<Site> allSites = jdbcSiteDAO.getAllSites(ChoosenCampground.getCampgroundId(), arrivalDate, departDate);

		System.out.printf("\n %-15s %-15s \t%-15s %-15s \t%-15s", "Site No.", "Max Occup.", "Accessible?", "RV Length",
				"Utility");

		for (Site sites : allSites) {
			System.out.printf("\n %-15s %-15s \t%-15s %-15s %-15s", sites.getSiteid(), sites.getMaxOccupancy(),
					sites.isAccessible(), sites.getMaxRVLength(), sites.isUtilities());
		}
		
	}
	
	public void retrieveReservation() {

		System.out.println("What is your reservation id?");
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();
		long numInput = Long.parseLong(userInput);
		
	Reservation reservationc = jdbcReservationDAO.getReservationbyID(numInput);
		
	if (numInput == reservationc.getReservationid()) {
		System.out.println("Hello " + reservationc.getName() + "! \n" + "You reserved siteid: " + reservationc.getSiteid() + " for the dates of " + reservationc.getFromDate() + " to "
				+ reservationc.getFromDate() + "\n");
	}
	else {
		System.out.println("We couldn't find a reservation under that id.  Please try a different one");
		retrieveReservation();
	}

	}

}
 