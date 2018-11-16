package ch.opentrainingcenter.otc.training.repository;

import java.util.Collections;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;

class TrainingRepositoryTest {

	private static final String EMAIL = "test@opentrainingcenter.ch";

	private TrainingRepository repository;
	@Mock
	private EntityManager em;
	@Mock
	private Training training;
	@Mock
	private TypedQuery<Training> tq;
	@Mock
	private Athlete athlete;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repository = new TrainingRepository();
		repository.em = em;
	}

	@Test
	void testDoSave() {
		repository.doSave(training);

		Mockito.verify(training).setDateOfImport(Mockito.any(Date.class));
		Mockito.verify(em).persist(training);
	}

	@Test
	void testFindTrainingByAthlete() {
		final long key = 42;

		Mockito.when(em.createNamedQuery("Training.getTrainingByAthlete", Training.class)).thenReturn(tq);
		Mockito.when(em.find(Athlete.class, key)).thenReturn(athlete);

		repository.findTrainingByAthlete(key);

		Mockito.verify(tq).setParameter("athlete", athlete);
		Mockito.verify(tq).getResultList();
		Mockito.verifyNoMoreInteractions(tq);
		Mockito.verify(em).createNamedQuery("Training.getTrainingByAthlete", Training.class);
		Mockito.verify(em).find(Athlete.class, key);
		Mockito.verifyNoMoreInteractions(em);
	}

	@Test
	void testFindFullTraining() {
		final long key = 42;

		Mockito.when(em.find(Training.class, key)).thenReturn(training);
		Mockito.when(training.getTrackPoints()).thenReturn(Collections.emptyList());
		Mockito.when(training.getLapInfos()).thenReturn(Collections.emptyList());

		repository.findFullTraining(key);

		Mockito.verify(training).getTrackPoints();
		Mockito.verify(training).getLapInfos();
	}

}
