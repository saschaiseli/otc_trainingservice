package ch.opentrainingcenter.otc.training.events;

import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.GoalDuration;
import ch.opentrainingcenter.otc.training.entity.TargetUnit;
import ch.opentrainingcenter.otc.training.entity.TrainingGoal;
import ch.opentrainingcenter.otc.training.repository.TrainingGoalRepository;
import ch.opentrainingcenter.otc.training.service.goal.TrainingGoalsUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

class TrainingGoalListenerTest {
    private TrainingGoalListener listener;
    @Mock
    private TrainingGoalRepository repo;
    @Mock
    private TrainingGoalsUpdater updater;
    private TrainingGoalDto dto;
    private LocalDate begin;
    private LocalDate end;
    @Mock
    private Athlete athlete;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listener = new TrainingGoalListener();
        listener.repo = repo;
        begin = LocalDate.of(2018, 12, 22);
        end = LocalDate.of(2018, 12, 29);
        dto = new TrainingGoalDto(42, TargetUnit.DISTANCE_KM, GoalDuration.SEVEN_DAYS, begin, end);
    }

    @Test
    void testOnNewTrainingGoal() {
        // Given
        dto.setActive(true);
        dto.setCurrentValue(dto.getCurrentValue());
        dto.setAthleteId(42L);

        final TrainingGoal entity = new TrainingGoal(dto, null);

        // When
        listener.onNewTrainingGoal(dto);

        // Then repo.storeTarget(goal, event.getAthleteId());
        Mockito.verify(repo).storeTrainingGoal(entity, dto.getAthleteId());
    }

}
