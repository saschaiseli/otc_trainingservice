package ch.opentrainingcenter.otc.training.events;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;

@Stateless
@Slf4j
public class TrainingListener {

	private final TrainingRepository trainingRepo;

	private final AthleteRepository athleteRepo;

	public TrainingListener() {
		this(null, null);
		log.info("created by ejb container");
	}

	@Inject
	public TrainingListener(final TrainingRepository trainingRepo, final AthleteRepository athleteRepo) {
		log.info("created by ejb container with injected values");
		this.trainingRepo = trainingRepo;
		this.athleteRepo = athleteRepo;
	}

	public void onAddTraining(@Observes @Created final TrainingEvent event) {
		final Athlete athlete = athleteRepo.findByEmail(event.getEmail());
		final Training training = event.getTraining();
		training.setAthlete(athlete);
		trainingRepo.doSave(training);
		log.info("Training created");
	}
}
