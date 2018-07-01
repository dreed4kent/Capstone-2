package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCparkDAOtest {

	private static SingleConnectionDataSource dataSource;
	private static final String park_name = "OurPark";
	private JDBCParkDAO dao;
	
	@Before
	public void setUp() {
		String sqlInsert = "Insert into park (park_id, name, location, establish_date, area, visitors, description) " + 
		"Values (4, ?, 'California', '2018-02-05', 40500, 1000, 'A really nice park')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsert, park_name); 
		dao = new JDBCParkDAO(dataSource);
	}
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void test_get_all_parks() {
		List<Park> allParks = dao.getAllParks();
		assertNotNull(allParks);
		assertEquals(4, allParks.size());
	}
}
