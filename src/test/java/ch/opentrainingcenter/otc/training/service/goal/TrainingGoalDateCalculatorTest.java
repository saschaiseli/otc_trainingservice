package ch.opentrainingcenter.otc.training.service.goal;

import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.entity.GoalDuration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.Assert.assertThat;

class TrainingGoalDateCalculatorTest {
    private TrainingGoalDateCalculator calculator;
    @Mock
    private TrainingGoalDto dto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        calculator = new TrainingGoalDateCalculator();
    }

    @Test
    void testGetEndDateMiddleOfMonthWeek() {
        // Given
        final LocalDate beginDate = LocalDate.of(2018, 12, 20);

        // When
        final LocalDate endDate = calculator.getEndDate(beginDate, GoalDuration.SEVEN_DAYS);

        // Then
        assertThat(endDate, Matchers.equalTo(LocalDate.of(2018, 12, 26)));
    }

    @Test
    void testGetEndDateEndOfMonthWeek() {
        // Given
        final LocalDate beginDate = LocalDate.of(2018, 12, 25);

        // When
        final LocalDate endDate = calculator.getEndDate(beginDate, GoalDuration.SEVEN_DAYS);

        // Then
        assertThat(endDate, Matchers.equalTo(LocalDate.of(2018, 12, 31)));
    }

    @Test
    void testGetEndDateChangeYearWeek() {
        // Given
        final LocalDate beginDate = LocalDate.of(2018, 12, 26);

        // When
        final LocalDate endDate = calculator.getEndDate(beginDate, GoalDuration.SEVEN_DAYS);

        // Then
        assertThat(endDate, Matchers.equalTo(LocalDate.of(2019, 01, 01)));
    }

    @Test
    void testGetEndDateMiddleOfMonth30() {
        // Given
        final LocalDate beginDate = LocalDate.of(2018, 12, 20);

        // When
        final LocalDate endDate = calculator.getEndDate(beginDate, GoalDuration.THIRTY_DAYS);

        // Then
        assertThat(endDate, Matchers.equalTo(LocalDate.of(2019, 1, 18)));
    }

    @Test
    void testGetEndDateMiddleOfMonthMonth() {
        // Given
        final LocalDate beginDate = LocalDate.of(2018, 12, 20);

        // When
        final LocalDate endDate = calculator.getEndDate(beginDate, GoalDuration.MONTH);

        // Then
        assertThat(endDate, Matchers.equalTo(LocalDate.of(2018, 12, 31)));
    }

    @Test
    void testGetEndDateMiddleOfMonthYear() {
        // Given
        final LocalDate beginDate = LocalDate.of(2018, 12, 20);

        // When
        final LocalDate endDate = calculator.getEndDate(beginDate, GoalDuration.YEAR);

        // Then
        assertThat(endDate, Matchers.equalTo(LocalDate.of(2019, 12, 19)));
    }

    @Test
    void testIsActiveNowBeforeStart() {
        final LocalDate now = LocalDate.of(2018, 12, 4);
        final LocalDate start = LocalDate.of(2018, 12, 5);
        final LocalDate end = LocalDate.of(2018, 12, 10);
        Mockito.when(dto.getBegin()).thenReturn(start);
        Mockito.when(dto.getEnd()).thenReturn(end);

        final boolean active = calculator.isActive(dto, now);

        assertThat(active, Matchers.is(false));
    }

    @Test
    void testIsActiveStartEqualsNow() {
        final LocalDate start = LocalDate.of(2018, 12, 5);
        final LocalDate now = start;
        final LocalDate end = LocalDate.of(2018, 12, 10);
        Mockito.when(dto.getBegin()).thenReturn(start);
        Mockito.when(dto.getEnd()).thenReturn(end);

        final boolean active = calculator.isActive(dto, now);

        assertThat(active, Matchers.is(true));
    }

    @Test
    void testIsActiveNowBetween() {
        final LocalDate start = LocalDate.of(2018, 12, 5);
        final LocalDate now = LocalDate.of(2018, 12, 7);
        final LocalDate end = LocalDate.of(2018, 12, 10);
        Mockito.when(dto.getBegin()).thenReturn(start);
        Mockito.when(dto.getEnd()).thenReturn(end);

        final boolean active = calculator.isActive(dto, now);

        assertThat(active, Matchers.is(true));
    }

    @Test
    void testIsActiveNowOnEnd() {
        final LocalDate start = LocalDate.of(2018, 12, 5);
        final LocalDate now = LocalDate.of(2018, 12, 7);
        final LocalDate end = now;
        Mockito.when(dto.getBegin()).thenReturn(start);
        Mockito.when(dto.getEnd()).thenReturn(end);

        final boolean active = calculator.isActive(dto, now);

        assertThat(active, Matchers.is(false));
    }

    @Test
    void testIsActiveNowAfterEnd() {
        final LocalDate start = LocalDate.of(2018, 12, 5);
        final LocalDate end = LocalDate.of(2018, 12, 7);
        final LocalDate now = LocalDate.of(2018, 12, 8);
        Mockito.when(dto.getBegin()).thenReturn(start);
        Mockito.when(dto.getEnd()).thenReturn(end);

        final boolean active = calculator.isActive(dto, now);

        assertThat(active, Matchers.is(false));
    }

    @Test
    void testGetBegin() {
        final LocalDate beginDate = LocalDate.of(2018, 12, 22);

        final LocalDate result = calculator.getBeginDate(beginDate, GoalDuration.SEVEN_DAYS);

        assertThat(result, Matchers.equalTo(beginDate));
    }

    @Test
    void testGetBeginMonth() {
        final LocalDate beginDate = LocalDate.of(2018, 12, 22);

        final LocalDate result = calculator.getBeginDate(beginDate, GoalDuration.MONTH);

        final LocalDate firstDay = LocalDate.of(2018, 12, 1);
        assertThat(result, Matchers.equalTo(firstDay));
    }
}
