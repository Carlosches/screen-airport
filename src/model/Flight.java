/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Icesi University (Cali - Colombia)
 * Faculty of Engineering (algorithms and programming 2)
 * laboratory 4
 * By: Carlos Andrés Restrepo Marín 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package model;

public class Flight implements Comparable<Flight> {
	
	/**
	 * The next element of the linked list of flights
	 */
	private Flight nextFlight;
	/**
	 * The previous element of the linked list of flights
	 */
	private Flight prevFlight;
	
	
	/**
	 * represent the airline of the flight
	 */
	private String airline;
	/**
	 * represent the flight number
	 */
	private String flightNumber;
	/**
	 * represent the place or city of destination of the flight
	 */
	private String destination;
	/**
	 * represent the boarding gate for the flight crew
	 */
	private int boardingGate;
	/**
	 * represent the departure time of the flight
	 */
	private String departureTime;
	/**
	 * relationship with the Date class that represent the date of the flight
	 */
	
	private Date date;
	
	/**
	 * Initialize a new Flight
	 * @param airline, the airline of the flight
	 * @param flightNumber, the flight number
	 * @param destination, the places of destination of the flight
	 * @param date, the date of the flight
	 */
	public Flight(String airline, String flightNumber, String destination, Date date) {
		
		this.airline = airline;
		this.flightNumber = flightNumber;
		this.destination = destination;
		
		
		int gate = 1 + (int)(Math.random() * (12));
		
		
		boardingGate = gate;
		
		String[] format = {"AM" , "PM"};
		int hour =  1 + (int)(Math.random() * (12));
		int minutes =   1 + (int)(Math.random() * (59));
		
		String timeHour = String.valueOf(hour);
		String timeMinutes = String.valueOf(minutes);
		
		if(timeHour.length() == 1) {
			timeHour = "0"+ timeHour;
		}
		if(timeMinutes.length() == 1) {
			timeMinutes = "0" + timeMinutes;
		}
		String departure = timeHour + "." + timeMinutes + " " +format[ 0 + (int)(Math.random() * (1))];
		
		departureTime = departure;
		this.date = date;
		
	}
	
	/**
	 * This method is responsible for comparing this Flight with another according to the flight number
	 * @return comparation, the value of the comparation
	 */
	@Override
	public int compareTo(Flight other) {
	int comparation = 0;
		
		String one = flightNumber.substring(0, 2);
		String two = other.getFlightNumber().substring(0,2);
		
		int oneNumber = Integer.parseInt(flightNumber.substring(3));
		int twoNumber = Integer.parseInt(other.getFlightNumber().substring(3));
		
		if(one.compareTo(two) > 0) {
			comparation = 1;
		}else if(one.compareTo(two) < 0) {
			comparation = -1;
		}else {
			if(oneNumber > twoNumber) {
				comparation = 1;
			}else if(oneNumber < twoNumber) {
				comparation = -1;
			}else {
				comparation = 0;
			}
		}
		
		return comparation;
	}
	
	
	/**
	 * <b>Description:</b>
		* allows to obtain the airline of the Flight
	 * @return airline, the airline of the flight
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * <b>Description:</b>
		* allows to obtain the flight number
	 * @return flightNumber, the flight number
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * <b>Description:</b>
		* allows to obtain the places of the destination of the Flight
	 * @return destination, the destination of the flight
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * <b>Description:</b>
		* allows to obtain the boarding gate for the flight crew
	 * @return boardingGate, the boarding gate of the flight
	 */
	public int getBoardingGate() {
		return boardingGate;
	}
	
	/**
	 * <b>Description:</b>
		* allows to obtain the date of the flight
	 * @return date, the date of the flight
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * <b>Description:</b>
		* allows to obtain the departure time of the flight
	 * @return departureTime, the departure time
	 */
	public String getDepartureTime() {
		return departureTime;
	}
	
	
	
	/**
	 *  <b>Description:</b>
		* allows to obtain the next flight of the linked list
	 * @return the nextFlight
	 */
	public Flight getNextFlight() {
		return nextFlight;
	}

	/**
	 *  <b>Description:</b>
		* allows to change the next flight of the linked list
	 * @param nextFlight the nextFlight to set
	 */
	public void setNextFlight(Flight nextFlight) {
		this.nextFlight = nextFlight;
	}

	/**
	 * <b>Description:</b>
		* allows to obtain the previous flight of the linked list
	 * @return prevFlight the previous flight
	 */
	public Flight getPrevFlight() {
		return prevFlight;
	}

	/**
	 *  <b>Description:</b>
		* allows to change the previous flight of the linked list
	 * @param nextFlight the prevFlight to set
	 */
	public void setPrevFlight(Flight prevFlight) {
		this.prevFlight = prevFlight;
	}

	
	
}
