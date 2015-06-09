package com.metability.scheduler;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SubmissionReader {
	
	public static List<String> readBookingSubmissions() throws IOException {
	return bookingSubmissions()
		.stream()
		.collect(
			LinesCollector::new,
			LinesCollector::add,
			LinesCollector::combine)
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
