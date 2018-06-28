package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO{
	
	private JdbcTemplate jdbctemplate;

	public JDBCParkDAO(DataSource datasource) {
		this.jdbctemplate = new JdbcTemplate (datasource);
	}


	@Override
	public List<Park> getAllParks() {
		
		List<Park> allParks = new ArrayList<Park>();
		String queryForAll = "select * from park";
		SqlRowSet results = jdbctemplate.queryForRowSet(queryForAll);
		
		while(results.next()) {
			allParks.add(mapParkToRow(results));
		}
		return allParks;
	}
	
	
	private Park mapParkToRow(SqlRowSet results) {
		Park park = new Park();
		park.setParkId(results.getInt("park_id"));
		park.setParkName(results.getString("name"));
		park.setDescription(results.getString("description"));
		park.setEstDate(results.getString("establish_date"));
		park.setParkArea(results.getInt("area"));
		park.setVisitors(results.getInt("visitors"));
		park.setParkLocation(results.getString("location"));
		return park;
	}

}
