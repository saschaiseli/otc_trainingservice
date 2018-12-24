package ch.opentrainingcenter.otc.training.events;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Updated;
import ch.opentrainingcenter.otc.training.repository.TargetRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Stateless
@Slf4j
@NoArgsConstructor
public class TrainingGoalListener {

	private TargetRepository repo;

	@Inject
	public TrainingGoalListener(final TargetRepository repo) {
		log.info("created by ejb container with injected values");
		this.repo = repo;
	}

	public void onNewTrainingGoal(@Observes @Created final TrainingGoalDto event) {
		log.info("new TrainingGoal");
		final TrainingGoal goal = new TrainingGoal();
		goal.setActive(event.isActive());
		goal.setBegin(event.getBegin());
		goal.setCurrentValue(event.getCurrentValue());
		goal.setDistanceOrHour(event.getDistanceOrHour());
		goal.setEnd(event.getEnd());
//		goal.setName(event.ge);
		repo.storeTarget(goal, event.getAthleteId());
	}

	public void onUpdatedTrainingGoal(@Observes @Updated final TrainingGoalDto event) {
		log.info("new TrainingGoal");

	}
}
