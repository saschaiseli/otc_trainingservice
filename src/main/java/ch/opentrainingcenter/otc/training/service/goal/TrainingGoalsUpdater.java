package ch.opentrainingcenter.otc.training.service.goal;

import ch.opentrainingcenter.otc.training.entity.TargetUnit;
import ch.opentrainingcenter.otc.training.entity.TrainingGoal;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.repository.TrainingGoalRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Stateless
@Slf4j
public class TrainingGoalsUpdater {

    @Inject
    protected TrainingGoalRepository goalRepository;

    @Inject
    protected TrainingRepository trainingRepository;

    public void updateGoalsFor(final Training training) {
        log.info("Update training goals for {}", training.getAthlete().getId());

        final List<TrainingGoal> goals = goalRepository.findTrainingGoalsByAthleteAndDate(training.getAthlete().getId(),
                training.getDateOfStart().toLocalDate());
        final int hours = (int) TimeUnit.SECONDS.toHours(training.getDauer());
        final int km = (int) (training.getLaengeInMeter() / 1000d);

        for (final TrainingGoal goal : goals) {
            if (TargetUnit.DISTANCE_KM.equals(goal.getUnit())) {
                goal.setCurrentValue(goal.getCurrentValue() + km);
            } else {
                goal.setCurrentValue(goal.getCurrentValue() + hours);
            }
        }
        if (!goals.isEmpty()) {
            goalRepository.storeTrainingGoals(goals, training.getAthlete().getId());
        }
    }

}
