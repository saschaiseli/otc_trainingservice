package ch.opentrainingcenter.otc.training.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 7 Days, 30-day, month or a year.
 */
public enum Duration {
	SEVEN_DAYS("WEEK"), THIRTY_DAYS("30DAYS"), MONTH("MONTH"), YEAR("YEAR");

	private final String key;

	private Duration(final String key) {
		this.key = key;
	}

	@JsonValue
	public String getKey() {
		return key;
	}

	public static Duration valueOfFromClient(final String key) {
		if (key == null) {
			return Duration.SEVEN_DAYS;
		}
		switch (key) {
		case "WEEK":
			return Duration.SEVEN_DAYS;
		case "30DAYS":
			return Duration.THIRTY_DAYS;
		case "MONTH":
			return Duration.MONTH;
		case "YEAR":
			return Duration.YEAR;
		default:
			return SEVEN_DAYS;

		}
	}
}
