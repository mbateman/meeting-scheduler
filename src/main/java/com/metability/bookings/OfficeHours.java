package com.metability.bookings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OfficeHours {

	private String startTime;
	private String endTime;
	private Integer endTimeHours;
	private Integer endTimeMinutes;
	private Integer startTimeHours;
	private Integer startTimeMinutes;

	public OfficeHours() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("resources/schedule")));
        String officeHoursEntry = reader.readLine();
		reader.close();
        this.startTime = officeHoursEntry.split(" ")[0];
		this.endTime =  officeHoursEntry.split(" ")[1];
		this.startTimeHours = Integer.parseInt(startTime.substring(0,2));
		this.startTimeMinutes = Integer.parseInt(startTime.substring(2,4));
		this.endTimeHours = Integer.parseInt(endTime.substring(0,2));
		this.endTimeMinutes = Integer.parseInt(endTime.substring(2,4));
	}
	
	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public int getEndTimeHours() {
		return endTimeHours;
	}
	
	public int getEndTimeMinutes() {
		return endTimeMinutes;
	}
	
	public int getStartTimeHours() {
		return startTimeHours;
	}
	
	public int getStartTimeMinutes() {
		return startTimeMinutes;
	}
	
}
