/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Icesi University (Cali - Colombia)
 * Faculty of Engineering (algorithms and programming 2)
 * laboratory 4
 * By: Carlos Andrés Restrepo Marín 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

package model;

public class Date implements Comparable<Date>{
	
	/**
	 * represent the day of the flight 
	 */
	private int day;
	/**
	 * represent the month of the flight 
	 */
	private int month;
	/**
	 * represent the year of the flight 
	 */
	private int year;
	
	/**
	 * Initialize a new Date, and generate a random values
	 * @param d, integer that represent the day of the flight
	 * @param m, integer that represent the month of the flight
	 * @param y, integer that represent the year of the flight
	 */
	public Date(int d, int m, int y) {
		if(d != 0 && m != 0 && y !=0) {
			this.day = d;
			this.month = m;
			this.year = y;
		}
		else {
			int mon = 1 + (int)(Math.random() * (12));	
			int da = 0;
			if((mon == 1) || (mon == 3) || (mon == 5) || (mon == 7) || (mon == 8) || (mon == 10) || (mon == 12)) {
				 da = 1 + (int)(Math.random() * (31));
				
			}else if(mon == 2) {
				 da = 1 + (int)(Math.random() * (28));
			}else {
				 da = 1 + (int)(Math.random() * (30));
			}
			int ye = 2010 + (int)(Math.random() * (9));
			
			month = mon;
			day = da;
			year = ye;
		}
	}
	
	/**
	 * <b>Description:</b>
		* allows to obtain the day of the flight
	 * @return day, the day of the flight
	 */
	public int getDay() {
		return day;
	}
	/**
	 * <b>Description:</b>
		* allows to obtain the month of the flight
	 * @return month, the month of the flight
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * <b>Description:</b>
		* allows to obtain the year of the flight
	 * @return year, the year of the flight
	 */
	public int getYear() {
		return year;
	}

	/**
	 * This method is responsible for comparing this date with another according to the attributes
	 * @return comparation, the value of the comparation
	 */
	@Override
	public int compareTo(Date other) {
		int comparation = 0;
		if(getYear() < other.getYear()) {
			comparation = -1;
		}else if(getYear() > other.getYear()) {
			comparation =1;
		}else {
			if(getMonth() < other.getMonth()){
			comparation = -1;
			}else if(getMonth() > other.getMonth()) {
				comparation = 1;
			}else {
				if(getDay() > other.getDay()) {
					comparation = 1;
				}else if(getDay() < other.getDay()){
					comparation = -1;
				}
			}
		}
		return comparation;
	}
	
	
	/**
	 * This method allows you to compare if an object is the same
	 * @param other the other date object to compare
	 * @return true if the other object is the same
	 */
	public boolean equals(Date other) {
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	/**
	 * allows to obtain the attributes of the class in a string
	 * 
	 */
	@Override
	public String toString() {
		
		return year +"-" + month +"-" + day;
	}
}
