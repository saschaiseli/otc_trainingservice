package ch.opentrainingcenter.otc.training.domain;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class DurationTest {

	@Test
	void testValueOfFromClientDefaultNull() {
		final Duration d = Duration.valueOfFromClient(null);

		assertThat(Duration.SEVEN_DAYS, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefaultEmpty() {
		final Duration d = Duration.valueOfFromClient("");

		assertThat(Duration.SEVEN_DAYS, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefaultWeek() {
		final Duration d = Duration.valueOfFromClient("WEEK");

		assertThat(Duration.SEVEN_DAYS, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefault30Days() {
		final Duration d = Duration.valueOfFromClient("30DAYS");

		assertThat(Duration.THIRTY_DAYS, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefaultMonth() {
		final Duration d = Duration.valueOfFromClient("MONTH");

		assertThat(Duration.MONTH, Matchers.equalTo(d));
	}

	@Test
	void testValueOfFromClientDefaultYear() {
		final Duration d = Duration.valueOfFromClient("YEAR");

		assertThat(Duration.YEAR, Matchers.equalTo(d));
	}
}
