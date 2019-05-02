package ch.opentrainingcenter.otc.training.repository;

import ch.opentrainingcenter.otc.training.entity.*;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Log
public class AthleteRepositoryIT extends BaseIT {

    private static final String EMAIL = "test@opentrainingcenter.ch";
    private final AthleteRepository repository = new AthleteRepository();


    @BeforeEach
    public void setUp() {
        repository.em = getEntityManager();
        final Athlete athlete = CommonTransferFactory.createAthleteHashedPass("first name", "last name", EMAIL, "abc");
        athlete.setSettings(Settings.of(SystemOfUnit.METRIC, Speed.PACE));

        executeInTransaction((AthleteRepository r) -> r.doSave(athlete), repository);
    }

    @After
    public void tearDown() {
        final Athlete athlete = repository.findByEmail(EMAIL);
        if (athlete != null) {
            repository.remove(Athlete.class, athlete.getId());
        }
    }

    @Test
    public void testFindByEmailFound() {
        final Athlete athlete = repository.findByEmail(EMAIL);
        assertThat(athlete, is(not(nullValue())));
        assertThat(athlete.getSettings(), is(Settings.of(SystemOfUnit.METRIC, Speed.PACE)));
    }

}
