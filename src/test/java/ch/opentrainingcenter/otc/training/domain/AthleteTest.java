package ch.opentrainingcenter.otc.training.domain;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.opentrainingcenter.otc.training.domain.raw.Training;

class AthleteTest {
	private Athlete athlete;

	@BeforeEach
	public void setUp() {
		athlete = new Athlete();
	}

	@Test
	void testAddTargetBidirectional() {
		athlete = new Athlete("firstname", "lastname", "email", "hashed");

		final Target target = new Target();
		athlete.addTarget(target);

		assertThat(athlete.getTargets(), Matchers.is(Matchers.contains(target)));
		assertThat(target.getAthlete(), Matchers.is(Matchers.equalTo(athlete)));

		athlete.removeTarget(target);
		assertThat(athlete.getTargets(), Matchers.empty());
	}

	@Test
	void testAddTrainingBidirectional() {
		athlete = new Athlete("firstname", "lastname", "email", "hashed");

		final Training training = new Training();
		athlete.addTraining(training);

		assertThat(athlete.getTrainings(), Matchers.is(Matchers.contains(training)));
		assertThat(training.getAthlete(), Matchers.is(Matchers.equalTo(athlete)));

		athlete.removeTraining(training);
		assertThat(athlete.getTrainings(), Matchers.empty());
	}
}
