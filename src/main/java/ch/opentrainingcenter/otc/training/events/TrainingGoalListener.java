package ch.opentrainingcenter.otc.training.events;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Updated;
import ch.opentrainingcenter.otc.training.repository.TrainingGoalRepository;
import lombok.extern.slf4j.Slf4j;

@Stateless
@Slf4j
public class TrainingGoalListener {

	@Inject
	protected TrainingGoalRepository repo;

	public void onNewTrainingGoal(@Observes @Created final TrainingGoalDto event) {
		log.info("new TrainingGoal");
		repo.storeTrainingGoal(new TrainingGoal(event, null), event.getAthleteId());
	}

	public void onUpdatedTrainingGoal(@Observes @Updated final TrainingGoalDto event) {
		log.info("new TrainingGoal");

	}
}
