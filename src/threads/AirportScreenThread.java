package threads;

import java.io.IOException;

import model.AirportScreen;

public class AirportScreenThread extends Thread {
	
	/**
	 * the relationship with the main class of the model
	 */
	private AirportScreen airport;
	/**
	 * the criteria of the sorting
	 */
	private String criteria;
	/**
	 * size of the list to generate
	 */
	private int size;
	
	/**
	 * initialize a new AiportScreen
	 * @param airport, the object of the main class
	 * @param criteria, the sorting criteria
	 * @param size, the size of the list
	 */
	
	public AirportScreenThread(AirportScreen airport, String criteria, int size) {
		this.airport = airport;
		this.criteria = criteria;
		this.size = size;
	}
	
	/**
	 * this method allows run the thread
	 */
	@Override
	public void run() {
	
			if(criteria.equalsIgnoreCase("airline")) {
	    		airport.sortingByAirline();
	    		
	    	}else if(criteria.equalsIgnoreCase("Default (Date and departure time)")){
	    		airport.sortingByDateAndTime();
	    		
	    	}else if(criteria.equalsIgnoreCase("flight number")){
	    		airport.sortingByFlightNumber();
	    		
	    	}else if(criteria.equalsIgnoreCase("destination")){
	    		airport.sortingByDestination();
	    		
	    	}else if(criteria.equalsIgnoreCase("Gate")){
	    		airport.sortingByBoardingGate();
	    		
	    	}else if(criteria.equals("generate list")) {
	    		try {
					airport.generateList(size);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		airport.sortingByDateAndTime();
	    	}
	
		
	}
	
}
