package com.techelevator;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

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
			bookCampSite();
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
		choice = Integer.parseInt(in.nextLine());
		
	}
	
	private void displayParkMenu() {
		
		List<Park> parkList = parkDAO.getAllParks();
		int ctr = 1;
		for (Park p : parkList) {
			System.out.println(ctr++ + ".) " + p.getParkName() + " \n" + "	" + "park_id: " + p.getParkId()+ " \n" + "	" + "location: " + 
					p.getParkLocation()+ " \n" + "	" + "area: "+ p.getParkArea()+ " \n" + "	" + "annual visitors: " + p.getVisitors()+ " \n" + "	" + "description: " + p.getDescription() + " \n");
		}
		System.out.println("Choose a park to visit");
		choice = Integer.parseInt(in.nextLine());
		if (choice < 1 || choice > 3) {
			System.out.println(choice + " is not a valid option!");
		}
	}
	
	private void displayAvailableSites() {
		
		while(true) {
		System.out.println("Enter start date: YYYY-MM-DD");
		String start = in.nextLine();
		LocalDate startDate = LocalDate.parse(start);
		
		System.out.println("Enter end date: YYYY-MM-DD");
		String end = in.nextLine();
		LocalDate endDate = LocalDate.parse(end);
		
		System.out.println("Enter campground id: ");
		int campground_id = Integer.parseInt(in.nextLine());
		
		List<Site> availableSites = reservationDAO.checkForAvailableReservations(startDate, endDate, campground_id);
			if(availableSites.isEmpty()) {
				System.out.println("No available sites, please choose different dates.");
			}
			int ctr = 1;
			for (Site r : availableSites) {
				System.out.println(ctr++ + ".) " + r.getSite_id() + " cost of stay $" + getTotalCost(campground_id, startDate, endDate) + "\n");
			}
			break;
		}
		
	}
	
	public double getTotalCost(int campground_id, LocalDate startDate, LocalDate endDate) {
		Campground campground = campgroundDAO.getCampgroundById(campground_id);
		
		//compare months from open and close to start and end
		
		double monthsStayed = endDate.getMonthValue() - startDate.getMonthValue();
		double daysStayed = (endDate.getDayOfMonth()) - startDate.getDayOfMonth();
		double totalCostOfStay = 0;
		if (daysStayed < 0) {
			totalCostOfStay = (monthsStayed * 30 - Math.abs(daysStayed)) * campground.getDaily_fee(); 
		} else {
			totalCostOfStay = (monthsStayed * 30 + daysStayed) * campground.getDaily_fee(); 
		}
		
		return totalCostOfStay;
		
	}
	
	private void bookCampSite() {
		Reservation res = new Reservation();
		System.out.println("Select from 1- 5 above");
		choice = Integer.parseInt(in.nextLine());
		res.setSite_id(choice);
		
		System.out.println("Enter name for reservation.");
		String name = in.nextLine();
		
		System.out.println("What will be the start date? yyyy-MM-dd");
		String beginning = in.nextLine();
		LocalDate reservationStartDate = LocalDate.parse(beginning);
		
		System.out.println("What will be the end date? yyyy-MM-dd");
		String ending = in.nextLine();
		LocalDate reservationEndDate = LocalDate.parse(ending);
		
		reservationDAO.bookReservation(name, reservationStartDate, reservationEndDate, choice);
		System.out.println("your confirmation number is: " + res.getReservation_id() + "\n");
		
	}
	

	
	
}





