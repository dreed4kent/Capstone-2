package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
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
		reservation.setCreate_date(result.getString("create_date"));
		reservation.setFrom_date(result.getString("from_date"));
		reservation.setTo_date(result.getString("to_date"));
		reservation.setName(result.getString("name"));
		return reservation;
	}
	
	public List<Reservation> checkForAvailableReservations(String start_date, String end_date, int campground_id) {
		List<Reservation> availableSites = new ArrayList <Reservation>();
		String reservationAvailability = "SELECT * FROM site " + 
				"WHERE campground_id = ? " + 
				" AND site_id NOT IN (SELECT * FROM reservation WHERE from_date >= '?' AND to_date <= '?')";
		SqlRowSet results = jdbcTemplate.queryForRowSet(reservationAvailability, campground_id, start_date, end_date);
		while (results.next()) {
			availableSites.add(mapReservationToRow(results));
		}
		return availableSites;
	}
	

}
