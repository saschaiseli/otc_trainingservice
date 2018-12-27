package ch.opentrainingcenter.otc.training.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import ch.opentrainingcenter.otc.training.domain.GoalDuration;
import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TrainingGoalDto {
	private final int distanceOrHour;
	private final TargetUnit unit;
	private final GoalDuration duration;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private final LocalDate begin;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private final LocalDate end;

	// calculated field
	private long id;
	private boolean isActive;
	private double progress;
	private double currentValue;
	@JsonIgnore
	private long athleteId;

	public TrainingGoalDto(final TrainingGoal entity) {
		this.id = entity.getId();
		this.distanceOrHour = entity.getDistanceOrHour();
		this.unit = null;
		this.duration = null;
		this.begin = entity.getBegin();
		this.end = entity.getEnd();
		this.isActive = entity.isActive();
		this.currentValue = entity.getCurrentValue();
		this.progress = this.currentValue / this.distanceOrHour * 100;
	}
}
