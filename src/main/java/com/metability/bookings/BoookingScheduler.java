package com.metability.bookings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BoookingScheduler {

	private Map<String, Map<String,String>> entries = new HashMap<>();
	private String startTime;
	private String endTime;
	
	private BoookingScheduler() {}
	
	public static BoookingScheduler initialize() throws IOException {
		BoookingScheduler schedule = new BoookingScheduler();
		schedule.readEntries();
		return schedule;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	} 
	
	public Map<String, Map<String,String>> entries() {
		return entries;
	}
	
	private void readEntries() throws IOException {
		BookingReader reader = new BookingReader();
		reader.readBookings();
		String officeHours = reader.getOfficeHours();
		startTime = officeHours.split(" ")[0];
        endTime = officeHours.split(" ")[1];
        for (String line : reader.getBookings()) {
        	String[] split = line.split(" ");
        	Map<String, String> details = new HashMap<>();
        	String timestamp = split[0].trim() + " "+ split[1].trim();
        	String employeeId = split[2].trim();
			details.put("timestamp", timestamp);
			details.put("employeeId", employeeId);
        	String startDate = split[3].trim();
        	String startTime = split[4].trim();
        	String duration= split[5].trim();
        	details.put("duration", duration);
        	if (meetingCanBeScheduled(startTime, duration)) {
        		String startDateTime =  startDate + " " + startTime ;
        		entries.put(startDateTime, details);
        	}
        }
     }

	private boolean meetingCanBeScheduled(String startTime, String duration) {
		int start = Integer.parseInt(startTime.substring(0, startTime.indexOf(":")));
		int length = Integer.parseInt(duration);
		return (start + length) * 100 <= Integer.parseInt(endTime);
	}
}
