package ch.opentrainingcenter.otc.training.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 7 Days, 30-day, month or a year.
 */
public enum GoalDuration {
	SEVEN_DAYS("WEEK", 7), THIRTY_DAYS("30DAYS", 30), MONTH("MONTH", -1), YEAR("YEAR", 365);

	private final String key;
	private final int days;

	private GoalDuration(final String key, final Integer days) {
		this.key = key;
		this.days = days;
	}

	@JsonValue
	public String getKey() {
		return key;
	}

	public int getDays() {
		return days;
	}

	public static GoalDuration valueOfFromClient(final String key) {
		if (key == null) {
			return GoalDuration.SEVEN_DAYS;
		}
		switch (key) {
		case "WEEK":
			return GoalDuration.SEVEN_DAYS;
		case "30DAYS":
			return GoalDuration.THIRTY_DAYS;
		case "MONTH":
			return GoalDuration.MONTH;
		case "YEAR":
			return GoalDuration.YEAR;
		default:
			throw new IllegalArgumentException(String.format("GoalDuration key '%s' is unknown", key));
		}
	}

}
