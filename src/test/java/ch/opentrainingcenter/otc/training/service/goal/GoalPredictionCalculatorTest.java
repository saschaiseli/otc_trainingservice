package ch.opentrainingcenter.otc.training.service.goal;

import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;

class GoalPredictionCalculatorTest {
	private GoalPredictionCalculator calculator;
	@Mock
	private TrainingGoalDto dto;
	private LocalDate begin;
	private LocalDate end;
	private LocalDate now;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		calculator = new GoalPredictionCalculator();
	}

	@Test
	void testPredictTrainingGoalNotActive() {
		begin = LocalDate.of(2018, 12, 22);
		end = LocalDate.of(2018, 12, 24);
		now = LocalDate.of(2018, 12, 26);
		dto = new TrainingGoalDto(300, null, null, begin, end);
		dto.setActive(false);
		dto.setCurrentValue(100d);

		final TrainingGoalDto goal = calculator.predictTrainingGoal(dto, now);

		assertThat(goal.getPrediction(), Matchers.closeTo(100d, 0.001));
	}

	@Test
	void testPredictTrainingGoalActiveFirstDay() {
		begin = LocalDate.of(2018, 12, 22);
		now = LocalDate.of(2018, 12, 22);
		end = LocalDate.of(2018, 12, 24);
		dto = new TrainingGoalDto(300, null, null, begin, end);
		dto.setActive(true);
		dto.setCurrentValue(100d);

		final TrainingGoalDto goal = calculator.predictTrainingGoal(dto, now);

		assertThat(goal.getPrediction(), Matchers.closeTo(300d, 0.001));
	}

	@Test
	void testPredictTrainingGoalActiveInTheMiddle() {
		begin = LocalDate.of(2018, 12, 22);
		now = LocalDate.of(2018, 12, 23);
		end = LocalDate.of(2018, 12, 24);
		dto = new TrainingGoalDto(300, null, null, begin, end);
		dto.setActive(true);
		dto.setCurrentValue(100d);

		final TrainingGoalDto goal = calculator.predictTrainingGoal(dto, now);

		assertThat(goal.getPrediction(), Matchers.closeTo(150, 0.001));
	}

	@Test
	void testPredictTrainingGoalActiveMoreThanDistance() {
		begin = LocalDate.of(2018, 12, 22);
		now = LocalDate.of(2018, 12, 23);
		end = LocalDate.of(2018, 12, 24);
		dto = new TrainingGoalDto(300, null, null, begin, end);
		dto.setActive(true);
		dto.setCurrentValue(300d);

		final TrainingGoalDto goal = calculator.predictTrainingGoal(dto, now);

		assertThat(goal.getPrediction(), Matchers.closeTo(450d, 0.001));
	}

	@Test
	void testPredictTrainingGoalActiveNowEqualsEnd() {
		begin = LocalDate.of(2018, 12, 22);
		now = LocalDate.of(2018, 12, 24);
		end = LocalDate.of(2018, 12, 24);
		dto = new TrainingGoalDto(300, null, null, begin, end);
		dto.setActive(true);
		dto.setCurrentValue(200d);

		final TrainingGoalDto goal = calculator.predictTrainingGoal(dto, now);

		assertThat(goal.getPrediction(), Matchers.closeTo(200d, 0.001));
	}
}
