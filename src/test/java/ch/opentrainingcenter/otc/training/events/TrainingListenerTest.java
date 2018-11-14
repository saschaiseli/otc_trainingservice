package ch.opentrainingcenter.otc.training.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;

class TrainingListenerTest {

	private TrainingListener listener;
	@Mock
	private TrainingRepository trainingRepo;
	@Mock
	private AthleteRepository athleteRepo;

	@Mock
	private Training training;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		listener = new TrainingListener(trainingRepo, athleteRepo);
	}

	@Test
	void test() {
		listener.onAddTraining(training);
		Mockito.verify(athleteRepo).doSave(Mockito.any());
		Mockito.verify(training).setAthlete(Mockito.any());
		Mockito.verify(trainingRepo).doSave(training);
	}

}
