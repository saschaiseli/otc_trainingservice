package ch.opentrainingcenter.otc.training.repository;

import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.entity.*;
import ch.opentrainingcenter.otc.training.entity.raw.Sport;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
//@Ignore
public class AthleteRepositoryIT {

    private static final String EMAIL = "test@opentrainingcenter.ch";
    private static final String EMAIL_2 = "test2@opentrainingcenter.ch";
    @Inject
    private AthleteRepository repository;

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive archive = ShrinkWrap.create(WebArchive.class)
                .addClasses(RepositoryServiceBean.class, AthleteRepository.class).addPackage(Athlete.class.getPackage()).addPackage(Training.class.getPackage())
                .addPackage(Sport.class.getPackage()).addPackage(SimpleTraining.class.getPackage())
                .addPackage(TrainingGoalDto.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

        return archive;
    }

    @Before
    public void setUp() {
        final Athlete athlete = CommonTransferFactory.createAthleteHashedPass("first name", "last name", EMAIL, "abc");
        athlete.setSettings(Settings.of(SystemOfUnit.METRIC, Speed.PACE));
        repository.doSave(athlete);
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
