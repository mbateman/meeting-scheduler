package com.metability.bookings;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingPrinter {

	public static void printBookings(List<Booking> bookings) {
		StringBuilder schedule = new StringBuilder();
		bookings.stream()
				.sorted((b1, b2) -> b1.getStartDateTime()
				.compareTo(b2.getStartDateTime()))
				.forEach(booking -> {
					String date = booking.getStartDateTime().format(DateTimeFormatter.ISO_DATE);
					if (!schedule.toString().contains(date)) {
						schedule.append(date+"\n\n");
					}
					schedule.append(addMeeting(booking));
		});
		System.out.println(schedule.substring(0, schedule.length()-1));
	}

	private static String addMeeting(Booking booking) {
		return String.format("%02d:%02d %02d:%02d %s", 
			booking.getStartDateTime().getHour(), 
			booking.getStartDateTime().getMinute(), 
			booking.getStartDateTime().plusHours(booking.getDuration()).getHour(), 
			booking.getStartDateTime().plusHours(booking.getDuration()).getMinute(), 
			booking.getEmployeeId()+"\n\n");
	}
}
