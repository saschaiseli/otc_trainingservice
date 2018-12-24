package ch.opentrainingcenter.otc.training.service.goal;

import java.util.List;

import javax.ejb.Stateless;

import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;

@Stateless
public class GoalProgressCalculator {

	public double calculateTrainingGoalProgress(final TrainingGoalDto dto, final List<SimpleTraining> trainings) {
		double result = 0;
		if (TargetUnit.DURATION_H.equals(dto.getUnit())) {
			result = trainings.stream().mapToDouble(x -> x.getTimeInSeconds()).sum();
			result = result / 3600d;
		} else {
			result = trainings.stream().mapToDouble(x -> x.getDistanceInKm()).sum();
		}
		return getProgress(result, dto.getDistanceOrHour());
	}

	private double getProgress(final double result, final int distanceOrHours) {
		return (result / distanceOrHours) * 100;
	}

}
