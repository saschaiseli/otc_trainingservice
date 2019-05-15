package ch.opentrainingcenter.otc.training.service.goal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.ejb.Stateless;

import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;

@Stateless
public class GoalPredictionCalculator {

	public TrainingGoalDto predictTrainingGoal(final TrainingGoalDto dto, final LocalDate now) {
		if (dto.isActive()) {
			final long daysTillNow = ChronoUnit.DAYS.between(dto.getBegin(), now) + 1;
			final double avg = dto.getCurrentValue() / daysTillNow;
			final long days = ChronoUnit.DAYS.between(dto.getBegin(), dto.getEnd()) + 1;
			dto.setPrediction(avg * days);
		} else {
			dto.setPrediction(dto.getCurrentValue());
		}
		return dto;
	}
}
