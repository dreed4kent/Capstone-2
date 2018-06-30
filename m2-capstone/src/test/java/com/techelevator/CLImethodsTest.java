package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class CLImethodsTest {
	
	private CampgroundCLI camp;
	private static SingleConnectionDataSource dataSource;
	private LocalDate startDate = LocalDate.parse("2018-06-25");
	private LocalDate endDate = LocalDate.parse("2018-06-30");
	
	@Before
	public void setUp() {
		camp = new CampgroundCLI(dataSource);
		
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
	public void test_for_cost_of_stay() {
		double cost = camp.getTotalCost(1, startDate, endDate);
		double expectedCost = 175d;
		assertEquals(175d, camp.getTotalCost(1, startDate, endDate), 0.0001);
	}

}
