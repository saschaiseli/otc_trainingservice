package ch.opentrainingcenter.otc.training.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.File;

import javax.inject.Inject;

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
import ch.opentrainingcenter.otc.training.domain.raw.Sport;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;

@RunWith(Arquillian.class)
public class AthleteRepositoryTestIT {

	private static final String EMAIL = "test@opentrainingcenter.ch";
	private static final String EMAIL_2 = "test2@opentrainingcenter.ch";
	@Inject
	private AthleteRepository repository;

	@Deployment
	public static WebArchive createDeployment() {
		final WebArchive archive = ShrinkWrap.create(WebArchive.class)
				.addClasses(RepositoryServiceBean.class, AthleteRepository.class).addPackage(Athlete.class.getPackage())
				.addPackage(Sport.class.getPackage()).addPackage(SimpleTraining.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

		final MavenResolverSystem resolver = Maven.resolver();
		final File[] files = resolver.loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
		archive.addAsLibraries(files);
		return archive;
	}

	@Before
	public void setUp() {
		final Athlete athlete = CommonTransferFactory.createAthlete("first name", "last name", EMAIL, "abc");
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

	@Test
	public void testFindById() {
		final Athlete athlete = repository.findByEmail(EMAIL);
		final Athlete athlete2 = repository.find(Athlete.class, athlete.getId());
		assertThat(athlete.getId(), is(equalTo(athlete2.getId())));
	}

	@Test
	public void testFindByEmailNotFound() {
		final Athlete athlete = repository.findByEmail(EMAIL_2);
		assertThat(athlete, is(nullValue()));
	}

	@Test
	public void testUpdate() {
		final Athlete athlete = repository.findByEmail(EMAIL);
		final String lastName = "new last name";
		athlete.setLastName(lastName);
		repository.update(athlete);

		final Athlete athlete2 = repository.findByEmail(EMAIL);

		assertThat(lastName, equalTo(athlete2.getLastName()));
	}

	@Test
	public void testRemove() {
		final Athlete athlete = repository.findByEmail(EMAIL);

		repository.remove(Athlete.class, athlete.getId());

		final Athlete athlete2 = repository.findByEmail(EMAIL);

		assertThat(athlete2, is(nullValue()));
	}
}
