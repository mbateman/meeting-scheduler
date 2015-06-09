package com.metability.scheduler;

import static java.time.LocalDateTime.of;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class MeetingScheduler {

	private List<Meeting> bookings;
	private OfficeHours officeHours;

	private MeetingScheduler() {}
	
	public static MeetingScheduler initialize() throws IOException {
		MeetingScheduler bookingScheduler = new MeetingScheduler();
		bookingScheduler.officeHours = new OfficeHours();
		bookingScheduler.bookings = readBookingSubmissions();
		return bookingScheduler;
	}
	
	public MeetingScheduler filterUnschedulableBookings() {
		bookings = bookings
			.stream()
			.filter(booking -> !meetingCanBeScheduled(booking))
			.collect(toList());
		return this;
	}

	public MeetingScheduler orderBySubmissionDateTime() {
		bookings = bookings
			.stream()
			.sorted((b1, b2) -> b1.getTimestamp().compareTo(b2.getTimestamp()))
			.collect(toList());
		return this;
	}
	
	public MeetingScheduler filterDoubleBookings() {		
		List<Meeting> filtered = bookings
			.stream()
			.distinct()
			.collect(toList());
		bookings = filtered;
		return this;
	}
	public List<Meeting> getBookings() {
		return bookings;
	}

	public OfficeHours getOfficeHours() {
		return officeHours;
	}
	
	private static List<Meeting> readBookingSubmissions() throws IOException {
		List<String> bookingSubmissions = SubmissionReader.readBookingSubmissions();
   		return bookingSubmissions
			.stream()
	        .map(mapToBooking)
	        .collect(toList());
     }
	
    private static Function<String, Meeting> mapToBooking = (line) -> {
    	String[] tokens = line.split(" ");
	  	String timestamp = tokens[0] + " " + tokens[1];
	  	String employeeId = tokens[2];
	  	String startDateTime = tokens[3] + " " + tokens[4];
	  	String duration = tokens[5];
	  	return new Meeting(employeeId, timestamp, startDateTime, duration);
  };

	private boolean meetingCanBeScheduled(Meeting booking) {
		return notBeforeOfficeHours(booking) || notAfterOfficeHours(booking);
	}

	private boolean notBeforeOfficeHours(Meeting booking) {
		LocalDateTime time = of(
				booking.getStartDateTime().getYear(), 
				booking.getStartDateTime().getMonth(), 
				booking.getStartDateTime().getDayOfMonth(), 
				officeHours.getStartTimeHours(), officeHours.getStartTimeMinutes());
		return booking.getStartDateTime().isBefore(time);
	}
	
	private boolean notAfterOfficeHours(Meeting booking) {
		LocalDateTime time = of(
				booking.getStartDateTime().getYear(), 
				booking.getStartDateTime().getMonth(), 
				booking.getStartDateTime().getDayOfMonth(), 
				officeHours.getEndTimeHours(), officeHours.getEndTimeMinutes());
		return booking.getStartDateTime().plusHours(booking.getDuration())
				.isAfter(time);
	}

}
