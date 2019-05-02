package ch.opentrainingcenter.otc.training.entity;

import ch.opentrainingcenter.otc.training.entity.raw.Training;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThat;

class AthleteTest {
    private Athlete athlete;

    @BeforeEach
    public void setUp() {
        athlete = new Athlete();
    }

    @Test
    void testAddTargetBidirectional() {
        athlete = new Athlete("firstname", "lastname", "email", "hashed");

        final TrainingGoal goal = new TrainingGoal();
        athlete.addTarget(goal);

        assertThat(athlete.getGoals(), Matchers.is(Matchers.contains(goal)));
        assertThat(goal.getAthlete(), Matchers.is(Matchers.equalTo(athlete)));

        athlete.removeTarget(goal);
        assertThat(athlete.getTrainings(), Matchers.empty());
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
