package ch.opentrainingcenter.otc.training;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.domain.Settings;
import ch.opentrainingcenter.otc.training.domain.Speed;
import ch.opentrainingcenter.otc.training.domain.SystemOfUnit;
import ch.opentrainingcenter.otc.training.domain.raw.Tracktrainingproperty;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;

public final class TrainingCreator {

	private final GarminConverter service = new GarminConverter();

	public static void main(final String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		new TrainingCreator().writeJSON();
	}

	public void writeJSON() throws JsonGenerationException, JsonMappingException, IOException {
		Locale.setDefault(Locale.GERMAN);
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));

		final File file = new File(TestConfig.FOLDER, TestConfig.FIT_FILE);

		final Training training = service.convert(new FileInputStream(file));
		training.setFileName(TestConfig.FIT_FILE);
		final Athlete athlete = CommonTransferFactory.createAthleteHashedPass("first name", "last name",
				"test@opentrainingcenter.ch", "abc");
		athlete.setSettings(Settings.of(SystemOfUnit.METRIC, Speed.PACE));
		training.setAthlete(athlete);
		training.setDateOfImport(new Date());
		training.setLapInfos(Collections.emptyList());
		final List<Tracktrainingproperty> tps = training.getTrackPoints();
		training.setTrackPoints(tps.subList(0, 10));
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File(TestConfig.FOLDER + "/training.json"), training);
	}

}
