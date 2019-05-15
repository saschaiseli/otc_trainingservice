package ch.opentrainingcenter.otc.training.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import io.jsonwebtoken.Claims;

class SimpleTrainingServiceTest {

	private SimpleTrainingService service;
	@Mock
	private TrainingRepository trainingRepo;
	@Mock
	private HttpHeaders httpHeaders;
	@Mock
	private JWTService jwtService;
	@Mock
	private Claims claims;

	private final long athleteId = 42L;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new SimpleTrainingService();
		service.dao = trainingRepo;
		service.jwtService = jwtService;
	}

	@Test
	void testGetSimpleTrainingByAthlete() {

		final List<SimpleTraining> trainings = new ArrayList<>();
		final SimpleTraining simpleTraining = Mockito.mock(SimpleTraining.class);
		trainings.add(simpleTraining);
		when(trainingRepo.findSimpleTrainingByAthlete(athleteId)).thenReturn(trainings);
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);

		// When
		final Response response = service.getSimpleTrainingByAthlete(httpHeaders);

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
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);

		// When
		final Response response = service.existsTraining(httpHeaders, fileName);

		assertThat(response.getStatus(), is(HttpStatus.SC_OK));
		assertThat(((Boolean) response.getEntity()), is(true));
	}

	@Test
	void testExistsTrainingNo() {
		// Given
		final String fileName = "abcdef.fit";
		when(trainingRepo.existsFile(athleteId, fileName)).thenReturn(false);
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);
		// When
		final Response response = service.existsTraining(httpHeaders, fileName);

		assertThat(response.getStatus(), is(HttpStatus.SC_OK));
		assertThat(((Boolean) response.getEntity()), is(false));
	}
}
