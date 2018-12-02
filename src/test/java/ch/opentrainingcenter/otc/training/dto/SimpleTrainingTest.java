package ch.opentrainingcenter.otc.training.dto;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.domain.raw.Training;

public class SimpleTrainingTest {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final File jsonTraining = new File(TestConfig.FOLDER + "/training.json");
	private Training training;

	@BeforeEach
	void setUp() throws JsonParseException, JsonMappingException, IOException {
		training = objectMapper.readValue(jsonTraining, Training.class);
	}

	@Test
	public void testConstructor() {
		final SimpleTraining simpleTraining = new SimpleTraining(training);
		final double distanceInKm = simpleTraining.getDistanceInKm();
		assertThat(training.getLaengeInMeter() / 1000d, equalTo(distanceInKm));
	}
}
