package ch.opentrainingcenter.otc.training.domain;

import static org.junit.Assert.assertThat;

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
		final GoalDuration d = GoalDuration.valueOfFromClient("");

		assertThat(GoalDuration.SEVEN_DAYS, Matchers.equalTo(d));
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
