package ch.opentrainingcenter.otc.training.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Distance in km or duration in hours.
 */
public enum TargetUnit {
	DISTANCE_KM("DISTANCE"), DURATION_H("HOURS");

	private final String key;

	private TargetUnit(final String key) {
		this.key = key;
	}

	@JsonValue
	public String getKey() {
		return key;
	}

	public static TargetUnit valueOfFromClient(final String key) {
		if (key == null) {
			throw new IllegalArgumentException("Not possible to create a TargetUnit with null key");
		}
		switch (key) {
		case "DISTANCE":
			return DISTANCE_KM;
		case "HOURS":
			return DURATION_H;
		default:
			throw new IllegalArgumentException(String.format("GoalDuration key '%s' is unknown", key));
		}
	}

}
