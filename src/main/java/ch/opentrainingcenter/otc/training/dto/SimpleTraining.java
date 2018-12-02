package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.service.converter.util.DistanceHelper;
import lombok.Getter;

@Getter
public class SimpleTraining {
	private static final String UNDEF = "-";
	private final long id;
	private final long timeInSeconds;
	private final double distanceInKm;
	private final int avgHeartBeat;
	private final int maxHeartBeat;
	private final String trainingEffect;
	private final String anaerobTrainingEffect;
	private final String pace;

	public SimpleTraining(final Training t) {
		this(t.getId(), t.getDauer(), t.getLaengeInMeter(), t.getAverageHeartBeat(), t.getMaxHeartBeat(),
				t.getTrainingEffect(), t.getAnaerobicTrainingEffect());
	}

	public SimpleTraining(final long id, final long dauer, final long laengeInMeter, final int averageHeartBeat,
			final int maxHeartBeat, final Integer trainingEffect, final Integer anaerobTrainingEffect) {
		this.id = id;
		this.timeInSeconds = dauer;
		this.distanceInKm = laengeInMeter / 1000d;
		this.avgHeartBeat = averageHeartBeat;
		this.maxHeartBeat = maxHeartBeat;
		this.trainingEffect = trainingEffect != null ? trainingEffect.toString() : UNDEF;
		this.anaerobTrainingEffect = anaerobTrainingEffect != null ? anaerobTrainingEffect.toString() : UNDEF;
		pace = DistanceHelper.calculateGeschwindigkeit(laengeInMeter, dauer);
	}
}
