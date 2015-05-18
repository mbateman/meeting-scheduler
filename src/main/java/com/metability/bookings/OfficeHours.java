package com.metability.bookings;

public class OfficeHours {

	private String startTime;
	private String endTime;
	private Integer endTimeHours;
	private Integer endTimeMinutes;
	private Integer startTimeHours;
	private Integer startTimeMinutes;

	public OfficeHours(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
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
