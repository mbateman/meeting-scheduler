package com.metability.bookings;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BookingPrinterTest {
	
	private BookingScheduler scheduler;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	@Before
	public void setUp() throws IOException {
		scheduler = BookingScheduler.initialize();
		System.setOut(new PrintStream(outContent));
	}

	@Test
	public void shouldPrintSchedule() {
		assertEquals(5, scheduler.getBookings().size());
		List<Booking> bookings = scheduler.process();
		bookings = scheduler.resolveDoubleBookings();
		BookingPrinter.printBookings(bookings);
		String expecteOutput = "2011-03-21\n\n" + "09:00 11:00 EMP002\n\n" +
				"2011-03-22\n\n" +
				"14:00 16:00 EMP003\n\n" + "16:00 17:00 EMP004\n\n";
		assertEquals(expecteOutput, outContent.toString());
	}

}
