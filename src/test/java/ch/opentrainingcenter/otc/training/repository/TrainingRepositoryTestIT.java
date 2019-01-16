package ch.opentrainingcenter.otc.training.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.TrainingCreator;
import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.domain.raw.Sport;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.service.converter.util.DistanceHelper;

@RunWith(Arquillian.class)
@Ignore
public class TrainingRepositoryTestIT {

	private static final String EMAIL = "test@opentrainingcenter.ch";

	@Inject
	private AthleteRepository repository;
	@Inject
	private TrainingRepository trainingRepo;
	private Athlete athlete;
	private Training training;

	@Deployment
	public static WebArchive createDeployment() {
		final WebArchive archive = ShrinkWrap.create(WebArchive.class)
				.addClasses(RepositoryServiceBean.class, AthleteRepository.class, TrainingRepository.class,
						TrainingCreator.class)
				.addPackage(Athlete.class.getPackage()).addPackage(Training.class.getPackage())
				.addClass(DistanceHelper.class).addPackage(SimpleTraining.class.getPackage())
				.addPackage(Sport.class.getPackage()).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

		final MavenResolverSystem resolver = Maven.resolver();
		final File[] files = resolver.loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
		archive.addAsLibraries(files);
		return archive;
	}

	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		final File jsonTraining = new File(TestConfig.FOLDER + "/training.json");

		training = objectMapper.readValue(jsonTraining, Training.class);
		athlete = CommonTransferFactory.createAthleteHashedPass("first name", "last name", EMAIL, "abc");
		athlete = repository.doSave(athlete);
		training.setDateOfImport(LocalDate.now());
		training.setAthlete(athlete);

		trainingRepo.doSave(training);
	}

	@After
	public void tearDown() {
		final Athlete athlete = repository.findByEmail(EMAIL);
		if (athlete != null) {
			repository.remove(Athlete.class, athlete.getId());
		}
	}

	@Test
	public void testFindByAthleteId() {
		final List<Training> trainings = trainingRepo.findTrainingByAthlete(athlete.getId());
		assertThat(trainings, is(not(empty())));
	}

	@Test
	public void testFindSimpleTrainingByAthleteId() {
		final List<SimpleTraining> trainings = trainingRepo.findSimpleTrainingByAthlete(athlete.getId());
		assertThat(trainings, is(not(empty())));
	}

	@Test
	public void testExistsTrainingByAthleteId() {
		final boolean exists = trainingRepo.existsFile(athlete.getId(), TestConfig.FIT_FILE);
		assertThat(exists, is(true));
	}

	@Test
	public void testExistsTrainingByAthleteIdNotFound() {
		final boolean exists = trainingRepo.existsFile(athlete.getId(), "schnabber.fit");
		assertThat(exists, is(false));
	}

	@Test
	public void testFindById() {
		final Training t = trainingRepo.findFullTraining(training.getId());
		assertThat(t, is(notNullValue()));
	}

	@Test
	public void testFindByAthleteIdAndDateBeginEqualDate() {
		final LocalDateTime dateOfStart = training.getDateOfStart();
		final List<SimpleTraining> trainings = trainingRepo.findByAthleteAndDate(athlete.getId(),
				dateOfStart.toLocalDate(), dateOfStart.plusDays(12).toLocalDate());
		assertThat(trainings, is(not(empty())));
	}

	@Test
	public void testFindByAthleteIdAndDateBeginEndDate() {
		final LocalDateTime date = training.getDateOfStart();
		final LocalDateTime begin = date.minusDays(1);
		final LocalDateTime end = date.plusDays(1);
		final List<SimpleTraining> trainings = trainingRepo.findByAthleteAndDate(athlete.getId(), begin.toLocalDate(),
				end.toLocalDate());
		assertThat(trainings, is(not(empty())));
	}

	@Test
	public void testFindByAthleteIdAndDateEqualsEndDate() {
		final LocalDateTime date = training.getDateOfStart();
		final LocalDateTime begin = date;
		final LocalDateTime end = date;
		final List<SimpleTraining> trainings = trainingRepo.findByAthleteAndDate(athlete.getId(), begin.toLocalDate(),
				end.toLocalDate());
		assertThat(trainings, is(empty()));
	}
}
