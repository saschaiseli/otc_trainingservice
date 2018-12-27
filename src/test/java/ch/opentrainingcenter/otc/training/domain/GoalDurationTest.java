package ch.opentrainingcenter.otc.training.domain;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class GoalDurationTest {

	@Test
	void testValueOfFromClientDefaultNull() {
		final GoalDuration d = GoalDuration.valueOfFromClient(null);

		assertThat(GoalDuration.SEVEN_DAYS, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefaultEmpty() {
		assertThrows(IllegalArgumentException.class, () -> GoalDuration.valueOfFromClient(""));
	}

	@Test
	void testValueOfFromClientDefaultUnknown() {
		assertThrows(IllegalArgumentException.class, () -> GoalDuration.valueOfFromClient("unknown_key"));
	}

	@Test
	void testValueOfFromClientDefaultWeek() {
		final GoalDuration d = GoalDuration.valueOfFromClient("WEEK");

		assertThat(GoalDuration.SEVEN_DAYS, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefault30Days() {
		final GoalDuration d = GoalDuration.valueOfFromClient("30DAYS");

		assertThat(GoalDuration.THIRTY_DAYS, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefaultMonth() {
		final GoalDuration d = GoalDuration.valueOfFromClient("MONTH");

		assertThat(GoalDuration.MONTH, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefaultYear() {
		final GoalDuration d = GoalDuration.valueOfFromClient("YEAR");

		assertThat(GoalDuration.YEAR, Matchers.equalTo(d));
	}
}
