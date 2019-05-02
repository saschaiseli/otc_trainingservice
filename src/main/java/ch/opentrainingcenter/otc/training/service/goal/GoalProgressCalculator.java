package ch.opentrainingcenter.otc.training.service.goal;

import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.entity.TargetUnit;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class GoalProgressCalculator {

    public double calculateTrainingGoalProgress(final TrainingGoalDto dto, final List<SimpleTraining> trainings) {
        double result = 0;
        if (TargetUnit.DURATION_H.equals(dto.getUnit())) {
            result = trainings.stream().mapToDouble(SimpleTraining::getTimeInSeconds).sum();
            result = result / 3600d;
        } else {
            result = trainings.stream().mapToDouble(SimpleTraining::getDistanceInKm).sum();
        }
        return result;
    }

}
