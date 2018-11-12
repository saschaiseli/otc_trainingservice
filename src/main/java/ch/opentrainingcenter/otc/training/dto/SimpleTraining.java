package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.domain.raw.Training;

public class SimpleTraining {
	private final long id;
	private final long dauer;
	private final long laengeInMeter;
	private final int averageHeartBeat;
	private final int maxHeartBeat;
	private final double maxSpeed;

	public SimpleTraining(final Training training) {
		id = training.getId();
		dauer = training.getDauer();
		laengeInMeter = training.getLaengeInMeter();
		averageHeartBeat = training.getAverageHeartBeat();
		maxHeartBeat = training.getMaxHeartBeat();
		maxSpeed = training.getMaxSpeed();
	}

	public long getId() {
		return id;
	}

	public long getDauer() {
		return dauer;
	}

	public long getLaengeInMeter() {
		return laengeInMeter;
	}

	public int getAverageHeartBeat() {
		return averageHeartBeat;
	}

	public int getMaxHeartBeat() {
		return maxHeartBeat;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

}
