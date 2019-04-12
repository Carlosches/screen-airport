package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FlightDateAndTimeComparatorTest {
	
	/**
	 * relationship with the class to be tested
	 */
	private FlightDateAndTimeComparator flightComparator;
	
	/**
	 * first scenary, is used to test the method compare
	 */
	 
	private void setUpScenary1() {
		flightComparator = new FlightDateAndTimeComparator();
	}
	 
	 /**
	  * test to verify that two flight objects are properly compared by date and time
	  */
	
	@Test
	void testCompare() {
		setUpScenary1();
		
		Flight one = new Flight("avianca", "AV1234", "Bogota", new Date(1, 5, 2015));
		Flight less = new Flight("GOL", "GO4898", "Cali", new Date(4, 1, 2014));
		Flight higher = new Flight("LOT", "LO2228", "Medellin", new Date(7, 12, 2018));
		
		assertTrue("the method not work", flightComparator.compare(one, less) == 1);
		assertTrue("the method not work", flightComparator.compare(one, higher) == -1);
	}
}
