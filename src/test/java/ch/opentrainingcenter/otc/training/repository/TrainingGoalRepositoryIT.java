package ch.opentrainingcenter.otc.training.repository;

import ch.opentrainingcenter.otc.training.entity.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrainingGoalRepositoryIT extends BaseIT {

    private static final String EMAIL = "test@opentrainingcenter.ch";
    private final AthleteRepository repository = new AthleteRepository();

    private final TrainingGoalRepository targetRepository = new TrainingGoalRepository();
    private Athlete athlete;


    @BeforeEach
    public void setUp() {
        repository.em = getEntityManager();
        targetRepository.em = getEntityManager();
        athlete = CommonTransferFactory.createAthleteHashedPass("first name", "last name", EMAIL, "abc");
        athlete.setSettings(Settings.of(SystemOfUnit.METRIC, Speed.PACE));
        athlete = executeInTransaction((AthleteRepository r) -> r.doSave(athlete), repository);
    }

    @AfterEach
    public void tearDown() {
        final Athlete athlete = repository.findByEmail(EMAIL);
        if (athlete != null) {
            executeInTransaction((AthleteRepository r) -> r.remove(Athlete.class, athlete.getId()), repository);
        }
    }

    @Test
    public void testFindByAthleteNoDataFound() {
        // Given

        // When
        final List<TrainingGoal> targets = targetRepository.findByAthlete(athlete.getId());

        // Then
        assertThat(targets, Matchers.empty());
    }

    @Test
    public void testFindByAthleteDataFound() {
        // Given
        final TrainingGoal nt = new TrainingGoal();
        nt.setDerStart(LocalDate.now());
        nt.setDistanceOrHour(42);
        nt.setAthlete(athlete);
        executeInTransaction((TrainingGoalRepository r) -> r.storeTrainingGoal(nt, athlete.getId()), targetRepository);

        // When
        final List<TrainingGoal> targets = targetRepository.findByAthlete(athlete.getId());

        // Then
        assertThat(targets.get(0).getId(), is(Matchers.equalTo(nt.getId())));
    }

    @Test
    public void testFindByAthleteAndDataBeginEqualsDate() {
        // Given
        final TrainingGoal goal = new TrainingGoal();
        final LocalDate begin = LocalDate.of(2018, 12, 22);
        final LocalDate end = LocalDate.of(2018, 12, 29);

        goal.setDerStart(begin);
        goal.setDasEnde(end);
        goal.setDistanceOrHour(42);
        goal.setAthlete(athlete);
        executeInTransaction((TrainingGoalRepository r) -> r.storeTrainingGoal(goal, athlete.getId()), targetRepository);

        // When
        final List<TrainingGoal> targets = targetRepository.findTrainingGoalsByAthleteAndDate(athlete.getId(), begin);

        // Then
        assertThat(targets.get(0).getId(), is(Matchers.equalTo(goal.getId())));
    }

    @Test
    public void testFindByAthleteAndDataDateBetweenBeginEnd() {
        // Given
        final TrainingGoal goal = new TrainingGoal();
        final LocalDate begin = LocalDate.of(2018, 12, 22);
        final LocalDate date = LocalDate.of(2018, 12, 25);
        final LocalDate end = LocalDate.of(2018, 12, 29);

        goal.setDerStart(begin);
        goal.setDasEnde(end);
        goal.setDistanceOrHour(42);
        goal.setAthlete(athlete);

        executeInTransaction((TrainingGoalRepository r) -> r.storeTrainingGoal(goal, athlete.getId()), targetRepository);

        // When
        final List<TrainingGoal> targets = targetRepository.findTrainingGoalsByAthleteAndDate(athlete.getId(), date);

        // Then
        assertThat(targets.get(0).getId(), is(Matchers.equalTo(goal.getId())));
    }

    @Test
    public void testFindByAthleteAndDataEndEqualsDate() {
        // Given
        final TrainingGoal goal = new TrainingGoal();
        final LocalDate begin = LocalDate.of(2018, 12, 22);
        final LocalDate end = LocalDate.of(2018, 12, 29);

        goal.setDerStart(begin);
        goal.setDasEnde(end);
        goal.setDistanceOrHour(42);
        goal.setAthlete(athlete);

        executeInTransaction((TrainingGoalRepository r) -> r.storeTrainingGoal(goal, athlete.getId()), targetRepository);

        // When
        final List<TrainingGoal> targets = targetRepository.findTrainingGoalsByAthleteAndDate(athlete.getId(), end);

        // Then
        assertThat(0, is(targets.size()));
    }
}
