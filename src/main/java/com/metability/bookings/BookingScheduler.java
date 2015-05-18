package com.metability.bookings;

import static java.time.LocalDateTime.of;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingScheduler {

	private List<Booking> bookings = new ArrayList<>();;
	private OfficeHours officeHours;

	private BookingScheduler() {}
	
	public static BookingScheduler initialize() throws IOException {
		BookingScheduler bookingScheduler = new BookingScheduler();
		bookingScheduler.readBookingSubmissions();
		return bookingScheduler;
	}
	
	public List<Booking> process() {
		bookings = bookings
			.stream()
			.filter(booking -> !meetingCanBeScheduled(booking))
			.sorted((b1, b2) -> b1.getTimestamp().compareTo(b2.getTimestamp()))
			.collect(toList());
		return bookings;
	}
	
	public List<Booking> resolveDoubleBookings() {		
		List<Booking> duplicates = new ArrayList<>(bookings);
		for (int i=0; i < bookings.size() - 1; i++) {
			if (bookings.get(i).getStartDateTime().equals(bookings.get(i+1).getStartDateTime())) {
				duplicates.remove(i+1);
			}
		}
		return duplicates;
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}

	public OfficeHours getOfficeHours() {
		return officeHours;
	}
	
	private void readBookingSubmissions() throws IOException {
		List<String> bookingSubmissions = BookingReader.readBookingSubmissions();
		officeHours = BookingReader.getOfficeHours();
        for (String line : bookingSubmissions) {
        	String[] tokens = line.split(" ");
        	String timestamp = tokens[0].trim() + " "+ tokens[1].trim();
        	String employeeId = tokens[2].trim();
        	String startDateTime = tokens[3].trim() + " " + tokens[4].trim();
        	String duration = tokens[5].trim();
        	Booking booking = new Booking(employeeId, timestamp, startDateTime, duration);
        	bookings.add(booking);
        }
     }

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