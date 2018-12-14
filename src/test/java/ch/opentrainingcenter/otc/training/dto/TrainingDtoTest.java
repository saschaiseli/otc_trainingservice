package ch.opentrainingcenter.otc.training.dto;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;

class TrainingDtoTest {
	private final GarminConverter service = new GarminConverter();
	private TrainingDto dto;

	@Test
	void test() throws JsonParseException, JsonMappingException, IOException {
		final File file = new File(TestConfig.FOLDER, "2_runden.fit");

		final Training training = service.convert(new FileInputStream(file));

		dto = new TrainingDto(training);

		assertThat(dto.getId(), is(equalTo(1412070744000L)));
		assertThat(dto.getTrackPoints(), is(not(empty())));
		assertThat(dto.getLaps(), is(not(empty())));
	}

}
