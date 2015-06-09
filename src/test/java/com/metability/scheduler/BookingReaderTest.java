package com.metability.scheduler;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class BookingReaderTest {

	@Test
	public void test() throws IOException {
		List<String> bookings = BookingReader.readBookingSubmissions();
		List<String> bookingsB = BookingReader.readBookingSubmissionsBuilder();
		List<String> bookingsR = BookingReader.readBookingSubmissionsReduce();
		List<String> bookingsC = BookingReader.readBookingSubmissionsCollect();
		System.out.println(bookings);
		System.out.println(bookingsB);
		System.out.println(bookingsR);
		System.out.println(bookingsC);
	}

}
