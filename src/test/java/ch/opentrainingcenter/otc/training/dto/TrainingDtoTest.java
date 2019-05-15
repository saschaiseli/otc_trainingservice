package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TrainingDtoTest {
    private final GarminConverter service = new GarminConverter();

    @Test
    void test() throws IOException {
        final File file = new File(TestConfig.FOLDER, "2_runden.fit");

        final Training training = service.convert(new FileInputStream(file));
        training.setId(42L);

        final TrainingDto dto = new TrainingDto(training);

        assertThat(dto.getTrackPoints(), is(not(empty())));
        assertThat(dto.getLaps(), is(not(empty())));
    }

}
