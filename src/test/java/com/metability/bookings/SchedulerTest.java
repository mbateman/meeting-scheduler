package com.metability.bookings;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SchedulerTest {

	private BoookingScheduler  schedule;
	
	@Before
	public void setUp() throws IOException {
		schedule = BoookingScheduler.initialize();
	}

	@Test
	public void shouldSetupBookingSchedule() throws IOException {
		assertEquals(3, schedule.entries().size());
		assertEquals("0900", schedule.getStartTime());
		assertEquals("1730", schedule.getEndTime());
	}

	@Test
	public void shouldResolveConflictsWithEarlierTimestamp() throws IOException {
		Map<String, String> booking = schedule.entries().get("2011-03-21 09:00");
		assertEquals("EMP002", booking.get("employeeId"));
	}
	
	@Test
	public void shouldNotSchedulelMeetingsPartlyFallingOutsideOfficeHours() {
		Map<String, String> booking = schedule.entries().get("2011-03-21 16:00");
		assertNull(booking);
	}
}
