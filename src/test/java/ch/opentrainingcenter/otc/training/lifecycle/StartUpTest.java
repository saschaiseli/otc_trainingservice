package ch.opentrainingcenter.otc.training.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.boundary.security.BCryptService;
import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;

class StartUpTest {
	private static final String EMAIL = "sascha.iseli@gmx.ch";
	private StartUp startUp;
	@Mock
	private AthleteRepository athleteRepo;
	@Mock
	private Athlete athlete;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		startUp = new StartUp();
		startUp.repo = athleteRepo;
		startUp.cryptService = new BCryptService();
	}

	@Test
	void testUserExists() {
		// Given
		when(athleteRepo.findByEmail(EMAIL)).thenReturn(athlete);

		// when
		startUp.init();

		// then
		verify(athleteRepo).findByEmail(EMAIL);
		Mockito.verifyNoMoreInteractions(athleteRepo);
	}

	@Test
	void testUserDoesNotExists() {
		// Given
		when(athleteRepo.findByEmail(EMAIL)).thenReturn(null);

		// when
		startUp.init();

		// then
		verify(athleteRepo).findByEmail(EMAIL);
		verify(athleteRepo).doSave(any());
		Mockito.verifyNoMoreInteractions(athleteRepo);
	}
}
