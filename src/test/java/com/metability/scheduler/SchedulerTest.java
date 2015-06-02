package com.metability.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.metability.scheduler.Booking;
import com.metability.scheduler.BookingScheduler;
import com.metability.scheduler.OfficeHours;

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
	public void bookingsMustBeProcessedInSubmissionOrder() {
		List<Booking> bookings = scheduler.orderBySubmissionDateTime().getBookings();
		assertEquals("EMP005", bookings.get(0).getEmployeeId());
		assertEquals("EMP004", bookings.get(bookings.size()-1).getEmployeeId());	
	}

	@Test
	public void shouldNotSchedulelMeetingsPartlyFallingOutsideOfficeHours() {
		List<Booking> bookings = scheduler.filterUnschedulableBookings().getBookings();
		assertEquals(4, bookings.size());
		boolean noBooking = bookings
				.stream()
				.anyMatch(booking -> booking.getEmployeeId().equals("EMP005"));
		assertFalse(noBooking);
		boolean doesHaveBooking = bookings
				.stream()
				.anyMatch(booking -> booking.getEmployeeId().equals("EMP001"));
		assertTrue(doesHaveBooking);
	}
	
	@Test
	public void shouldResolveConflictsWithEarlierTimestamp() throws IOException {
		assertEquals(5, scheduler.getBookings().size()); 
		List<Booking> bookings = scheduler
				.filterUnschedulableBookings()
				.orderBySubmissionDateTime()
				.resolveDoubleBookings()
				.getBookings();
		assertEquals(3, bookings.size());
		boolean doesNothaveBooking = bookings
				.stream()
				.anyMatch(booking -> booking.getEmployeeId().equals("EMP001"));
		assertFalse(doesNothaveBooking);
	}
	
}
