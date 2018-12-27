package ch.opentrainingcenter.otc.training.events;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.goal.TrainingGoalsUpdater;
import lombok.extern.slf4j.Slf4j;

@Stateless
@Slf4j
public class TrainingListener {
	@Inject
	protected TrainingRepository trainingRepo;
	@Inject
	protected AthleteRepository athleteRepo;
	@Inject
	protected TrainingGoalsUpdater updater;

	public void onAddTraining(@Observes @Created final TrainingEvent event) {
		final Athlete athlete = athleteRepo.findByEmail(event.getEmail());
		final Training training = event.getTraining();
		training.setAthlete(athlete);
		trainingRepo.doSave(training);
		updater.updateGoalsFor(training);
		log.info("Training created");
	}
}
