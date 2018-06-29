package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbctemplate;
	public JDBCCampgroundDAO(DataSource datasource) {
		this.jdbctemplate = new JdbcTemplate(datasource);
	}
	
	private Campground mapCampgroundToRow(SqlRowSet results) {
		Campground campground = new Campground();
		campground.setCampground_id(results.getInt("campground_id"));
		campground.setCampground_name(results.getString("name"));
		campground.setOpen_month(results.getString("open_from_mm"));	// When CAMPGROUNG is Opened, not user choice
		campground.setClose_month(results.getString("open_to_mm"));		// When CAMPGROUNG is Closed, not user choice
		campground.setDaily_fee(results.getDouble("daily_fee"));
		return campground;
	}

	@Override
	public List<Campground> getAllCampgrounds(int park_id) {
		List<Campground> allCampgrounds = new ArrayList<Campground>();
		String selectAll = "Select * from campground WHERE park_id = ?";
		SqlRowSet result = jdbctemplate.queryForRowSet(selectAll, park_id);
		while (result.next()) {
			allCampgrounds.add(mapCampgroundToRow(result));
		}
		return allCampgrounds;
	}

	@Override
	public Campground getCampgroundById(int id) {
		Campground campgroundById = null;
		String idQuery = "SELECT * FROM campground " + 
				" WHERE campground_id = ?";
		SqlRowSet result = jdbctemplate.queryForRowSet(idQuery, id);
		if (result.next()) {
			campgroundById = mapCampgroundToRow(result);
		}
		return campgroundById;
	}

}
