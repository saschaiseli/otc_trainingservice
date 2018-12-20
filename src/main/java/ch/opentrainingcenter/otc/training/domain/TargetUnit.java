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
		if (key == null || key.isEmpty() || DISTANCE_KM.key.equals(key)) {
			return DISTANCE_KM;
		} else {
			return DURATION_H;
		}
	}

}
