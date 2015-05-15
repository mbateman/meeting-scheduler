package com.metability.bookings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Schedule {

	private Map<String, Map<String,String>> entries = new HashMap<>();
	private String startTime;
	private String endTime;
	
	private Schedule() {}
	
	public static Schedule initialize() throws IOException {
		Schedule schedule = new Schedule();
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
        BufferedReader reader = new BufferedReader(new FileReader(new File("resources/schedule")));
        String officeHours = reader.readLine();
        startTime = officeHours.split(" ")[0];
        endTime = officeHours.split(" ")[1];
        String line;
        while ((line = reader.readLine()) != null) {
        	if (!line.isEmpty()) {
	        	String[] split = line.split(" ");
	        	Map<String, String> details = new HashMap<>();
	        	String timestamp = split[0].trim() + " "+ split[1].trim();
	        	String employeeId = split[2].trim();
				details.put("timestamp", timestamp);
				details.put("employeeId", employeeId);
	        	line = reader.readLine();
	        	if (line.isEmpty())
	        		line = reader.readLine();
	        	split = line.split(" ");
	        	String startDateTime = split[0].trim() + " "+ split[1].trim();
	        	details.put("duration", split[2].trim());        	
	        	entries.put(startDateTime, details);
        	}
        }
        reader.close();	
     }
}
