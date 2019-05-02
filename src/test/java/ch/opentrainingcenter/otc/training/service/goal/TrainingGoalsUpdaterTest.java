package ch.opentrainingcenter.otc.training.service.goal;

import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.TargetUnit;
import ch.opentrainingcenter.otc.training.entity.TrainingGoal;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.repository.TrainingGoalRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class TrainingGoalsUpdaterTest {

    private static final long ATHLETE_ID = 42;

    private TrainingGoalsUpdater updater;

    @Mock
    private TrainingGoalRepository repo;
    @Mock
    private TrainingRepository trainingRepo;
    @Mock
    private Athlete athlete;
    @Spy
    private TrainingGoal newGoal;

    private final LocalDateTime NOW_LDT = LocalDateTime.now();
    private final LocalDate NOW_LD = NOW_LDT.toLocalDate();
    private final List<TrainingGoal> goals = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        updater = new TrainingGoalsUpdater();
        updater.goalRepository = repo;
        updater.trainingRepository = trainingRepo;
        when(athlete.getId()).thenReturn(ATHLETE_ID);
    }

    @Test
    void testUpdateNoTraining() {
        // Given
        final List<TrainingGoal> goals = new ArrayList<>();
        final Training training = createTrainingDauer(10);
        when(repo.findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD)).thenReturn(goals);

        // When
        updater.updateGoalsFor(training);

        // Then
        verify(repo).findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void testUpdateOneTrainingDistance() {
        // Given
        final TrainingGoal goal = createGoal(30d, TargetUnit.DISTANCE_KM);
        goals.add(goal);

        final Training training = createTrainingDistance(12000);
        when(repo.findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD)).thenReturn(goals);

        // When
        updater.updateGoalsFor(training);

        // Then
        verify(repo).findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD);
        verify(goal).getUnit();
        verify(goal).getCurrentValue();
        verify(goal).setCurrentValue(42d);
        verify(repo).storeTrainingGoals(goals, ATHLETE_ID);
    }

    @Test
    void testUpdateTwoTrainingsDistance() {
        // Given
        final TrainingGoal goalA = createGoal(30d, TargetUnit.DISTANCE_KM);
        final TrainingGoal goalB = createGoal(100d, TargetUnit.DISTANCE_KM);
        goals.add(goalA);
        goals.add(goalB);

        final Training training = createTrainingDistance(12000);
        when(repo.findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD)).thenReturn(goals);

        // When
        updater.updateGoalsFor(training);

        // Then
        verify(repo).findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD);
        verify(goalA).getUnit();
        verify(goalA).getCurrentValue();
        verify(goalA).setCurrentValue(42d);
        verify(goalB).getUnit();
        verify(goalB).getCurrentValue();
        verify(goalB).setCurrentValue(112d);
        verify(repo).storeTrainingGoals(goals, ATHLETE_ID);
    }

    @Test
    void testUpdateOneTrainingDurance() {
        // Given
        final TrainingGoal goal = createGoal(30d, TargetUnit.DURATION_H);
        goals.add(goal);

        final Training training = createTrainingDauer(TimeUnit.HOURS.toSeconds(2));
        when(repo.findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD)).thenReturn(goals);

        // When
        updater.updateGoalsFor(training);

        // Then
        verify(repo).findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD);
        verify(goal).getUnit();
        verify(goal).getCurrentValue();
        verify(goal).setCurrentValue(32d);
        verify(repo).storeTrainingGoals(goals, ATHLETE_ID);
    }

    @Test
    void testUpdateTwoTrainingsDurance() {
        // Given
        final TrainingGoal goalA = createGoal(30d, TargetUnit.DURATION_H);
        final TrainingGoal goalB = createGoal(100d, TargetUnit.DURATION_H);
        goals.add(goalA);
        goals.add(goalB);

        final Training training = createTrainingDauer(TimeUnit.HOURS.toSeconds(5));
        when(repo.findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD)).thenReturn(goals);

        // When
        updater.updateGoalsFor(training);

        // Then
        verify(repo).findTrainingGoalsByAthleteAndDate(ATHLETE_ID, NOW_LD);
        verify(goalA).getUnit();
        verify(goalA).getCurrentValue();
        verify(goalA).setCurrentValue(35d);
        verify(goalB).getUnit();
        verify(goalB).getCurrentValue();
        verify(goalB).setCurrentValue(105d);
        verify(repo).storeTrainingGoals(goals, ATHLETE_ID);
    }

    private Training createTrainingDistance(final long distanceInMeter) {
        return createTraining(distanceInMeter, 0);
    }

    private Training createTrainingDauer(final long dauerInSeconds) {
        return createTraining(0, dauerInSeconds);
    }

    private Training createTraining(final long distanceInMeter, final long dauerInSeconds) {
        final Training training = mock(Training.class);
        when(training.getAthlete()).thenReturn(athlete);
        when(training.getDateOfStart()).thenReturn(NOW_LDT);
        when(training.getLaengeInMeter()).thenReturn(distanceInMeter);
        when(training.getDauer()).thenReturn(dauerInSeconds);
        return training;
    }

    private TrainingGoal createGoal(final double currentValue, final TargetUnit unit) {
        final TrainingGoal result = mock(TrainingGoal.class);
        when(result.getCurrentValue()).thenReturn(currentValue);
        when(result.getUnit()).thenReturn(unit);
        return result;
    }

    private TrainingGoal createGoal(final int distanceOrHour, final TargetUnit unit, final LocalDate begin,
                                    final LocalDate end) {
        final TrainingGoal goal = new TrainingGoal();
        goal.setDistanceOrHour(distanceOrHour);
        goal.setUnit(unit);
        goal.setDerStart(begin);
        goal.setDasEnde(end);
        return goal;
    }
}
