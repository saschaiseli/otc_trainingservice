package ch.opentrainingcenter.otc.training.repository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;

class TrainingRepositoryTest {

	private TrainingRepository repository;
	@Mock
	private EntityManager em;
	@Mock
	private Training training;
	@Mock
	private TypedQuery<Training> tq;
	@Mock
	private Athlete athlete;
	@Mock
	private TypedQuery<SimpleTraining> tqSimple;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repository = new TrainingRepository();
		repository.em = em;
	}

	@Test
	void testDoSave() {
		repository.doSave(training);

		verify(training).setDateOfImport(Mockito.any(LocalDate.class));
		verify(em).persist(training);
	}

	@Test
	void testFindTrainingByAthlete() {
		final long key = 42;

		when(em.createNamedQuery("Training.getTrainingByAthlete", Training.class)).thenReturn(tq);
		when(em.find(Athlete.class, key)).thenReturn(athlete);

		repository.findTrainingByAthlete(key);

		verify(tq).setParameter("athlete", athlete);
		verify(tq).getResultList();
		verifyNoMoreInteractions(tq);
		verify(em).createNamedQuery("Training.getTrainingByAthlete", Training.class);
		verify(em).find(Athlete.class, key);
		verifyNoMoreInteractions(em);
	}

	@Test
	void testFindFullTraining() {
		final long key = 42;

		when(em.find(Training.class, key)).thenReturn(training);
		when(training.getTrackPoints()).thenReturn(Collections.emptyList());
		when(training.getLapInfos()).thenReturn(Collections.emptyList());

		repository.findFullTraining(key);

		verify(training).getTrackPoints();
		verify(training).getLapInfos();
	}

	@Test
	void testFindSimpleTrainingByAthlete() {
		final long key = 42;
		when(em.createNamedQuery("Training.getSimpleTrainingByAthlete", SimpleTraining.class)).thenReturn(tqSimple);

		repository.findSimpleTrainingByAthlete(key);

		verify(tqSimple).setParameter("athleteId", key);
		verify(tqSimple).getResultList();
		verifyNoMoreInteractions(tqSimple);
	}

	@Test
	void testExistsFile() {
		final long athleteId = 42;
		final String fileName = "name.fit";
		when(em.createNamedQuery("Training.existsFileByAthlete", Training.class)).thenReturn(tq);

		repository.existsFile(athleteId, fileName);

		verify(tq).setParameter("athleteId", athleteId);
		verify(tq).setParameter("fileName", fileName);
		verify(tq).getResultList();
		verifyNoMoreInteractions(tq);
	}
}
