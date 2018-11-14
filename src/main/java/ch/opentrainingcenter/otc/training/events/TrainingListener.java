package ch.opentrainingcenter.otc.training.events;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;

@Stateless
public class TrainingListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingListener.class);

	private final TrainingRepository trainingRepo;

	private final AthleteRepository athleteRepo;

	// EJB Container uses an empty Constructor
	public TrainingListener() {
		this(null, null);
	}

	@Inject
	public TrainingListener(final TrainingRepository trainingRepo, final AthleteRepository athleteRepo) {
		this.trainingRepo = trainingRepo;
		this.athleteRepo = athleteRepo;
	}

	public void onAddTraining(@Observes @Created final Training training) {
		training.setAthlete(getOrCreateAthlete());
		trainingRepo.doSave(training);
		LOGGER.info("Training created");
	}

	private Athlete getOrCreateAthlete() {
		Athlete athlete = athleteRepo.findByEmail("sascha.iseli@gmx.ch");
		if (athlete == null) {
			athlete = new Athlete("sascha", "iseli", "sascha.iseli@gmx.ch", "secret");
			athlete = athleteRepo.doSave(athlete);
		}
		return athlete;
	}
}
