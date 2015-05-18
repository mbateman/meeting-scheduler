package com.metability.bookings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SchedulerTest {

	private BookingScheduler scheduler;
	
	@Before
	public void setUp() throws IOException {
		scheduler = BookingScheduler.initialize();
	}

	@Test
	public void shouldSetupBookingSchedule() throws IOException {
		assertEquals(5, scheduler.getBookings().size());
		OfficeHours officeHours = scheduler.getOfficeHours();
		assertEquals("0900", officeHours.getStartTime());
		assertEquals("1730", officeHours.getEndTime());
		assertEquals("EMP001", scheduler.getBookings().get(0).getEmployeeId());
		assertEquals("EMP005", scheduler.getBookings().get(scheduler.getBookings().size()-1).getEmployeeId());
	}

	@Test
	public void shouldResolveConflictsWithEarlierTimestamp() throws IOException {
		assertEquals(5, scheduler.getBookings().size()); 
		List<Booking> bookings = scheduler.process();
		assertEquals(4, bookings.size());
		boolean noBooking = bookings
				.stream()
				.anyMatch(booking -> booking.getEmployeeId().equals("EMP005"));
		assertFalse(noBooking);
		boolean doesHaveBooking = bookings
				.stream()
				.anyMatch(booking -> booking.getEmployeeId().equals("EMP001"));
		assertTrue(doesHaveBooking);
		bookings = scheduler.resolveDoubleBookings();
		assertEquals(3, bookings.size());
		boolean doesNothaveBooking = bookings
				.stream()
				.anyMatch(booking -> booking.getEmployeeId().equals("EMP001"));
		assertFalse(doesNothaveBooking);
	}
	
	@Test
	public void shouldNotSchedulelMeetingsPartlyFallingOutsideOfficeHours() {
		assertEquals(5, scheduler.getBookings().size());
		List<Booking> bookings = scheduler.process();
		boolean hasBooking = bookings
				.stream()
				.anyMatch(booking -> booking.getEmployeeId().equals("EMP005"));
		assertFalse(hasBooking);
	}
	
}
