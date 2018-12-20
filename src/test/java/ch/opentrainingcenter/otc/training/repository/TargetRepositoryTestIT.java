package ch.opentrainingcenter.otc.training.repository;

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
import ch.opentrainingcenter.otc.training.domain.Duration;
import ch.opentrainingcenter.otc.training.domain.Settings;
import ch.opentrainingcenter.otc.training.domain.Speed;
import ch.opentrainingcenter.otc.training.domain.SystemOfUnit;
import ch.opentrainingcenter.otc.training.domain.Target;
import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.domain.raw.Sport;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;

@RunWith(Arquillian.class)
public class TargetRepositoryTestIT {

	private static final String EMAIL = "test@opentrainingcenter.ch";
	@Inject
	private AthleteRepository repository;

	@Inject
	private TargetRepository targetRepository;
	private Athlete athlete;

	@Deployment
	public static WebArchive createDeployment() {
		final WebArchive archive = ShrinkWrap.create(WebArchive.class)
				.addClasses(RepositoryServiceBean.class, AthleteRepository.class, TargetRepository.class)
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
		final List<Target> targets = targetRepository.findByAthlete(athlete.getId());

		assertThat(targets, Matchers.empty());
	}

	@Test
	public void testFindByAthleteDataFound() {
		final Target nt = new Target();
		nt.setTargetBegin(LocalDate.now());
		nt.setDistanceOrHours(42);
		nt.setAthlete(athlete);
		nt.setGoalUnit(TargetUnit.DISTANCE_KM);
		nt.setDuration(Duration.MONTH);

		targetRepository.storeTarget(nt, athlete.getId());

		final List<Target> targets = targetRepository.findByAthlete(athlete.getId());

		assertThat(targets.get(0).getId(), Matchers.is(Matchers.equalTo(nt.getId())));
	}

}
