/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Icesi University (Cali - Colombia)
 * Faculty of Engineering (algorithms and programming 2)
 * laboratory 4
 * By: Carlos Andrés Restrepo Marín 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AirportScreen {

	/**
	 * List that represents the flights of the airport
	 */
	private List<Flight> flights;
	
	/**
	 * constant that represents the location of the file airlines
	 */
	
	private static final String PATH_AIRLINES = "data/airlines";
	
	/**
	 * constant that represents the location of the file destinations
	 */
	private static final String PATH_DESTINATIONS = "data/destinations";
	
	/**
	 * initialize a new AiportScreen object
	 * <b>post:</b> 
	 * the list flights has been initialize
	 */
	
	public AirportScreen() {
		flights = new ArrayList<Flight>();
	}
	
	
	/**
	 * this method is responsible for reading a text file to generate random lists
	 * @param path, the location of the file
	 * @return array, a String array that represents the elements for random list
	 * @throws IOException if file is not found
	 */
	
	public String[] loadArchive(String path) throws IOException {
		String[] array;
		
		File fl = new File(path);
		FileReader fr = new FileReader(fl);
		BufferedReader br = new BufferedReader(fr);
		
		String re = "";
		String line = br.readLine();
		
		while(line!=null) {
			re+= line+";";
			line = br.readLine();
		}
		
		array = re.split(";");
		
		return array;
	}
	/**
	 * this method is responsible for adding the flights generated randomly
	 * @param size, the size of the flights list
	 * <b>post:</b> 
	 * items have been added to the list flights
	 * @throws IOException throw exception if file is not found
	 */
	public void generateList(int size) throws IOException{
		flights.clear();
		String[] airl = loadArchive(PATH_AIRLINES);
		String[] dest = loadArchive(PATH_DESTINATIONS);
		
		for (int i = 0; i < size; i++) {
			flights.add(random(i, airl, dest));
		}
	}
	
	/**
	 * this method is responsible for generating a random flight
	 * @param n, the position in the list
	 * @param airlines, array with the airlines
	 * @param destinations, array with the destinations
	 * @return f, the flight generated randomly
	 *@throws IOException if file is not found
	 */
	public Flight random(int n, String[] airlines, String[] destinations) throws IOException {

		
			String airl = airlines[0 + (int) (Math.random() * (13))];
			
			
			String destination = destinations[  0 + (int)(Math.random() * (18))];
			
			String number = airl.substring(0, 2);
				number = number + (n+1000);
			Flight f =  new Flight(airl, number, destination ,new Date(0,0,0));
			return f;
	
	}
	
	/**
	 * This method is responsible for ordering flights by the departure date and departure time. This is the default order
	 * * <b>post:</b> 
	 * the positions of the items in the list have been changed
	 */
	// default.  class Collections.sort comparator
	public void sortingByDateAndTime() {
		Collections.sort(flights, new FlightDateAndTimeComparator());
		 
	}
	
	
	/**
	 * This method is responsible for ordering flights by airline name.
	 *  <b>post:</b> 
	 * the positions of the items in the list have been changed
	 */
	// selection
	public void sortingByAirline() {
		for (int i = 0; i < flights.size()-1; i++) {
			
			int min = i;
			boolean e = false;
			for (int j = i+1; j < flights.size() ; j++) {
				
				if( flights.get(j).getAirline().compareToIgnoreCase(flights.get(min).getAirline()) < 0) {
					min = j;
					e = true;
				}
			}
			if(e) {
				Flight flJ = flights.get(min);
				Flight flJ1 = flights.get(i);
				flights.remove(min);
				flights.remove(i);
				flights.add(i, flJ);
				flights.add(min, flJ1);
			}
		}
		
		
	}
	
	/**
	 * This method is responsible for ordering flights by the flight number.
	 * <b>post:</b> 
	 * the positions of the items in the list have been changed
	 */
	// Collections.sort whit comparable
	public void sortingByFlightNumber() {
		
		Collections.sort(flights);
	}
	
	/**
	 * This method is responsible for ordering flights by the destination.
	 * * <b>post:</b> 
	 * the positions of the items in the list have been changed
	 */
	
	// insertion
	public void sortingByDestination() {
		
		for (int i = 0; i < flights.size(); i++) {
			
			for (int j = i; j > 0  && flights.get(j-1).getDestination().compareToIgnoreCase(flights.get(j).getDestination()) >0; j-- ) {
				Flight flJ = flights.get(j);
				Flight flJ1 = flights.get(j-1);
				flights.remove(j);
				flights.remove(j-1);
				flights.add(j-1, flJ);
				flights.add(j, flJ1);
			}
		}
		
	}
	
	/**
	 * This method is responsible for ordering flights by the boarding gate.
	 * * <b>post:</b> 
	 * the positions of the items in the list have been changed
	 */
	// bubble
	public void sortingByBoardingGate() {
		for (int i = 0; i < flights.size()-1; i++) {
			for (int j = 0; j < flights.size()-1-i; j++) {
				if(flights.get(j).getBoardingGate() > flights.get(j+1).getBoardingGate()) {
					Flight flJ = flights.get(j);
					Flight flJ1 = flights.get(j+1);
					flights.remove(j);
					flights.remove(j);
					flights.add(j, flJ1);
					flights.add(j+1, flJ);
					
				}
				
			}
		}
	}
	
	/**
	 * this method is responsible for finding a flight, according to a given date
	 * @param year, the flight year
	 * @param month, the flight month
	 * @param day, the flight day
	 * @return f, the flight found
	 */
	// Binary search
	public Flight searchByDate(int year, int month, int day){
		sortingByDateAndTime();
		
		Date key = new Date(day, month, year);
		int low=0;
		int high = flights.size()-1;
		
		int mid = 0;
		int pos = -1;
		
		while(low<=high && pos == -1) {
			mid = (low+high)/2;
			
			if(flights.get(mid).getDate().compareTo(key) <0) {
				low = mid+1;
			}else if(flights.get(mid).getDate().compareTo(key) >0) {
				high = mid-1;
			}else {
				pos = mid;
			}
			
			
		}
		Flight f;
		if( pos == -1) {
			f = null;
		}else {
			f = flights.get(pos);
		}
		return f;
		
	}
	
	/**
	 * this method is responsible for finding a flight, according to a given departure time
	 * @param timeKey, the search criteria
	 * @return f, the flight found
	 */
	// linear search
	public Flight searchByTime(String timeKey) {
		boolean exit = false;
		int pos = 0;
		for (int i = 0; i < flights.size( ) && !exit; i++) {
			if(flights.get(i).getDepartureTime().equalsIgnoreCase(timeKey)) {
				pos = i;
				exit = true;
			}
		}
		Flight f;
		if(exit) {
			f = flights.get(pos);
		}else {
			f = null;

		}
		return f;
	}
	
	/**
	 * this method is responsible for finding a flight, according to a given airline
	 * @param airlineKey, the search criteria
	 * @return f, the flight found
	 */
	
	// Binary search
	public Flight searchByAirline(String airlineKey) {
		sortingByAirline();
		
		int low=0;
		int high = flights.size()-1;
		
		int mid = 0;
		int pos = -1;
		
		while(low<=high && pos == -1) {
			mid = (low+high)/2;
			
			if(flights.get(mid).getAirline().compareToIgnoreCase(airlineKey) <0) {
				low = mid+1;
			}else if(flights.get(mid).getAirline().compareToIgnoreCase(airlineKey) >0) {
				high = mid-1;
			}else {
				pos = mid;
			}
		}
		Flight f;
		if( pos == -1) {
			f = null;
		}else {
			f = flights.get(pos);
		}
		return f;
		
	}

	/**
	 * this method is responsible for finding a flight, according to a given destination
	 * @param destinationKey, the search criteria
	 * @return f, the flight found
	 */
	public Flight searchByDestination(String destinationKey) {
		sortingByDestination();
		
		int low=0;
		int high = flights.size()-1;
		
		int mid = 0;
		int pos = -1;
		
		while(low<=high && pos == -1) {
			mid = (low+high)/2;
			
			if(flights.get(mid).getDestination().compareToIgnoreCase(destinationKey) <0) {
				low = mid+1;
			}else if(flights.get(mid).getDestination().compareToIgnoreCase(destinationKey) >0) {
				high = mid-1;
			}else {
				pos = mid;
			}
		}
		Flight f;
		if( pos == -1) {
			f = null;
		}else {
			f = flights.get(pos);
		}
		return f;
		
	
	}
	
	/**
	 * this method is responsible for finding a flight, according to a given boardin gate
	 * @param gateKey, the search criteria
	 * @return f, the flight found
	 */
	
	public Flight searchByGate(int gateKey) {
		boolean exit = false;
		int pos = 0;
		for (int i = 0; i < flights.size( ) && !exit; i++) {
			if(flights.get(i).getBoardingGate() == gateKey) {
				pos = i;
				exit = true;
			}
		}
		Flight f;
		if(exit) {
			f = flights.get(pos);
		}else {
			f = null;

		}
		return f;
	}
	/**
	 * this method is responsible for finding a flight, according to a flightNumber
	 * @param numberKey, the search criteria
	 * @return f, the flight found
	 */
	public Flight searchByFlightNumber(String numberKey) {
		sortingByFlightNumber();
		
		int low=0;
		int high = flights.size()-1;
		
		int mid = 0;
		int pos = -1;
		
		while(low<=high && pos == -1) {
			mid = (low+high)/2;
			
			if(flights.get(mid).getFlightNumber().compareToIgnoreCase(numberKey) <0) {
				low = mid+1;
			}else if(flights.get(mid).getFlightNumber().compareToIgnoreCase(numberKey) >0) {
				high = mid-1;
			}else {
				pos = mid;
			}
		}
		Flight f;
		if( pos == -1) {
			f = null;
		}else {
			f = flights.get(pos);
		}
		return f;
		
		
	
	}
	
	/**
	 * this method allows obtain the flights list
	 * @return flights, the list of the flights
	 */
	public List<Flight> getFlights() {
		return flights;
	}
	
	/**
	 * this method allows changing the list of flights
	 * @param fl, the new flights list
	 */
	public void setFlights(List<Flight> fl) {
		this.flights = fl;
	}


}
