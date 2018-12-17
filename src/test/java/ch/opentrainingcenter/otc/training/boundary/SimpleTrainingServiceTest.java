package ch.opentrainingcenter.otc.training.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;

class SimpleTrainingServiceTest {

	private SimpleTrainingService service;
	@Mock
	private TrainingRepository trainingRepo;
	private final long athleteId = 42L;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new SimpleTrainingService();
		service.dao = trainingRepo;
	}

	@Test
	void testGetSimpleTrainingByAthlete() {

		final List<SimpleTraining> trainings = new ArrayList<>();
		final SimpleTraining simpleTraining = Mockito.mock(SimpleTraining.class);
		trainings.add(simpleTraining);
		when(trainingRepo.findSimpleTrainingByAthlete(athleteId)).thenReturn(trainings);

		// When
		final Response response = service.getSimpleTrainingByAthlete(athleteId);

		// Then
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		@SuppressWarnings("unchecked")
		final List<SimpleTraining> entities = (List<SimpleTraining>) response.getEntity();
		assertThat(entities, Matchers.contains(simpleTraining));
		assertThat(entities.size(), is(1));
	}

	@Test
	void testExistsTrainingYes() {
		// Given
		final String fileName = "abcdef.fit";
		when(trainingRepo.existsFile(athleteId, fileName)).thenReturn(true);

		// When
		final Response response = service.existsTraining(athleteId, fileName);

		assertThat(response.getStatus(), is(HttpStatus.SC_OK));
		assertThat(((Boolean) response.getEntity()), is(true));
	}

	@Test
	void testExistsTrainingNo() {
		// Given
		final String fileName = "abcdef.fit";
		when(trainingRepo.existsFile(athleteId, fileName)).thenReturn(false);

		// When
		final Response response = service.existsTraining(athleteId, fileName);

		assertThat(response.getStatus(), is(HttpStatus.SC_OK));
		assertThat(((Boolean) response.getEntity()), is(false));
	}
}
