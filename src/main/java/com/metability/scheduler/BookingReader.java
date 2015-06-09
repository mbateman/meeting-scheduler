package com.metability.scheduler;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingReader {
	
	public static List<String> readBookingSubmissions() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("resources/schedule")));
        reader.readLine();
		String firstLine;
        String secondLine;
        List<String> bookings = new ArrayList<>();
        while ((firstLine = reader.readLine()) != null) {
        	if (!firstLine.isEmpty()) {
        		secondLine = reader.readLine();
        		if (secondLine.isEmpty())
        			secondLine = reader.readLine();
        		bookings.add(firstLine + " " + secondLine);
        	}
        }
        reader.close();
		return bookings;
	}
	
	public static List<String> readBookingSubmissionsBuilder() throws IOException {
		List<String> submissions = bookingSubmissions();
		StringBuilder builder = new StringBuilder();
		List<String> bookings = new ArrayList<>();
		for (String submission : submissions) {
			if (builder.length() == 0) {
				builder.append(submission);
			} else {
				bookings.add(builder.toString() + " " + submission);
				builder.delete(0, builder.length());
			} 
		}
		return bookings;
	}

	public static List<String> readBookingSubmissionsReduce() throws IOException {
		List<String> bookings = new ArrayList<>();
		bookingSubmissions()
		.stream()
		.reduce(new StringBuilder(), (builder, submission) -> {
			if (builder.length() == 0) {
				builder.append(submission);
			} else {
				bookings.add(builder.toString() + " " + submission);
				builder.delete(0, builder.length());
			} 
			return builder;
		}, (left, right) -> left.append(right));
		return bookings;
	}
	
	public static List<String> readBookingSubmissionsCollect() throws IOException {
	LinesCollector collector = bookingSubmissions()
		.stream()
		.collect(
			LinesCollector::new,
			LinesCollector::add,
			LinesCollector::combine
	);
	return collector
			.lines()
			.stream()
			.map(pair -> pair.toString())
			.collect(toList());
	}
	
	private static List<String> bookingSubmissions() throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("resources/schedule")));
		List<String> entries = reader
			.lines()
			.skip(1)
			.filter(s -> s.length() > 0)
			.collect(toList());
		reader.close();
		return entries;
	}
}
