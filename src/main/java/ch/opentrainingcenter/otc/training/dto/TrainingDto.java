package ch.opentrainingcenter.otc.training.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TrainingDto {
	private final long id;
	private final String geoJson;
	private final Date dateOfImport;
	private final String fileName;
	private final Integer geoQuality;
	private final String note;
	private final Integer upMeter;
	private final Integer downMeter;
	private final List<TracktrainingpropertyDto> trackPoints = new ArrayList<>();
	private final List<LapInfoDto> laps = new ArrayList<>();

	public TrainingDto(final Training t) {
		id = t.getId();
		dateOfImport = t.getDateOfImport();
		fileName = t.getFileName();
		geoQuality = t.getGeoQuality();
		note = t.getNote();
		upMeter = t.getUpMeter();
		downMeter = t.getDownMeter();
		geoJson = t.getGeoJSON();
		trackPoints.addAll(t.getTrackPoints().stream().map(TracktrainingpropertyDto::new).collect(Collectors.toList()));
		laps.addAll(t.getLapInfos().stream().map(LapInfoDto::new).collect(Collectors.toList()));
	}
}
