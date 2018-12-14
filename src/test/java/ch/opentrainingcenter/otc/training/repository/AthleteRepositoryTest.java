package ch.opentrainingcenter.otc.training.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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
		when(em.createNamedQuery("Athlete.findByEmail", Athlete.class)).thenReturn(tq);

		repository.findByEmail(EMAIL);

		verify(tq).setParameter("email", EMAIL);
		verify(tq).getSingleResult();
	}

	@Test
	public void testFindByEmailNotFound() {
		when(em.createNamedQuery("Athlete.findByEmail", Athlete.class)).thenReturn(tq);
		when(tq.getSingleResult()).thenThrow(NoResultException.class);

		final Athlete result = repository.findByEmail(EMAIL);

		verify(tq).setParameter("email", EMAIL);
		verify(tq).getSingleResult();

		assertThat(result, is(nullValue()));
	}

	@Test
	public void testDoSave() {
		repository.doSave(athlete);
		verify(em).persist(athlete);
	}

	@Test
	public void testUpdate() {
		repository.update(athlete);
		verify(em).merge(athlete);
	}

	@Test
	public void testRemove() {
		when(em.find(Athlete.class, 42L)).thenReturn(athlete);
		repository.remove(Athlete.class, 42L);

		verify(em).find(Athlete.class, 42L);
		verify(em).remove(athlete);
	}

	@Test
	public void testFind() {
		repository.find(Athlete.class, 42L);

		verify(em).find(Athlete.class, 42L);
	}

	@Test
	public void testAuthenticate() {
		final String password = "secret";
		when(em.createNamedQuery("Athlete.authenticate", Athlete.class)).thenReturn(tq);

		repository.authenticate(EMAIL, password);

		verify(tq).setParameter("email", EMAIL);
		verify(tq).setParameter("password", password);
		verify(tq).getSingleResult();
		verifyNoMoreInteractions(tq);
	}
}
