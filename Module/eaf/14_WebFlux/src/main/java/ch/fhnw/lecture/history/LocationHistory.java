package ch.fhnw.lecture.history;

import java.time.LocalDate;
import java.util.Random;

public class LocationHistory {
	private final static Random r = new Random();
	
	public final int x;
	public final int y;
	public final LocalDate date;
	
	public LocationHistory(LocalDate date) {
		this.date = date;
		x = r.nextInt();
		y = r.nextInt();
	}
}
