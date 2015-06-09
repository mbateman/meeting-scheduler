package com.metability.scheduler;

import static java.time.LocalDateTime.of;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class BookingScheduler {

	private List<Booking> bookings;
	private OfficeHours officeHours;

	private BookingScheduler() {}
	
	public static BookingScheduler initialize() throws IOException {
		BookingScheduler bookingScheduler = new BookingScheduler();
		bookingScheduler.officeHours = new OfficeHours();
		bookingScheduler.bookings = readBookingSubmissions();
		return bookingScheduler;
	}
	
	public BookingScheduler filterUnschedulableBookings() {
		bookings = bookings
			.stream()
			.filter(booking -> !meetingCanBeScheduled(booking))
			.collect(toList());
		return this;
	}

	public BookingScheduler orderBySubmissionDateTime() {
		bookings = bookings
			.stream()
			.sorted((b1, b2) -> b1.getTimestamp().compareTo(b2.getTimestamp()))
			.collect(toList());
		return this;
	}
	
	public BookingScheduler filterDoubleBookings() {		
		List<Booking> filtered = bookings
			.stream()
			.distinct()
			.collect(toList());
		bookings = filtered;
		return this;
	}
	public List<Booking> getBookings() {
		return bookings;
	}

	public OfficeHours getOfficeHours() {
		return officeHours;
	}
	
	private static List<Booking> readBookingSubmissions() throws IOException {
		List<String> bookingSubmissions = BookingReader.readBookingSubmissions();
   		return bookingSubmissions
			.stream()
	        .map(mapToBooking)
	        .collect(toList());
     }
	
    private static Function<String, Booking> mapToBooking = (line) -> {
    	String[] tokens = line.split(" ");
	  	String timestamp = tokens[0] + " " + tokens[1];
	  	String employeeId = tokens[2];
	  	String startDateTime = tokens[3] + " " + tokens[4];
	  	String duration = tokens[5];
	  	return new Booking(employeeId, timestamp, startDateTime, duration);
  };

	private boolean meetingCanBeScheduled(Booking booking) {
		return notBeforeOfficeHours(booking) || notAfterOfficeHours(booking);
	}

	private boolean notBeforeOfficeHours(Booking booking) {
		LocalDateTime time = of(
				booking.getStartDateTime().getYear(), 
				booking.getStartDateTime().getMonth(), 
				booking.getStartDateTime().getDayOfMonth(), 
				officeHours.getStartTimeHours(), officeHours.getStartTimeMinutes());
		return booking.getStartDateTime().isBefore(time);
	}
	
	private boolean notAfterOfficeHours(Booking booking) {
		LocalDateTime time = of(
				booking.getStartDateTime().getYear(), 
				booking.getStartDateTime().getMonth(), 
				booking.getStartDateTime().getDayOfMonth(), 
				officeHours.getEndTimeHours(), officeHours.getEndTimeMinutes());
		return booking.getStartDateTime().plusHours(booking.getDuration())
				.isAfter(time);
	}

}
