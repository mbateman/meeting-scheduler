package com.metability.bookings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingReader {

	private List<String> bookings = new ArrayList<>();
	private String officeHours;
	
	public List<String> getBookings() {
		return bookings;
	}

	public String getOfficeHours() {
		return officeHours;
	}
	
	public void readBookings() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("resources/schedule")));
        officeHours = reader.readLine();
        String firstLine;
        String secondLine;
        while ((firstLine = reader.readLine()) != null) {
        	if (!firstLine.isEmpty()) {
        		secondLine = reader.readLine();
        		if (secondLine.isEmpty())
        			secondLine = reader.readLine();
        		bookings.add(firstLine + " " + secondLine);
        	}
        }
        reader.close();
	}
}
