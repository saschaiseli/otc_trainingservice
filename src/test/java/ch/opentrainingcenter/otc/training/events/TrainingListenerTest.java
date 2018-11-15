package ch.opentrainingcenter.otc.training.events;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;
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

	@Mock
	private Athlete athlete;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		listener = new TrainingListener(trainingRepo, athleteRepo);
	}

	@Test
	void testHappyCase() {
		listener.onAddTraining(training);
		verify(athleteRepo).doSave(any());
		verify(training).setAthlete(any());
		verify(trainingRepo).doSave(training);
	}

	@Test
	void testAthleteIsNull() {
		when(athleteRepo.findByEmail(any())).thenReturn(athlete);
		listener.onAddTraining(training);
		verify(athleteRepo).findByEmail(any());
		verifyNoMoreInteractions(athleteRepo);
		verify(training).setAthlete(any());
		verify(trainingRepo).doSave(training);
	}

}
