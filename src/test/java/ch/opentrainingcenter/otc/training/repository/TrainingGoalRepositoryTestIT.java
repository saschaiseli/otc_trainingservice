package ch.opentrainingcenter.otc.training.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.domain.Settings;
import ch.opentrainingcenter.otc.training.domain.Speed;
import ch.opentrainingcenter.otc.training.domain.SystemOfUnit;
import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import ch.opentrainingcenter.otc.training.domain.raw.Sport;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;

@RunWith(Arquillian.class)
public class TrainingGoalRepositoryTestIT {

	private static final String EMAIL = "test@opentrainingcenter.ch";
	@Inject
	private AthleteRepository repository;

	@Inject
	private TrainingGoalRepository targetRepository;
	private Athlete athlete;

	@Deployment
	public static WebArchive createDeployment() {
		final WebArchive archive = ShrinkWrap.create(WebArchive.class)
				.addClasses(RepositoryServiceBean.class, AthleteRepository.class, TrainingGoalRepository.class)
				.addPackage(Athlete.class.getPackage()).addPackage(Sport.class.getPackage())
				.addPackage(SimpleTraining.class.getPackage()).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

		final MavenResolverSystem resolver = Maven.resolver();
		final File[] files = resolver.loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
		archive.addAsLibraries(files);
		return archive;
	}

	@Before
	public void setUp() {
		athlete = CommonTransferFactory.createAthleteHashedPass("first name", "last name", EMAIL, "abc");
		athlete.setSettings(Settings.of(SystemOfUnit.METRIC, Speed.PACE));
		athlete = repository.doSave(athlete);
	}

	@After
	public void tearDown() {
		final Athlete athlete = repository.findByEmail(EMAIL);
		if (athlete != null) {
			repository.remove(Athlete.class, athlete.getId());
		}
	}

	@Test
	public void testFindByAthleteNoDataFound() {
		final List<TrainingGoal> targets = targetRepository.findByAthlete(athlete.getId());

		assertThat(targets, Matchers.empty());
	}

	@Test
	public void testFindByAthleteDataFound() {
		final TrainingGoal nt = new TrainingGoal();
		nt.setBegin(LocalDate.now());
		nt.setDistanceOrHour(42);
		nt.setAthlete(athlete);

		targetRepository.storeTrainingGoal(nt, athlete.getId());

		final List<TrainingGoal> targets = targetRepository.findByAthlete(athlete.getId());

		assertThat(targets.get(0).getId(), is(Matchers.equalTo(nt.getId())));
	}

	@Test
	public void testFindByAthleteAndDataBeginEqualsDate() {
		final TrainingGoal goal = new TrainingGoal();
		final LocalDate begin = LocalDate.of(2018, 12, 22);
		final LocalDate end = LocalDate.of(2018, 12, 29);

		goal.setBegin(begin);
		goal.setEnd(end);
		goal.setDistanceOrHour(42);
		goal.setAthlete(athlete);

		targetRepository.storeTrainingGoal(goal, athlete.getId());

		final List<TrainingGoal> targets = targetRepository.findTrainingGoalsByAthleteAndDate(athlete.getId(), begin);

		assertThat(targets.get(0).getId(), is(Matchers.equalTo(goal.getId())));
	}

	@Test
	public void testFindByAthleteAndDataDateBetweenBeginEnd() {
		final TrainingGoal goal = new TrainingGoal();
		final LocalDate begin = LocalDate.of(2018, 12, 22);
		final LocalDate date = LocalDate.of(2018, 12, 25);
		final LocalDate end = LocalDate.of(2018, 12, 29);

		goal.setBegin(begin);
		goal.setEnd(end);
		goal.setDistanceOrHour(42);
		goal.setAthlete(athlete);

		targetRepository.storeTrainingGoal(goal, athlete.getId());

		final List<TrainingGoal> targets = targetRepository.findTrainingGoalsByAthleteAndDate(athlete.getId(), date);

		assertThat(targets.get(0).getId(), is(Matchers.equalTo(goal.getId())));
	}

	@Test
	public void testFindByAthleteAndDataEndEqualsDate() {
		final TrainingGoal goal = new TrainingGoal();
		final LocalDate begin = LocalDate.of(2018, 12, 22);
		final LocalDate end = LocalDate.of(2018, 12, 29);

		goal.setBegin(begin);
		goal.setEnd(end);
		goal.setDistanceOrHour(42);
		goal.setAthlete(athlete);

		targetRepository.storeTrainingGoal(goal, athlete.getId());

		final List<TrainingGoal> targets = targetRepository.findTrainingGoalsByAthleteAndDate(athlete.getId(), end);

		assertThat(0, is(targets.size()));
	}
}
