package com.metability.scheduler;

public class Pair {
	
	private final String line1;
	private final String line2;
	
	public Pair(String line1, String line2) {
		this.line1 = line1;
		this.line2 = line2;
	}
	
	public String toString() {
		return line1 + " " + line2;
	}

}
