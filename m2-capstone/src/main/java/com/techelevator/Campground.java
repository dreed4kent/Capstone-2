package com.techelevator;

public class Campground {
	
	private int campground_id;
	private int park_id;
	private String campground_name;
	private String open_month;
	private String close_month;
	private double daily_fee;
	public int getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(int campground_id) {
		this.campground_id = campground_id;
	}
	
	public int getPark_id() {
		return park_id;
	}
	public void setPark_id(int park_id) {
		this.park_id = park_id;
	}
	
	
	public String getCampground_name() {
		return campground_name;
	}
	public void setCampground_name(String campground_name) {
		this.campground_name = campground_name;
	}
	
	
	public String getOpen_month() {
		return open_month;
	}
	public void setOpen_month(String open_month) {
		this.open_month = open_month;
	}
	
	
	public String getClose_month() {
		return close_month;
	}
	public void setClose_month(String close_month) {
		this.close_month = close_month;
	}
	
	
	public double getDaily_fee() {
		return daily_fee;
	}
	public void setDaily_fee(double daily_fee) {
		this.daily_fee = daily_fee;
	}
	
	

}
