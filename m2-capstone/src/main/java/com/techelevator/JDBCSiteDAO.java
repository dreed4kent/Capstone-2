package com.techelevator;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public Site mapSightToRow(SqlRowSet results) {
		Site site = new Site();
		site.setAccessible(results.getBoolean("accessible"));
		site.setCampground_id(results.getInt("campground_id"));
		site.setMax_occupancy(results.getInt("max_occupancy"));
		site.setMax_rv_length(results.getInt("max_rv_length"));
		site.setSite_id(results.getInt("site_id"));
		site.setSite_number(results.getInt("site_number"));
		site.setUtilities(results.getBoolean("utilities"));
		return site;
	}

}
