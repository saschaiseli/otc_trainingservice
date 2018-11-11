package ch.opentrainingcenter.otc.training.service.converter.util;

import java.time.LocalDate;

public class Interval {
	private final LocalDate start;
	private final LocalDate end;

	private Interval(final LocalDate start, final LocalDate end) {
		this.start = start;
		this.end = end;
	}

	public static Interval of(final LocalDate start, final LocalDate end) {
		return new Interval(start, end);
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

}
