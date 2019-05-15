package ch.opentrainingcenter.otc.training.repository;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Log
public class TrainingRepositoryIT extends BaseIT {

    private static final String EMAIL = "test@opentrainingcenter.ch";

    private final AthleteRepository repository = new AthleteRepository();
    private final TrainingRepository tRepo = new TrainingRepository();

    private Athlete athlete;
    private Training training;


    @BeforeEach
    public void setUp() throws IOException {
        repository.em = getEntityManager();
        tRepo.em = getEntityManager();

        final ObjectMapper objectMapper = new ObjectMapper();
        final File jsonTraining = new File(TestConfig.FOLDER + "/training.json");

        training = objectMapper.readValue(jsonTraining, Training.class);
        athlete = CommonTransferFactory.createAthleteHashedPass("first name", "last name", EMAIL, "abc");
        final Athlete original = repository.findByEmail(EMAIL);
        if (original == null) {
            athlete = executeInTransaction((AthleteRepository r) -> r.doSave(athlete), repository);
        } else {
            athlete = original;
        }
        training.setDateOfImport(LocalDate.now());
        training.setAthlete(athlete);
        executeInTransaction((TrainingRepository r) -> r.doSave(training), tRepo);
    }

    @AfterEach
    public void tearDown() {
//        final Athlete athlete = repository.findByEmail(EMAIL);
//        if (athlete != null) {
////            executeInTransaction((AthleteRepository r) -> r.remove(Athlete.class, athlete.getId()), repository);
//        }
    }

    @Test
    public void testStore() throws IOException {
        // Given
        final File jsonTraining = new File(TestConfig.FOLDER + "/training.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        training = objectMapper.readValue(jsonTraining, Training.class);


        final Athlete original = repository.findByEmail(EMAIL);
        if (original == null) {
            athlete = repository.doSave(athlete);
        } else {
            athlete = original;
        }
        System.out.println("athlete ist null IM TESCHT--------------------? " + athlete);
        training.setAthlete(athlete);
        final Training newTraining = executeInTransaction((TrainingRepository r) -> r.doSave(training), tRepo);
        assertThat(newTraining.getId(), is(notNullValue()));
    }


    @Test
    public void testFindByAthleteId() {
        final List<Training> trainings = tRepo.findTrainingByAthlete(athlete.getId());
        assertThat(trainings, is(not(empty())));
    }

    @Test
    public void testFindSimpleTrainingByAthleteId() {
        final List<SimpleTraining> trainings = tRepo.findSimpleTrainingByAthlete(athlete.getId());
        assertThat(trainings, is(not(empty())));
    }

    @Test
    public void testExistsTrainingByAthleteId() {
        final boolean exists = tRepo.existsFile(athlete.getId(), TestConfig.FIT_FILE);
        assertThat(exists, is(true));
    }

    @Test
    public void testExistsTrainingByAthleteIdNotFound() {
        final boolean exists = tRepo.existsFile(athlete.getId(), "schnabber.fit");
        assertThat(exists, is(false));
    }

    @Test
    public void testFindById() {
        final Training t = tRepo.findFullTraining(training.getId());
        assertThat(t, is(notNullValue()));
    }

    @Test
    public void testFindByAthleteIdAndDateBeginEqualDate() {

        final LocalDateTime start = training.getDateOfStart();
        final long id = athlete.getId();
        final List<Training> all = tRepo.findTrainingByAthlete(id);
        final LocalDate beginDate = start.minusDays(1200).toLocalDate();
        final LocalDate endDate = start.plusDays(1200).toLocalDate();
        final List<SimpleTraining> trainings = tRepo.findByAthleteAndDate(id, beginDate, endDate);
        assertThat(trainings, is(not(empty())));
    }

    @Test
    public void testFindByAthleteIdAndDateBeginEndDate() {
        final LocalDateTime date = training.getDateOfStart();
        final LocalDateTime begin = date.minusDays(1);
        final LocalDateTime end = date.plusDays(1);
        final List<SimpleTraining> trainings = tRepo.findByAthleteAndDate(athlete.getId(), begin.toLocalDate(),
                end.toLocalDate());
        assertThat(trainings, is(not(empty())));
    }

    @Test
    public void testFindByAthleteIdAndDateEqualsEndDate() {
        final LocalDateTime date = training.getDateOfStart();
        final LocalDateTime begin = date;
        final LocalDateTime end = date;
        final List<SimpleTraining> trainings = tRepo.findByAthleteAndDate(athlete.getId(), begin.toLocalDate(),
                end.toLocalDate());
        assertThat(trainings, is(empty()));
    }
}
