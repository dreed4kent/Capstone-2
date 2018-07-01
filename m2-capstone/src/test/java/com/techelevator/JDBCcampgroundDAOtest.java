package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCcampgroundDAOtest {
	
	private static SingleConnectionDataSource dataSource;
	private static final String campground_name = "OurCampground";
	private JDBCCampgroundDAO dao;
	
	@Before
	public void setUp() {
		String sqlInsert = "Insert into campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) " + 
		"Values (8, 2, ?, '01', '12', 20)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsert, campground_name);
		dao = new JDBCCampgroundDAO(dataSource);
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
	public void test_get_all_campgrounds() {
		List<Campground> allCampgrounds = dao.getAllCampgrounds(2);
		assertNotNull(allCampgrounds);
		assertEquals(4, allCampgrounds.size());
	}
	
	@Test
	public void test_get_campground_by_id() {
		Campground camp2 = getCampground(8, campground_name);
		Campground camp = dao.getCampgroundById(camp2.getCampground_id());
		assertNotEquals(null, camp.getCampground_id());
		assertCampgroundsAsEqual(camp2, camp);
		
	}
	
	private Campground getCampground(int campground_id, String name) {
		Campground camp = new Campground();
		camp.setCampground_id(campground_id);
		camp.setCampground_name(name);
		return camp;
	}
	
	private void assertCampgroundsAsEqual(Campground expected, Campground actual) {
		assertEquals(expected.getCampground_id(), actual.getCampground_id());
		assertEquals(expected.getCampground_name(), actual.getCampground_name());
	}

}
