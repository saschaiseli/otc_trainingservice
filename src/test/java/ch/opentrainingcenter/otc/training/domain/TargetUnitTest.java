package ch.opentrainingcenter.otc.training.domain;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class TargetUnitTest {

	@Test
	void testValueOfFromClientDefaultNull() {
		assertThrows(IllegalArgumentException.class, () -> TargetUnit.valueOfFromClient(null));
	}

	@Test
	void testValueOfFromClientDefaultEmpty() {
		assertThrows(IllegalArgumentException.class, () -> TargetUnit.valueOfFromClient(""));
	}

	@Test
	void testValueOfFromClientDefaultUnknown() {
		assertThrows(IllegalArgumentException.class, () -> TargetUnit.valueOfFromClient("unknown_key"));
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
