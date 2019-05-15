package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.entity.GoalDuration;
import ch.opentrainingcenter.otc.training.entity.TargetUnit;
import ch.opentrainingcenter.otc.training.entity.TrainingGoal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class TrainingGoalDto {
    private final int distanceOrHour;
    private final TargetUnit unit;
    private final GoalDuration duration;
    private final LocalDate begin;
    private final LocalDate end;

    // calculated field
    private long id;
    private boolean isActive;
    private double progress;
    private double currentValue;
    private double prediction;
    @JsonIgnore
    private long athleteId;

    public TrainingGoalDto(final TrainingGoal entity) {
        id = entity.getId();
        distanceOrHour = entity.getDistanceOrHour();
        unit = null;
        duration = null;
        begin = entity.getDerStart();
        end = entity.getDasEnde();
        isActive = entity.isActive();
        currentValue = entity.getCurrentValue();
        progress = currentValue / distanceOrHour * 100;
    }
}
