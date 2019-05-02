package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.entity.raw.Training;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class TrainingDto {
    private final long id;
    private final String geoJson;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate dateOfImport;
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
