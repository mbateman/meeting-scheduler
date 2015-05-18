package com.metability.bookings;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDateTime;

public class Booking {

	private String employeeId;
	private LocalDateTime submissionDateTime;
	private LocalDateTime startDateTime;
	private int duration;

	public Booking(String employeeId, String timestamp, String startDateTime, String duration) {
		this.employeeId = employeeId;
		this.submissionDateTime = parse(timestamp, ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.startDateTime = parse(startDateTime, ofPattern("yyyy-MM-dd HH:mm"));
		this.duration = Integer.parseInt(duration);
	}

	public String getEmployeeId() {
		return employeeId;
	}
	
	public LocalDateTime getTimestamp() {
		return submissionDateTime;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	
	public int getDuration() {
		return duration;
	}
}