package com.metability.bookings;

import java.io.BufferedReader;
import java.io.File;
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
}
