package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DateTest {
	
	/**
	 * relationship with the class to be tested
	 */
	private Date date;

	/**
	 * first scenary, it is empty since the builder of the Date class will be tested
	 */
	private void setUpScenary1() {
		
	}
	/**
	 * second scenary, is used to test the method compareTo
	 */
	private void setUpScenary2() {
		date = new Date(1, 10, 2011);
	}
	
	/**
	 *  test to verify the correct creation of a Date object and the correct functioning of trivial methods
	 */
	@Test
	void testDate(){
		setUpScenary1();
		
		Date da = new Date(4, 9, 2018);
		
		assertNotNull("The object is null", da);
		assertTrue("the mehtod getDay does not work", da.getDay() == 4);
		assertTrue("the mehtod getMonth does not work", da.getMonth() == 9);
		assertTrue("the mehtod getYear does not work", da.getYear() == 2018);
		
		
	}
	
	/**
	 * test to verify that the current date is properly compared with another date
	 */
	@Test
	void testCompareTo() {
		setUpScenary2();
		
		Date dateLess = new Date(1,1,2011);
		Date dateHeigher = new Date(2,12,2018);
		Date dateEquals = new Date(1,10,2011);
		
		// As the flight number is unique, it is not proven that it is the same
		
		assertTrue("the method does not work", date.compareTo(dateLess) == 1);
		assertTrue("the method does not work", date.compareTo(dateHeigher) == -1);
		assertTrue("The method does not work", date.compareTo(dateEquals) == 0);
	}
	

}
