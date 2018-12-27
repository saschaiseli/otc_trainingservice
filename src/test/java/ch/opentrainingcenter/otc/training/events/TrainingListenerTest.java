package ch.opentrainingcenter.otc.training.events;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.goal.TrainingGoalsUpdater;

class TrainingListenerTest {

	private TrainingListener listener;
	@Mock
	private TrainingRepository trainingRepo;
	@Mock
	private AthleteRepository athleteRepo;
	@Mock
	private TrainingEvent trainingEvent;
	@Mock
	private Training training;
	@Mock
	private Athlete athlete;
	@Mock
	private TrainingGoalsUpdater updater;

	private final String email = "testemail";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		listener = new TrainingListener();
		listener.trainingRepo = trainingRepo;
		listener.athleteRepo = athleteRepo;
		listener.updater = updater;
	}

	@Test
	public void testHappyCase() {
		when(trainingEvent.getTraining()).thenReturn(training);
		when(trainingEvent.getEmail()).thenReturn(email);
		when(athleteRepo.findByEmail(email)).thenReturn(athlete);
		final LocalDateTime date = LocalDateTime.now();
		when(training.getDateOfStart()).thenReturn(date);
		listener.onAddTraining(trainingEvent);

		verify(athleteRepo).findByEmail(email);
		verifyNoMoreInteractions(athleteRepo);

		verify(training).setAthlete(athlete);
		verify(trainingRepo).doSave(training);
		verify(updater).updateGoalsFor(training);
	}

	@Test
	public void testNullPointerWithEmptyConstructor() {
		listener = new TrainingListener();
		assertThrows(NullPointerException.class, () -> listener.onAddTraining(trainingEvent), "a message");
	}

}
