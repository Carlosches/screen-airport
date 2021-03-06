package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class AirportScreenTest {

	/**
	 * relationship with the class to be tested
	 */
	private AirportScreen airport;
	
	/**
	 * first scenary, it is empty since the builder of the AiportScreen class will be tested
	 */
	private void setUpScenary1() {
		
	}
	/**
	 * second scenary, is used to test the methods that allow generating the list of flights randomly
	 */
	private void setUpScenary2() {
		airport = new AirportScreen();
		
	}
	/**
	 * third scenario, is used to test the ordering and search methods
	 */
	private void setUpScenary3() {
		airport = new AirportScreen();
		try {
			airport.generateList(15);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  test to verify the correct creation of a AiportScreen object and the correct functioning of trivial methods
	 */
	@Test
	void testAirportScreen() {
		setUpScenary1();
		
		airport = new AirportScreen();
		
		assertNotNull("the object was not initialized well", airport);
		assertNotNull("the list is null", airport.getFlights());
		airport.setFlights(new ArrayList<Flight>());
		assertNotNull("the list is null", airport.getFlights());
	}
	
	/**
	 * test to verify the correct reading of text files  
	 * and to verify if it catches the exception when it should not
	 */
	@Test
	void testLoadArchive() {
		setUpScenary2();
		
		try {
			String[] file = airport.loadArchive("data/airlines");
			assertTrue("did not read the file properly", file.length == 13 );
			assertTrue("did not read the file properly", file[5].equals("Latam") && file[0].equals("Avianca") && file[12].equals("easyJet"));
		}catch(IOException e) {
			fail("caught the exception when it should not");
		}
	}
	
	/**
	 * test to verify the correct reading of text files  
	 * and to verify if it does not catch the exception when it should
	 */
	 
	@Test
	void testLoadArchive2() {
		setUpScenary2();
		
		try {
			airport.loadArchive("data/numbers");
			fail("he did not catch the exception when he should not");
		}catch(IOException e) {
			
		}
	}
	
	/**
	 * test to verify that the list of flights is generated correctly
	 */
	@Test
	void testGenerateList() {
		setUpScenary2();
		
		try {
			airport.generateList(15);
			assertTrue("the list was not generated", airport.getFlights().size() == 15);
		}catch(IOException e) {
			fail("caught the exception when it should not");
		}
		
	}
	

	/**
	 * test to verify that flights are generated randomly
	 *  and to verify if it catches the exception when it should not
	 */
	@Test
	void testRandom() {
		setUpScenary2();
		
		try {
			String[] airl = airport.loadArchive("data/airlines");
			String[] dest = airport.loadArchive("data/destinations");
			
			Flight n = airport.random(5, airl, dest);
			assertNotNull("did not generate a random flight", n);
		}catch(IOException e) {
			fail("caught the exception when it should not");
		}
		
	}

	/**
	 * test to verify that the list is correctly ordered according to the date and time of departure
	 */
	@Test
	void testSortingByDateAndTime(){
		setUpScenary3();
		
		airport.sortingByDateAndTime();
		boolean ex = true;
		for (int i = 0; i < airport.getFlights().size()-1; i++) {
			if(airport.getFlights().get(i).getDate().compareTo(airport.getFlights().get(i+1).getDate()) > 0) {
				ex = false;
			}
		}
		
		assertTrue("it is not ordered correctly", ex);
		
		
	}
	
	/**
	 * test to verify that the list is correctly ordered according to the airline
	 */
	@Test
	void testSortingByAirline(){
		setUpScenary3();
		
		airport.sortingByAirline();
		boolean ex = true;
		for (int i = 0; i < airport.getFlights().size()-1; i++) {
			if(airport.getFlights().get(i).getAirline().compareToIgnoreCase(airport.getFlights().get(i+1).getAirline()) > 0) {
				ex = false;
			}
		}
		
		assertTrue("it is not ordered correctly", ex);
		
		
	}
	
	/**
	 * test to verify that the list is correctly ordered according to the flight number
	 */
	@Test
	void testSortingByFlightNumber(){
		setUpScenary3();
		
		airport.sortingByFlightNumber();
		boolean ex = true;
		for (int i = 0; i < airport.getFlights().size()-1; i++) {
			if(airport.getFlights().get(i).compareTo(airport.getFlights().get(i+1)) > 0) {
				ex = false;
			}
		}
		
		assertTrue("it is not ordered correctly", ex);
		
		
	}
	
	
	/**
	 * test to verify that the list is correctly ordered according to the destination
	 */
	@Test
	void testSortingByDestination(){
		setUpScenary3();
		
		airport.sortingByDestination();
		boolean ex = true;
		for (int i = 0; i < airport.getFlights().size()-1; i++) {
			if(airport.getFlights().get(i).getDestination().compareToIgnoreCase(airport.getFlights().get(i+1).getDestination()) > 0) {
				ex = false;
			}
		}
		
		assertTrue("it is not ordered correctly", ex);
		
		
	}
	
	/**
	 * test to verify that the list is correctly ordered according to the boarding gate
	 */
	@Test
	void testSortingByBoardingGate(){
		setUpScenary3();
		
		airport.sortingByBoardingGate();
		boolean ex = true;
		for (int i = 0; i < airport.getFlights().size()-1; i++) {
			if(airport.getFlights().get(i).getBoardingGate() > airport.getFlights().get(i+1).getBoardingGate() ) {
				ex = false;
			}
		}
		
		assertTrue("it is not ordered correctly", ex);
		
		
	}
	
	/**
	 * test to verify that the searched flight is found correctly by date
	 */
	@Test
	void testSearchByDate() {
		setUpScenary3();
		
		int day = airport.getFlights().get(0).getDate().getDay();
		int month = airport.getFlights().get(0).getDate().getMonth();
		int year = airport.getFlights().get(0).getDate().getYear();
		
		Flight f =airport.searchByDate(year, month, day);
		
		//the maximum date is until 2019, this is done to make sure you do not find it when you are not
		int year2 = 2025;
		
		Flight f2 =airport.searchByDate(year2, month, day);
		
		assertTrue("did not find the object", f.getDate().getDay() == day && f.getDate().getMonth() == month && f.getDate().getYear() == year );
		assertNull("the method did not work", f2);
		
	}
	
	/**
	 * test to verify that the searched flight is found correctly by time
	 */
	
	@Test
	void testSearchByTime() {
		setUpScenary3();
		
		String n = airport.getFlights().get(13).getDepartureTime();
		
		Flight f =airport.searchByTime(n);
		
		String n2 = "00.00 AM";
		Flight f2 =airport.searchByTime(n2);
		
		assertTrue("did not find the object", f.getDepartureTime().equals(n));
		assertNull("the methid did not work", f2);
		
		
	}
	
	/**
	 * test to verify that the searched flight is found correctly by airline
	 */

	@Test
	void testSearchByAirline() {
		setUpScenary3();
		
		String n = airport.getFlights().get(13).getAirline();
		
		Flight f =airport.searchByAirline(n);
		
		String n2 = "non-existent";
		
		Flight f2 =airport.searchByAirline(n2);
		
		assertTrue("did not find the object", f.getAirline().equals(n));
		assertNull("the methid did not work", f2);
		
		
	}
	
	/**
	 * test to verify that the searched flight is found correctly by destination
	 */
	@Test
	void testSearchByDestination() {
		setUpScenary3();
		
		String n = airport.getFlights().get(13).getDestination();
		
		Flight f =airport.searchByDestination(n);
		
		
		String n2 = "non-existent";
		
		Flight f2 =airport.searchByDestination(n2);
		
		assertNull("the methid did not work", f2);
		assertTrue("did not find the object", f.getDestination().equals(n));
		
		
	}
	/**
	 * test to verify that the searched flight is found correctly by boarding gate
	 */
	@Test
	void testSearchByGate() {
		setUpScenary3();
		
		int n = airport.getFlights().get(13).getBoardingGate();
		
		Flight f =airport.searchByGate(n);
		
		int n2 = 0;
		
		Flight f2 =airport.searchByGate(n2);
		
		assertNull("the methid did not work", f2);
		assertTrue("did not find the object", f.getBoardingGate() == n);
		
		
	}
	
	/**
	 * test to verify that the searched flight is found correctly by flight number
	 */
	@Test
	void testSearchByFlightNumber() {
		setUpScenary3();
		
		String n = airport.getFlights().get(13).getFlightNumber();
		
		Flight f =airport.searchByFlightNumber(n);
		
		String n2 = "non-existent";
		
		Flight f2 =airport.searchByAirline(n2);
		
		assertNull("the methid did not work", f2);
		assertTrue("did not find the object", f.getFlightNumber().equals(n));
		
		
	}
	
	
}
