package com.metability.scheduler;

import java.util.ArrayList;
import java.util.List;

public class LinesCollector {
	
	
	private List<Pair> pairs = new ArrayList<>();
	
	private String previousLine;
	
	public List<Pair> lines() {
		return pairs;
	}
	
	public void add(String string) {
		if(previousLine != null) {
			pairs.add(new Pair(previousLine, string));
			previousLine = null;
		}
		else {
			previousLine = string;
		}
	}
	
	public LinesCollector combine(LinesCollector other) {
		throw new RuntimeException("Not implemented");
	}

}
