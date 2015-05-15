package com.metability.bookings;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SchedulerTest {

	private Schedule  schedule;
	
	@Before
	public void setUp() throws IOException {
		schedule = Schedule.initialize();
	}

	@Test
	public void shouldSetupBookingSchedule() throws IOException {
		assertEquals(4, schedule.entries().size());
		assertEquals("0900", schedule.getStartTime());
		assertEquals("1730", schedule.getEndTime());
	}

}
