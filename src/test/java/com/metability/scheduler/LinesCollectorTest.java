package com.metability.scheduler;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import org.junit.Test;

public class LinesCollectorTest {

	LinesCollector linesCollector = new LinesCollector();
	
	@Test
	public void addBookingToCollector_givenLine1AndLine2() {
		String line1 = "x";
		String line2 = "y";
		linesCollector.add(line1);
		linesCollector.add(line2);
//		assertThat(linesCollector.line1, ));
		assertThat(linesCollector.lines().size(), is(1));
		
	}
	
	@Test
	public void populatesCollectorWithOneBooking_givenTwoLinesInStream() {
		List<String> list = Arrays.asList("2011-03-15 17:29:12 EMP005", "2011-03-21 16:00 3");
		LinesCollector collect = list.stream().collect(
				LinesCollector::new,
				LinesCollector::add,
				LinesCollector::combine
				);
		assertNotNull(collect);
		assertThat(collect.lines().size(), is(1));
	}

	@Test
	public void populatesCollectorWithFiveBookingsGivenTenLinesInStream() throws FileNotFoundException, IOException {
		List<String> list = bookingSubmissions();
		LinesCollector collect = list.stream().collect(
				LinesCollector::new,
				LinesCollector::add,
				LinesCollector::combine
				);
		assertNotNull(collect);
		assertThat(collect.lines().size(), is(5));
	}

	private List<String> bookingSubmissions() throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("resources/schedule")));
		List<String> entries = reader
			.lines()
			.skip(1)
			.filter(s -> s.length() > 0)
			.collect(toList());
		reader.close();
		return entries;
	}

}
