package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.service.converter.util.DistanceHelper;
import lombok.Getter;

@Getter
public class SimpleTraining {
	private final long id;
	private final long dauer;
	private final long laengeInMeter;
	private final int averageHeartBeat;
	private final int maxHeartBeat;
	private final double maxSpeed;
	private final String pace;

	public SimpleTraining(final Training t) {
		this(t.getId(), t.getDauer(), t.getLaengeInMeter(), t.getAverageHeartBeat(), t.getMaxHeartBeat(),
				t.getMaxSpeed());
	}

	public SimpleTraining(final long id, final long dauer, final long laengeInMeter, final int averageHeartBeat,
			final int maxHeartBeat, final double maxSpeed) {
		this.id = id;
		this.dauer = dauer;
		this.laengeInMeter = laengeInMeter;
		this.averageHeartBeat = averageHeartBeat;
		this.maxHeartBeat = maxHeartBeat;
		this.maxSpeed = maxSpeed;
		pace = DistanceHelper.calculateGeschwindigkeit(laengeInMeter, dauer);
	}
}
