package com.metability.scheduler;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class BookingReaderTest {

	@Test
	public void shouldHaveFiveBookings() throws IOException {
		List<String> bookings = BookingReader.readBookingSubmissions();
		assertThat(bookings.size(), is(5));
	}

}
