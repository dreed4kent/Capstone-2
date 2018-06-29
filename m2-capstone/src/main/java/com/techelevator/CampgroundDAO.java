package com.techelevator;

import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> getAllCampgrounds(int park_id);
	public Campground getCampgroundById(int id);

}
