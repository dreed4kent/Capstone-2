package com.techelevator;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {
	
	private int reservation_id;
	private int site_id;
	private String name;
	private Date from_date;
	private Date to_date;
	private Date create_date;
	
	
	public int getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}
	
	
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Date getFrom_date() {
		return from_date;
	}
	public void setFrom_date(Date date) {
		this.from_date = date;
	}
	
	
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date date) {
		this.to_date = date;
	}
	
	
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date date) {
		this.create_date = date;
	}

	
	
}
