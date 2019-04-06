package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ChartServiceTest {
    private static final long ID = 42L;
    private final GarminConverter converter = new GarminConverter();
    private ChartService service;
    @Mock
    private TrainingRepository repo;
    private Training training;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);
        service = new ChartService();
        service.repo = repo;
        final File file = new File(TestConfig.FOLDER, TestConfig.FIT_FILE);

        training = converter.convert(new FileInputStream(file));
        training.setStartInMillis(ID);
    }

    @Test
    void testHeartById() throws JsonProcessingException {
        Mockito.when(repo.findFullTraining(ID)).thenReturn(training);

        final Response response = service.getHeartById(ID);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        final String entity = (String) response.getEntity();
        assertThat(entity, Matchers.notNullValue());
        assertThat(entity, Matchers.containsString("Herzfrequenz"));

        assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
    }

    @Test
    void testAltitudeById() throws JsonProcessingException {
        Mockito.when(repo.findFullTraining(ID)).thenReturn(training);

        final Response response = service.getAltitudeById(ID);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        final String entity = (String) response.getEntity();
        assertThat(entity, Matchers.notNullValue());
        assertThat(entity, Matchers.containsString("Höhenmeter"));

        assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
    }

}
