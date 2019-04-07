package ch.opentrainingcenter.otc.training.entity.raw;

import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.HeartRate;
import ch.opentrainingcenter.otc.training.entity.TrainingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NamedQuery(name = "Training.getTrainingByAthlete", query = "SELECT t FROM TRAINING t where t.athlete=:athlete order by t.id desc")
@NamedQuery(name = "Training.getSimpleTrainingByAthlete", query = "select new ch.opentrainingcenter.otc.training.dto.SimpleTraining(t.id,t.dauer,t.laengeInMeter,t.averageHeartBeat,t.maxHeartBeat,t.trainingEffect,t.anaerobTrainingEffect) FROM TRAINING t where t.athlete.id=:athleteId")
@NamedQuery(name = "Training.existsFileByAthlete", query = "select t from TRAINING t where t.athlete.id=:athleteId AND t.fileName=:fileName")
@NamedQuery(name = "Training.findByAthleteAndDate", query = "select new ch.opentrainingcenter.otc.training.dto.SimpleTraining(t.id,t.dauer,t.laengeInMeter,t.averageHeartBeat,t.maxHeartBeat,t.trainingEffect,t.anaerobTrainingEffect) from TRAINING t where t.athlete.id=:athleteId AND "
        + "t.dateOfStart>=:beginDate and t.dateOfStart<:endDate") //
@Cacheable
@Entity(name = "TRAINING")
public class Training {

    @Id
    @GeneratedValue
    private Long id;

//    private long startInMillis;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateOfStart;
    private long dauer;
    private long laengeInMeter;
    private int averageHeartBeat;
    private int maxHeartBeat;
    private double maxSpeed;
    private String note;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
    private Athlete athlete;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fk_training")
    private List<Tracktrainingproperty> trackPoints = new ArrayList<>();

    @Column(nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfImport;

    private Integer upMeter;

    private Integer downMeter;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LapInfo> lapInfos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @Column(name = "GEO_QUALITY")
    private Integer fehlerInProzent;

    @Column(name = "TRAINING_EFFECT")
    private Integer trainingEffect;

    @Column(name = "ANAEROB_TRAINING_EFFECT")
    private Integer anaerobTrainingEffect;

    @Lob
    @Column(name = "GEO_JSON")
    private String geoJson;

    public Training() {
    }

    public Training(final RunData runData, final HeartRate heart, final String remark) {
        final long startInMillis = runData.getDateOfStart().getTime();
        dateOfStart = Instant.ofEpochMilli(startInMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
        dauer = runData.getTimeInSeconds();
        laengeInMeter = runData.getDistanceInMeter();
        averageHeartBeat = heart.getAverage();
        maxHeartBeat = heart.getMax();
        maxSpeed = runData.getMaxSpeed();
        note = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

//    public long getStartInMillis() {
//        return startInMillis;
//    }
//
//    public void setStartInMillis(final long startInMillis) {
//        this.startInMillis = startInMillis;
//    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(final TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    @JsonIgnore
    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    @JsonIgnore
    public LocalDateTime getDateOfStart() {
        return dateOfStart;
    }

    public long getDauer() {
        return dauer;
    }

    public void setDauer(final long dauer) {
        this.dauer = dauer;
    }

    public long getLaengeInMeter() {
        return laengeInMeter;
    }

    public void setLaengeInMeter(final long laengeInMeter) {
        this.laengeInMeter = laengeInMeter;
    }

    public int getAverageHeartBeat() {
        return averageHeartBeat;
    }

    public void setAverageHeartBeat(final int averageHeartBeat) {
        this.averageHeartBeat = averageHeartBeat;
    }

    public int getMaxHeartBeat() {
        return maxHeartBeat;
    }

    public void setMaxHeartBeat(final int maxHeartBeat) {
        this.maxHeartBeat = maxHeartBeat;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(final double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public List<Tracktrainingproperty> getTrackPoints() {
        return trackPoints;
    }

    public void setTrackPoints(final List<Tracktrainingproperty> trackPoints) {
        this.trackPoints = trackPoints;
    }

    public LocalDate getDateOfImport() {
        return dateOfImport;
    }

    public void setDateOfImport(final LocalDate dateOfImport) {
        this.dateOfImport = dateOfImport;

    }

    public Integer getUpMeter() {
        return upMeter;
    }

    public void setUpMeter(final Integer upMeter) {
        this.upMeter = upMeter;
    }

    public Integer getDownMeter() {
        return downMeter;
    }

    public void setDownMeter(final Integer downMeter) {
        this.downMeter = downMeter;
    }

    public List<LapInfo> getLapInfos() {
        return Collections.unmodifiableList(lapInfos);
    }

    public void setLapInfos(final List<LapInfo> lapInfos) {
        this.lapInfos = lapInfos;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(final Sport sport) {
        this.sport = sport;
    }

    public Integer getGeoQuality() {
        return fehlerInProzent;
    }

    public void setGeoQuality(final Integer fehlerInProzent) {
        this.fehlerInProzent = fehlerInProzent;
    }

    public Integer getTrainingEffect() {
        return trainingEffect;
    }

    public void setTrainingEffect(final Integer trainingEffect) {
        this.trainingEffect = trainingEffect;
    }

    public void setAnaerobicTrainingEffect(final Integer anaerobTrainingEffect) {
        this.anaerobTrainingEffect = anaerobTrainingEffect;
    }

    public Integer getAnaerobicTrainingEffect() {
        return anaerobTrainingEffect;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setGeoJSON(final String geoJson) {
        this.geoJson = geoJson;
    }

    public String getGeoJSON() {
        return geoJson;
    }
}
