package com.metability.bookings;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BoookingScheduler {

	private List<Booking> bookings = new ArrayList<>();
	private OfficeHours officeHours;

	private BoookingScheduler() {}
	
	public static BoookingScheduler initialize() throws IOException {
		BoookingScheduler scheduler = new BoookingScheduler();
		scheduler.readEntries();
		Collections.sort(scheduler.bookings, new Comparator<Booking>() {
			@Override
			public int compare(Booking b1, Booking b2) {
				return b1.getTimestamp().compareTo(b2.getTimestamp());
			}
		});
		return scheduler;
	}
	
	public List<Booking> process() {
		return bookings.stream().filter(booking -> !meetingCanBeScheduled(booking)).collect(Collectors.toList());
	}
	
	public List<Booking> bookings() {
		return bookings;
	}

	public OfficeHours getOfficeHours() {
		return officeHours;
	}
	
	private void readEntries() throws IOException {
		BookingReader reader = new BookingReader();
		reader.readBookings();
		String startTime = reader.getOfficeHours().split(" ")[0];
        String endTime = reader.getOfficeHours().split(" ")[1];
        officeHours = new OfficeHours(startTime, endTime);
        for (String line : reader.getBookings()) {
        	String[] split = line.split(" ");
        	String timestamp = split[0].trim() + " "+ split[1].trim();
        	String employeeId = split[2].trim();
        	String startDateTime = split[3].trim() + " " + split[4].trim();
        	String duration = split[5].trim();
        	Booking booking = new Booking(employeeId, timestamp, startDateTime, duration);
        	bookings.add(booking);
        }
     }

	private boolean meetingCanBeScheduled(Booking booking) {
		LocalDateTime time = LocalDateTime.of(
				booking.getStartDateTime().getYear(), 
				booking.getStartDateTime().getMonth(), 
				booking.getStartDateTime().getDayOfMonth(), 
				officeHours.getEndTimeHours(), officeHours.getEndTimeMinutes());
		return booking.getStartDateTime().plusHours(booking.getDuration())
				.isAfter(time);
	}

}
