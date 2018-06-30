package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO {
	

	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private Reservation mapReservationToRow (SqlRowSet result) {
		Reservation reservation = new Reservation();
		reservation.setReservation_id(result.getInt("reservation_id"));
		reservation.setSite_id(result.getInt("site_id"));
		reservation.setCreate_date(result.getDate("create_date"));
		reservation.setFrom_date(result.getDate("from_date"));
		reservation.setTo_date(result.getDate("to_date"));
		reservation.setName(result.getString("name"));
		return reservation;
	}
	
	public List<Site> checkForAvailableReservations(LocalDate startDate, LocalDate endDate, int campground_id) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		JDBCSiteDAO siteDAO = new JDBCSiteDAO(dataSource);
		List<Site> availableSites = new ArrayList <Site>();
		String reservationAvailability = "SELECT * FROM site " + 
				"WHERE campground_id = ? " + 
				" AND site_id NOT IN (SELECT site_id FROM reservation WHERE from_date >= ? AND to_date <= ?) LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(reservationAvailability, campground_id, startDate, endDate);
		while (results.next()) {
			availableSites.add(siteDAO.mapSightToRow(results));
		}
		return availableSites;
	}
	

	
	public void bookReservation(String name, LocalDate start, LocalDate end, int siteId) {
		
		Reservation bookingReservation = new Reservation();
		String sqlReserve = "Insert into reservation(site_id, name, from_date, to_date, create_date) " + 
							"Values (?, ?, ?, ?, '2018-06-29')";
//		Integer id = jdbcTemplate.queryForObject(sqlReserve, Integer.class, bookingReservation.getReservation_id());
//		bookingReservation.setReservation_id(id);
	
		jdbcTemplate.update(sqlReserve, siteId, name, start, end);
		
	}
	
	

//	public int getNextReservationId() {
//		SqlRowSet nextId = jdbcTemplate.queryForRowSet("Select nextval('seq_reservation_id')");
//		if (nextId.next()) {
//			return nextId.getInt(1);
//		} else {
//			throw new RuntimeException("Something went wrong trying to get next Reservation Id");
//		}
//	}
	

}
