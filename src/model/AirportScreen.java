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
	 * object that represents the first element of the linked list
	 */
	private Flight firstFlight;
	
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
	 */
	
	public AirportScreen() {
		
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
	 * This function allows to add a new flight to the linked list
	 * @param newFlight the new flight to add
	 * <b>post:</b> 
	 * items have been added to the list flights
	 * 
	 */
	public void addFlight(Flight newFlight) {
		
		if(firstFlight != null) {
			
			Flight current = firstFlight;
			
			while(current.getNextFlight() != null) {
				current = current.getNextFlight();

			}
			
			current.setNextFlight(newFlight);
			newFlight.setPrevFlight(current);
		}else {
			firstFlight = newFlight;
		}
	}
	/**
	 * this method is responsible for adding the flights generated randomly
	 * @param size, the size of the flights list
	 * <b>post:</b> 
	 * items have been added to the list flights
	 * @throws IOException throw exception if file is not found
	 */
	public void generateList(int size) throws IOException{
		
		String[] airl = loadArchive(PATH_AIRLINES);
		String[] dest = loadArchive(PATH_DESTINATIONS);
		firstFlight = random(1, airl, dest);
		
		for (int i = 2; i <= size; i++) {
			Flight current = random(i, airl, dest);
			addFlight(current);

			
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

	
	public void sortingByDateAndTime() {
		FlightDateAndTimeComparator comparator = new FlightDateAndTimeComparator();
		boolean changed = true;
		while(changed) {
			Flight currentNode = firstFlight;
			changed = false;
			
			while(currentNode.getNextFlight() != null) {
				
				if(comparator.compare(currentNode, currentNode.getNextFlight()) >0) {
					changed = true;
					Flight less = currentNode.getNextFlight();
					
					if(currentNode.getPrevFlight() != null) {
						currentNode.getPrevFlight().setNextFlight(less);
					}
					if(less.getNextFlight() != null) {
						less.getNextFlight().setPrevFlight(currentNode);
					}
					
					currentNode.setNextFlight(less.getNextFlight());
					less.setPrevFlight(currentNode.getPrevFlight());
					currentNode.setPrevFlight(less);
					less.setNextFlight(currentNode);
					
					if(currentNode == firstFlight) {
						firstFlight = less;
					}
		
					
				}else {
					currentNode = currentNode.getNextFlight();
				}

			}
			
		}

	}
	
	
	/**
	 * This method is responsible for ordering flights by airline name.
	 *  <b>post:</b> 
	 * the positions of the items in the list have been changed
	 */
	// selection
	public void sortingByAirline() {
		
		Flight auxNode = firstFlight;
		
		
		while(auxNode != null) {
		
			Flight currentNode = auxNode;
			Flight nextNode = auxNode.getNextFlight();
			
			boolean enter = false;

			while(nextNode != null) {
				if(currentNode.getAirline().compareToIgnoreCase(nextNode.getAirline())>0) {
					Flight next = nextNode.getNextFlight();
					currentNode = nextNode;
					nextNode =next;
					enter = true;
				}else {
					nextNode = nextNode.getNextFlight();
				}
			}
			
			if(enter) {
				if(auxNode.getPrevFlight() != null) {
					auxNode.getPrevFlight().setNextFlight(currentNode);
				}
				if(currentNode.getNextFlight() != null) {
					currentNode.getNextFlight().setPrevFlight(auxNode);
				}
				
				if(currentNode == auxNode.getNextFlight()) {
					auxNode.setNextFlight(currentNode.getNextFlight());
					currentNode.setPrevFlight(auxNode.getPrevFlight());
					auxNode.setPrevFlight(currentNode);
					currentNode.setNextFlight(auxNode);
				}else {
					Flight auxNext = auxNode.getNextFlight();
					auxNode.setNextFlight(currentNode.getNextFlight());
					Flight currentprev =currentNode.getPrevFlight();
					currentNode.setPrevFlight(auxNode.getPrevFlight());
					auxNode.setPrevFlight(currentprev);
					currentNode.setNextFlight(auxNext);
					auxNext.setPrevFlight(currentNode);
					currentprev.setNextFlight(auxNode);
					

				}
				if(auxNode == firstFlight) {
					firstFlight = currentNode;
				}
				auxNode = currentNode.getNextFlight();
			}else {
				auxNode = auxNode.getNextFlight();
			}
		
		}
		
	}
	
	/**
	 * This method is responsible for ordering flights by the flight number.
	 * <b>post:</b> 
	 * the positions of the items in the list have been changed
	 */
	public void sortingByFlightNumber() {
		boolean changed = true;
		while(changed) {
			Flight currentNode = firstFlight;
			changed = false;
			
			while(currentNode.getNextFlight() != null) {
				
				if(currentNode.compareTo(currentNode.getNextFlight()) >0) {
					changed = true;
					Flight less = currentNode.getNextFlight();
					
					if(currentNode.getPrevFlight() != null) {
						currentNode.getPrevFlight().setNextFlight(less);
					}
					if(less.getNextFlight() != null) {
						less.getNextFlight().setPrevFlight(currentNode);
					}
					
					currentNode.setNextFlight(less.getNextFlight());
					less.setPrevFlight(currentNode.getPrevFlight());
					currentNode.setPrevFlight(less);
					less.setNextFlight(currentNode);
					
					if(currentNode == firstFlight) {
						firstFlight = less;
					}
		
					
				}else {
					currentNode = currentNode.getNextFlight();
				}

			}
			
		}

	}
	
	/**
	 * This method is responsible for ordering flights by the destination.
	 * * <b>post:</b> 
	 * the positions of the items in the list have been changed
	*/ 
	// insertion
	
	public void sortingByDestination() {
		Flight oCurr = firstFlight.getNextFlight();
		while(oCurr != null) {
			Flight curr = oCurr;
			while(curr.getPrevFlight()!=null) {
				if(curr.getDestination().compareToIgnoreCase(curr.getPrevFlight().getDestination()) <0) {
					Flight temp = curr.getPrevFlight();
					if(temp.getPrevFlight() != null) {
                        temp.getPrevFlight().setNextFlight(curr);
                    }
                    if(curr.getNextFlight() != null) {
                        curr.getNextFlight().setPrevFlight(temp);
                    }
                    temp.setNextFlight(curr.getNextFlight());
                    curr.setPrevFlight(temp.getPrevFlight());
                    temp.setPrevFlight(curr);
                    curr.setNextFlight(temp);
                    if(temp == firstFlight)
                        firstFlight = curr;
				}else
				curr = curr.getPrevFlight();
			}
			oCurr = oCurr.getNextFlight();
		}
		
	}
	
	/**
	 * This method is responsible for ordering flights by the boarding gate.
	 * * <b>post:</b> 
	 * the positions of the items in the list have been changed
	*/ 
	// bubble
	public void sortingByBoardingGate() {
		
		boolean changed = true;
		while(changed) {
			Flight currentNode = firstFlight;
			changed = false;
			
			while(currentNode.getNextFlight() != null) {
				
				if(currentNode.getBoardingGate() > currentNode.getNextFlight().getBoardingGate()) {
					changed = true;
					Flight less = currentNode.getNextFlight();
					
					if(currentNode.getPrevFlight() != null) {
						currentNode.getPrevFlight().setNextFlight(less);
					}
					if(less.getNextFlight() != null) {
						less.getNextFlight().setPrevFlight(currentNode);
					}
					
					currentNode.setNextFlight(less.getNextFlight());
					less.setPrevFlight(currentNode.getPrevFlight());
					currentNode.setPrevFlight(less);
					less.setNextFlight(currentNode);
					
					if(currentNode == firstFlight) {
						firstFlight = less;
					}
		
					
				}else {
					currentNode = currentNode.getNextFlight();
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
	public Flight searchByDate(int year, int month, int day){
		Flight current = firstFlight;
		Flight flightFound = null;
		boolean found = false;
		
		while(current != null && !found) {
			
			if(current.getDate().equals(new Date(day,month,year))) {
				found = true;
				flightFound = current;
			}

			current = current.getNextFlight();
		}
		
		return flightFound;
	}
	
	/**
	 * this method is responsible for finding a flight, according to a given departure time
	 * @param timeKey, the search criteria
	 * @return f, the flight found
	 */
	// linear search
	public Flight searchByTime(String timeKey) {
		Flight current = firstFlight;
		Flight flightFound = null;
		boolean found = false;
		
		while(current != null && !found) {
			
			if(current.getDepartureTime().equalsIgnoreCase(timeKey)) {
				found = true;
				flightFound = current;
			}

			current = current.getNextFlight();
		}
		
		return flightFound;
	}
	
	/**
	 * this method is responsible for finding a flight, according to a given airline
	 * @param airlineKey, the search criteria
	 * @return f, the flight found
	 */
	
	public Flight searchByAirline(String airlineKey) {
		Flight current = firstFlight;
		Flight flightFound = null;
		boolean found = false;
		
		while(current != null && !found) {
			
			if(current.getAirline().equalsIgnoreCase(airlineKey)) {
				found = true;
				flightFound = current;
			}

			current = current.getNextFlight();
		}
		
		return flightFound;
		
	}

	/**
	 * this method is responsible for finding a flight, according to a given destination
	 * @param destinationKey, the search criteria
	 * @return f, the flight found
	*/
	public Flight searchByDestination(String destinationKey) {
		Flight current = firstFlight;
		Flight flightFound = null;
		boolean found = false;
		
		while(current != null && !found) {
			
			if(current.getDestination().equalsIgnoreCase(destinationKey)) {
				found = true;
				flightFound = current;
			}

			current = current.getNextFlight();
		}
		
		return flightFound;
		
	
	}
	
	/**
	 * this method is responsible for finding a flight, according to a given boardin gate
	 * @param gateKey, the search criteria
	 * @return f, the flight found
	 */
	
	public Flight searchByGate(int gateKey) {
		Flight current = firstFlight;
		Flight flightFound = null;
		boolean found = false;
		
		while(current != null && !found) {
			
			if(current.getBoardingGate() == gateKey) {
				found = true;
				flightFound = current;
			}

			current = current.getNextFlight();
		}
		
		return flightFound;
	}
	/**
	 * this method is responsible for finding a flight, according to a flightNumber
	 * @param numberKey, the search criteria
	 * @return f, the flight found
	*/ 
	public Flight searchByFlightNumber(String numberKey) {
		
		Flight current = firstFlight;
		Flight flightFound = null;
		boolean found = false;
		
		while(current != null && !found) {
			
			if(current.getFlightNumber().equalsIgnoreCase(numberKey)) {
				found = true;
				flightFound = current;
			}

			current = current.getNextFlight();
		}
		
		return flightFound;
	
	}

	
	/**
	 * @return the firstFlight
	 */
	public Flight getFirstFlight() {
		return firstFlight;
	}


	/**
	 * @param firstFlight the firstFlight to set
	 */
	public void setFirstFlight(Flight firstFlight) {
		this.firstFlight = firstFlight;
	}
	
	
}
