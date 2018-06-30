package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCReservationDAOIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private static final String reservation_name = "my res";
	private JDBCReservationDAO dao;
	
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
	
	
	@Before
	public void setUp() {
		String sqlInsert = "Insert into reservation (reservation_id, site_id, name, from_date, to_date, create_date) " + 
						   "Values (49, 1, ?, '2018-08-20', '2018-08-30', '2018-06-29')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.update(sqlInsert, reservation_name);
		dao = new JDBCReservationDAO(dataSource);
	}
	
	private LocalDate startDate = LocalDate.parse("2018-06-25");
	private LocalDate endDate = LocalDate.parse("2018-06-30");
	
	@Test
	public void testBookReservations() {
		Reservation r = getReservation(49, reservation_name, 2);		// LocalDate to int???
		dao.bookReservation(r.getName(), startDate, endDate, 1);
	}
	
	private Reservation getReservation(Integer reservation_id, String reservation_name, Integer site_id) {
		Reservation r = new Reservation();
		r.setReservation_id(reservation_id);
		r.setName(reservation_name);
		r.setSite_id(site_id);
		return r;
	}
	
	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		assertEquals(expected.getReservation_id(), actual.getReservation_id());
		assertEquals(expected.getName(), actual.getName());
	}
	
	//String sqlReserve = "Insert into reservation(site_id, name, from_date, to_date, create_date) "
}
