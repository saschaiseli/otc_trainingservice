package ch.opentrainingcenter.otc.training.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.service.converter.util.DistanceHelper;
import lombok.Getter;

@Getter
public class TrainingDto {
	private final long id;
	private final long timeInSeconds;
	private final double distanceInKm;
	private final int avgHeartBeat;
	private final int maxHeartBeat;
	private final String pace;
	private final Integer trainingEffect;
	private final Integer anaerobTrainingEffect;
	private final String geoJson;
	private final List<TracktrainingpropertyDto> trackPoints = new ArrayList<>();
	private final List<LapInfoDto> lapInfos = new ArrayList<>();

	public TrainingDto(final Training training) {
		this.id = training.getId();
		this.timeInSeconds = training.getDauer();
		final long laengeInMeter = training.getLaengeInMeter();
		this.distanceInKm = laengeInMeter / 1000d;
		this.avgHeartBeat = training.getAverageHeartBeat();
		this.maxHeartBeat = training.getMaxHeartBeat();
		this.pace = DistanceHelper.calculateGeschwindigkeit(laengeInMeter, timeInSeconds);
		this.trainingEffect = training.getTrainingEffect();
		this.anaerobTrainingEffect = training.getAnaerobicTrainingEffect();
		this.geoJson = training.getGeoJSON();
		trackPoints.addAll(
				training.getTrackPoints().stream().map(TracktrainingpropertyDto::new).collect(Collectors.toList()));
		lapInfos.addAll(training.getLapInfos().stream().map(LapInfoDto::new).collect(Collectors.toList()));
	}
}
