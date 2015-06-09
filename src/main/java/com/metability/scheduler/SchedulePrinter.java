package com.metability.scheduler;

import java.util.List;

public class SchedulePrinter {

	public static void printSchedule(List<Meeting> bookings) {
		StringBuilder schedule = bookings
			.stream()
			.sorted((b1, b2) -> b1.getStartDateTime().compareTo(b2.getStartDateTime()))
			.reduce(new StringBuilder(), (builder, booking) -> {
				String date = booking.getFormattedStartDateTime();
				if (!builder.toString().contains(date)) {
					builder.append(date + "\n\n");
				}
				builder.append(booking.meetingFormat());
				return builder;
			}, (left, right) -> left.append(right));
		System.out.println(schedule.substring(0, schedule.length()-1));
	}

}
