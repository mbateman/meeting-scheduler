package com.metability.bookings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SchedulerTest {

	private BoookingScheduler schedule;
	
	@Before
	public void setUp() throws IOException {
		schedule = BoookingScheduler.initialize();
	}

	@Test
	public void shouldSetupBookingSchedule() throws IOException {
		assertEquals(5, schedule.bookings().size());
		OfficeHours officeHours = schedule.getOfficeHours();
		assertEquals("0900", officeHours.getStartTime());
		assertEquals("1730", officeHours.getEndTime());
		assertEquals("EMP005", schedule.bookings().get(0).getEmployeeId());
		assertEquals("EMP004", schedule.bookings().get(schedule.bookings().size()-1).getEmployeeId());
		
	}

	@Test
	public void shouldResolveConflictsWithEarlierTimestamp() throws IOException {
		List<Booking> bookings = schedule.process();
		boolean hasBooking = bookings.stream().anyMatch(booking -> booking.getEmployeeId().equals("EMP002"));
		assertTrue(hasBooking);
	}
	
	@Test
	public void shouldNotSchedulelMeetingsPartlyFallingOutsideOfficeHours() {
		List<Booking> bookings = schedule.process();
		boolean hasBooking = bookings.stream().anyMatch(booking -> booking.getEmployeeId().equals("EMP005"));
		assertFalse(hasBooking);
	}
}
