package com.techelevator;

import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	private JDBCReservationDAO reservationDAO;
	private JDBCCampgroundDAO campgroundDAO;
	private JDBCParkDAO parkDAO;
	
	private int choice = 0;
	Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
	}
	
	public void run() {
		while (true) {
			displayParkMenu();
			displayCampgroundMenu();
			displayAvailableSites();
			if (choice == 0) {
				break;
			}
		}
	}
	
	private void displayCampgroundMenu() {
		
		List<Campground> cg_list = campgroundDAO.getAllCampgrounds(choice);
		int ctr = 1;
		for (Campground c : cg_list) {
			System.out.println(ctr++ + ".) " + c.getCampground_name() + " \n" + "	" + "campground id: " + c.getCampground_id() + " \n" + "	" + "open month: " + c.getOpen_month()
			+ " \n" + "	" + "close month: " + c.getClose_month()+ " \n" + "	" + "daily fee: $" + c.getDaily_fee() + " \n");
		}
		System.out.println("Choose a campground or enter '0' to return to previous menu");
		choice = in.nextInt();
	}
	
	private void displayParkMenu() {
		
		List<Park> parkList = parkDAO.getAllParks();
		int ctr = 1;
		for (Park p : parkList) {
			System.out.println(ctr++ + ".) " + p.getParkName() + " \n" + "	" + "park_id: " + p.getParkId()+ " \n" + "	" + "location: " + 
					p.getParkLocation()+ " \n" + "	" + "area: "+ p.getParkArea()+ " \n" + "	" + "annual visitors: " + p.getVisitors()+ " \n" + "	" + "description: " + p.getDescription() + " \n");
		}
		System.out.println("Choose a park to visit");
		choice = in.nextInt();
		in.nextLine();
		if (choice < 1 || choice > 3) {
			System.out.println(choice + " is not a valid option!");
		}
	}
	
	private void displayAvailableSites() {
		System.out.println("Enter start date: YYYY-MM-DD");
		String start = in.nextLine();
		in.nextLine();
		System.out.println("Enter campground id: ");
		int campground_id = in.nextInt();
		in.nextLine();
		System.out.println("Enter end date: YYYY-MM-DD");
		String end = in.nextLine();
		
		List<Reservation> reservation = reservationDAO.checkForAvailableReservations(start, end, campground_id);
		int ctr = 1;
		for (Reservation r : reservation) {
			System.out.println(ctr++ + ".) " + r.getName() + " \n");
		}
	}
	
	
	
}





