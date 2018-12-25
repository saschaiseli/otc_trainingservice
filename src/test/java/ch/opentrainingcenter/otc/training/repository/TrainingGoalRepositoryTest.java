package ch.opentrainingcenter.otc.training.repository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.TrainingGoal;

class TrainingGoalRepositoryTest {

	private static final long ATHLETE_ID = 42L;
	@Mock
	private EntityManager em;
	@Mock
	private TypedQuery<TrainingGoal> tq;
	@Mock
	private TrainingGoal target;
	@Mock
	private Athlete athlete;

	private TrainingGoalRepository repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repository = new TrainingGoalRepository();
		repository.em = em;
	}

	@Test
	public void testFindByAthlete() {
		// Given
		when(em.createNamedQuery("TrainingGoal.findByAthlete", TrainingGoal.class)).thenReturn(tq);

		// When
		repository.findByAthlete(ATHLETE_ID);

		// Then
		verify(tq).setParameter("athleteId", ATHLETE_ID);
		verify(tq).getResultList();
		verifyNoMoreInteractions(tq);
	}

	@Test
	public void testStoreTarget() {
		// Given
		when(em.createNamedQuery("TrainingGoal.findByAthlete", TrainingGoal.class)).thenReturn(tq);
		when(em.find(Athlete.class, ATHLETE_ID)).thenReturn(athlete);

		// When
		repository.storeTrainingGoal(target, ATHLETE_ID);

		// Then
		verify(em).find(Athlete.class, ATHLETE_ID);
		verify(athlete).addTarget(target);

		verify(em).persist(athlete);
		verifyNoMoreInteractions(em);
		verifyNoMoreInteractions(tq);
	}

}
