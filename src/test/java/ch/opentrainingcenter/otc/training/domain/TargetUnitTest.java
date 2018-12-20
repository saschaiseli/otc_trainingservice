package ch.opentrainingcenter.otc.training.domain;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class TargetUnitTest {

	@Test
	void testValueOfFromClientDefaultNull() {
		final TargetUnit tu = TargetUnit.valueOfFromClient(null);

		assertThat(TargetUnit.DISTANCE_KM, Matchers.equalTo(tu));
	}

	@Test
	void testValueOfFromClientDefaultEmpty() {
		final TargetUnit tu = TargetUnit.valueOfFromClient("");

		assertThat(TargetUnit.DISTANCE_KM, Matchers.equalTo(tu));
	}

	@Test
	void testValueOfFromClientDistance() {
		final TargetUnit tu = TargetUnit.valueOfFromClient("DISTANCE");

		assertThat(TargetUnit.DISTANCE_KM, Matchers.equalTo(tu));
	}

	@Test
	void testValueOfFromClientHOUR() {
		final TargetUnit tu = TargetUnit.valueOfFromClient("HOURS");

		assertThat(TargetUnit.DURATION_H, Matchers.equalTo(tu));
	}
}
