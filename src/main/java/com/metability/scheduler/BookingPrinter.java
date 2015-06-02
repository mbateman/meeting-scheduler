package com.metability.scheduler;

import java.util.List;

public class BookingPrinter {

	public static void printBookingsReducer(List<Booking> bookings) {
		StringBuilder schedule = bookings
			.stream()
			.sorted((b1, b2) -> b1.getStartDateTime().compareTo(b2.getStartDateTime()))
			.reduce(new StringBuilder(), (builder, booking) -> {
				String date = booking.getFormattedStartDateTime();
				if (!builder.toString().contains(date)) {
					builder.append(date + "\n\n");
				}
				builder.append(addMeeting(booking));
				return builder;
			}, (left, right) -> left.append(right));
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
