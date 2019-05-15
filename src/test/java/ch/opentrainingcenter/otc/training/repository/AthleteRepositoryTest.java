package ch.opentrainingcenter.otc.training.repository;

import ch.opentrainingcenter.otc.training.entity.Athlete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AthleteRepositoryTest {

    private static final String EMAIL = "test@opentrainingcenter.ch";

    private AthleteRepository repository;
    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<Athlete> tq;
    @Mock
    private Athlete athlete;

    @BeforeEach
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
}
