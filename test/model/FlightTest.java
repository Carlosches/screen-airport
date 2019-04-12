package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FlightTest {
	/**
	 * relationship with the class to be tested
	 */
	private Flight flight;

	/**
	 * first scenary, it is empty since the builder of the Flight class will be tested
	 */
	private void setUpScenary1() {
		
	}
	
	/**
	 * second scenary, is used to test the method compareTo
	 */
	private void setUpScenary2() {
		flight = new Flight("GOL", "GO2345", "Medellin", new Date(5, 12, 2015));
	}
	
	/**
	 *  test to verify the correct creation of a Flight object and the correct functioning of trivial methods
	 */
	
	@Test
	void testFlight(){
		setUpScenary1();
		
		Flight fl = new Flight("avianca", "AV1234", "Bogota", new Date(5, 12, 2015));
		
		assertNotNull("The object is null", fl);
		assertTrue("the mehtod getAirline does not work", fl.getAirline().equals("avianca"));
		assertTrue("the mehtod getFlightNumber does not work", fl.getFlightNumber().equals("AV1234"));
		assertTrue("the mehtod getDestination does not work", fl.getDestination().equals("Bogota"));
		assertTrue("the mehtod getDate does not work", fl.getDate().getDay() == 5);
		assertTrue("the mehtod getDate does not work", fl.getDate().getMonth() == 12);
		assertTrue("the mehtod getDate does not work", fl.getDate().getYear() == 2015);
		assertTrue("the mehtod getBoardinGate does not work", fl.getBoardingGate() != 0);
		assertTrue("the mehtod getDepartureTime does not work", !fl.getDepartureTime().isEmpty());
		
	}
	
	/**
	 * test to verify that the current flight is properly compared with another flight
	 */
	@Test
	void testCompareTo() {
		setUpScenary2();
		
		Flight flightNumberLess = new Flight("avianca", "AV1234", "Bogota", new Date(5, 12, 2015));
		Flight flightNumberHeigher = new Flight("LOT", "LO2545", "Bogota", new Date(5, 12, 2015));
		
		// As the flight number is unique, it is not proven that it is the same
		
		assertTrue("the method does not work", flight.compareTo(flightNumberLess) == 1);
		assertTrue("the method does not work", flight.compareTo(flightNumberHeigher) == -1);
	}
	
}
