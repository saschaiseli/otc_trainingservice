package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimpleTrainingTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File jsonTraining = new File(TestConfig.FOLDER + "/training.json");
    private Training training;

    @BeforeEach
    void setUp() throws IOException {
        training = objectMapper.readValue(jsonTraining, Training.class);
        training.setId(42L);
    }

    @Test
    public void testConstructor() {
        final SimpleTraining simpleTraining = new SimpleTraining(training);
        final double distanceInKm = simpleTraining.getDistanceInKm();
        assertThat(training.getLaengeInMeter() / 1000d, equalTo(distanceInKm));
    }
}
