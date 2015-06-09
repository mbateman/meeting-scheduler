package com.metability.scheduler;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Meeting {

	private String employeeId;
	private LocalDateTime submissionDateTime;
	private LocalDateTime startDateTime;
	private int duration;

	public Meeting(String employeeId, String timestamp, String startDateTime, String duration) {
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

	public String getFormattedStartDateTime() {
		return startDateTime.format(DateTimeFormatter.ISO_DATE);
	}

	public int getDuration() {
		return duration;
	}
	
	public String meetingFormat() {
		return String.format("%02d:%02d %02d:%02d %s", 
			getStartDateTime().getHour(), 
			getStartDateTime().getMinute(), 
			getStartDateTime().plusHours(getDuration()).getHour(), 
			getStartDateTime().plusHours(getDuration()).getMinute(), 
			getEmployeeId()+"\n\n");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((startDateTime == null) ? 0 : startDateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meeting other = (Meeting) obj;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		return true;
	}
	
}
