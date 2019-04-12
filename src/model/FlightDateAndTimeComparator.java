package model;

import java.util.Comparator;

public class FlightDateAndTimeComparator implements Comparator<Flight>{
	
	/**
	 * this method is responsible for comparing two flight objects according to their dates and times of departure
	 */
	@Override
	public int compare(Flight one, Flight other) {
		int comparation = 0;
		if(one.getDate().getYear() < other.getDate().getYear()){
			comparation = -1;
		}else if(one.getDate().getYear() > other.getDate().getYear()) {
			comparation = 1;
		}else {
			if(one.getDate().getMonth() < other.getDate().getMonth()){
			comparation = -1;
			}else if(one.getDate().getMonth() > other.getDate().getMonth()) {
			comparation = 1;
			}else {
				if(one.getDate().getDay() > other.getDate().getDay()) {
					comparation = 1;
				}else if(one.getDate().getDay() < other.getDate().getDay()){
					comparation = -1;
				}else {
					String format1 =  one.getDepartureTime().substring(6);
					String format2 =  other.getDepartureTime().substring(6);
					if(format1.equals("AM") && format2.equals("PM")) {
						comparation = -1;
					}else if(format1.equals("PM") && format2.equals("AM")) {
						comparation = 1;
					}else {
						String t1 = one.getDepartureTime().substring(0,5);
						String t2 = other.getDepartureTime().substring(0,5);
					
						double time1 = Double.parseDouble(t1);
						double time2 = Double.parseDouble(t2);
						
						if( time1 > time2) {
							comparation = 1;
						}else if( time1 < time2){
							comparation = -1;
						}else {
							comparation = 0;
						}
				}
			}
		}
		
		}
		return comparation;
	}
	
}
