package com.metability.bookings;

import java.time.format.DateTimeFormatter;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class BookingPrinter {

	public static void printBookings(List<Booking> bookings) {
		List<Booking> bookingsByStartDateTime = bookings
				.stream()
				.sorted((b1, b2) -> b1.getStartDateTime().compareTo(b2.getStartDateTime())).collect(toList());
		String previousDate = "";
		for (Booking booking : bookingsByStartDateTime) {
			String date = booking.getStartDateTime().format(DateTimeFormatter.ISO_DATE);
			if (!previousDate.equals(date)) {
				System.out.println(date+"\n");
			}
			System.out.println(
				String.format("%02d:%02d %02d:%02d %s", 
					booking.getStartDateTime().getHour(), 
					booking.getStartDateTime().getMinute(), 
					booking.getStartDateTime().plusHours(booking.getDuration()).getHour(), 
					booking.getStartDateTime().plusHours(booking.getDuration()).getMinute(), 
					booking.getEmployeeId())+"\n");
			previousDate = date;
		}
	}
}
