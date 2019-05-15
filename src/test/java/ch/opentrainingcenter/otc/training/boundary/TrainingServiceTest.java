package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.dto.TrainingDto;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class TrainingServiceTest {
    private final GarminConverter converter = new GarminConverter();

    private TrainingService service;
    @Mock
    private TrainingRepository dao;
    @Mock
    private UriInfo uriInfo;
    @Mock
    private Training training;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        final File file = new File(TestConfig.FOLDER, TestConfig.FIT_FILE);
        training = converter.convert(new FileInputStream(file));

        MockitoAnnotations.initMocks(this);
        service = new TrainingService();
        service.dao = dao;
        service.uriInfo = uriInfo;
    }

    @Test
    void testGetById() throws JsonProcessingException {
        // Given
        final long trainingId = 42L;
        Mockito.when(dao.findFullTraining(trainingId)).thenReturn(training);

        // When
        final Response response = service.getTrainingById(trainingId);

        // Then
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
        assertThat(((TrainingDto) response.getEntity()).getId(), is(equalTo(new TrainingDto(training).getId())));
    }

    @Test
    void testDeleteById() throws JsonProcessingException {
        // Given
        final long trainingId = 42L;

        // When
        final Response response = service.deleteTraining(trainingId);

        // Then
        Mockito.verify(dao).remove(Training.class, trainingId);
        Mockito.verifyNoMoreInteractions(dao);
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
        assertThat(response.getEntity(), nullValue());
    }
}
