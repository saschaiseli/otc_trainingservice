package ch.opentrainingcenter.otc.training.repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;

public class AthleteRepositoryTest {

	private static final String EMAIL = "test@opentrainingcenter.ch";

	private AthleteRepository repository;
	@Mock
	private EntityManager em;
	@Mock
	private TypedQuery<Athlete> tq;
	@Mock
	private Athlete athlete;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repository = new AthleteRepository();
		repository.em = em;
	}

	@Test
	public void testFindByEmail() {
		Mockito.when(em.createNamedQuery("Athlete.findByEmail", Athlete.class)).thenReturn(tq);

		repository.findByEmail(EMAIL);

		Mockito.verify(tq).setParameter("email", EMAIL);
		Mockito.verify(tq).getSingleResult();
	}

	@Test
	public void testDoSave() {
		repository.doSave(athlete);
		Mockito.verify(em).persist(athlete);
	}

	@Test
	public void testUpdate() {
		repository.update(athlete);
		Mockito.verify(em).merge(athlete);
	}

	@Test
	public void testRemove() {
		Mockito.when(em.find(Athlete.class, 42L)).thenReturn(athlete);
		repository.remove(Athlete.class, 42L);

		Mockito.verify(em).find(Athlete.class, 42L);
		Mockito.verify(em).remove(athlete);
	}

	@Test
	public void testFind() {
		repository.find(Athlete.class, 42L);

		Mockito.verify(em).find(Athlete.class, 42L);
	}
}
