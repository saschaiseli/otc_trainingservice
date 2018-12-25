package ch.opentrainingcenter.otc.training.events;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.GoalDuration;
import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.repository.TrainingGoalRepository;

class TrainingGoalListenerTest {
	private TrainingGoalListener listener;
	@Mock
	private TrainingGoalRepository repo;
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
		dto = new TrainingGoalDto(42, TargetUnit.DISTANCE_KM, GoalDuration.SEVEN_DAYS);
		begin = LocalDate.of(2018, 12, 22);
		end = LocalDate.of(2018, 12, 29);
	}

	@Test
	void testOnNewTrainingGoal() {
		// Given
		dto.setActive(true);
		dto.setBegin(begin);
		dto.setCurrentValue(dto.getCurrentValue());
		dto.setEnd(end);
		dto.setAthleteId(42L);

		final TrainingGoal entity = new TrainingGoal(dto, null);

		// When
		listener.onNewTrainingGoal(dto);

		// Then repo.storeTarget(goal, event.getAthleteId());
		Mockito.verify(repo).storeTrainingGoal(entity, dto.getAthleteId());
	}

}
