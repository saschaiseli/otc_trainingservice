package ch.opentrainingcenter.otc.training.service.goal;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;

class GoalProgressCalculatorTest {
	private GoalProgressCalculator calculator;

	private List<SimpleTraining> list;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		calculator = new GoalProgressCalculator();

		list = new ArrayList<>();
	}

	@Test
	void testCalculateTrainingGoalsOneTrainingDistanceMoreThan100() {
		list.add(createSimpleTraining(3600, 16000));

		final TrainingGoalDto dto = createDto(8, TargetUnit.DISTANCE_KM);

		final double result = calculator.calculateTrainingGoalProgress(dto, list);

		assertThat(result, Matchers.equalTo(16d));
	}

	@Test
	void testCalculateTrainingGoalsOneTrainingDistanceLessThan100() {
		list.add(createSimpleTraining(3600, 16000));

		final TrainingGoalDto dto = createDto(18, TargetUnit.DISTANCE_KM);

		final double result = calculator.calculateTrainingGoalProgress(dto, list);

		assertThat(result, Matchers.closeTo(16d, 0.01d));
	}

	@Test
	void testCalculateTrainingGoalsOneTrainingHoursMoreThan100() {
		list.add(createSimpleTraining(TimeUnit.HOURS.toSeconds(3), 16000));

		final TrainingGoalDto dto = createDto(1, TargetUnit.DURATION_H);

		final double result = calculator.calculateTrainingGoalProgress(dto, list);

		assertThat(result, Matchers.equalTo(3d));
	}

	@Test
	void testCalculateTrainingGoalsOneTrainingHoursLessThan100() {
		list.add(createSimpleTraining(TimeUnit.HOURS.toSeconds(3), 16000));

		final TrainingGoalDto dto = createDto(9, TargetUnit.DURATION_H);

		final double result = calculator.calculateTrainingGoalProgress(dto, list);

		assertThat(result, Matchers.closeTo(3, 0.01d));
	}

	@Test
	void testCalculateTrainingGoalsOneTrainingHoursWithoutTraining() {
		final TrainingGoalDto dto = createDto(9, TargetUnit.DURATION_H);

		final double result = calculator.calculateTrainingGoalProgress(dto, list);

		assertThat(result, Matchers.closeTo(0d, 0.01d));
	}

	@Test
	void testCalculateTrainingGoalsOneTrainingDistanceWithoutTraining() {
		final TrainingGoalDto dto = createDto(9, TargetUnit.DISTANCE_KM);

		final double result = calculator.calculateTrainingGoalProgress(dto, list);

		assertThat(result, Matchers.closeTo(0d, 0.01d));
	}

	private TrainingGoalDto createDto(final int distanceOrHours, final TargetUnit unit) {
		return new TrainingGoalDto(distanceOrHours, unit, null, null, null);
	}

	private SimpleTraining createSimpleTraining(final long durationInSec, final int distanceInMeter) {
		return new SimpleTraining(0, durationInSec, distanceInMeter, 0, 0, 0, 0);
	}

}
