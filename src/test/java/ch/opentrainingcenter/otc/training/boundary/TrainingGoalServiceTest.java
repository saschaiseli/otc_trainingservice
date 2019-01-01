package ch.opentrainingcenter.otc.training.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.event.Event;
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
import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.repository.TrainingGoalRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.goal.GoalPredictionCalculator;
import ch.opentrainingcenter.otc.training.service.goal.GoalProgressCalculator;
import ch.opentrainingcenter.otc.training.service.goal.TrainingGoalDateCalculator;
import io.jsonwebtoken.Claims;

class TrainingGoalServiceTest {
	private TrainingGoalService service;
	@Mock
	private HttpHeaders httpHeaders;
	@Mock
	private TrainingGoalRepository repository;
	@Mock
	private TrainingRepository trainingRepo;
	@Mock
	private GoalProgressCalculator calculator;
	@Mock
	private GoalPredictionCalculator prediction;
	@Mock
	private JWTService jwtService;
	@Mock
	private Claims claims;
	@Mock
	protected Event<TrainingGoalDto> newTrainingGoalEvent;
	@Mock
	protected Event<TrainingGoalDto> updatedTrainingGoalEvent;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new TrainingGoalService();
		service.repository = repository;
		service.trainingRepo = trainingRepo;
		service.jwtService = jwtService;
		service.dateCalculator = new TrainingGoalDateCalculator();
		service.calculator = calculator;
		service.newTrainingGoalEvent = newTrainingGoalEvent;
		service.updatedTrainingGoalEvent = updatedTrainingGoalEvent;
		service.prediction = prediction;

	}

	@Test
	void testGetTargets() {
		// Given
		final long athleteId = 42;
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);
		final List<TrainingGoal> list = new ArrayList<>();
		when(repository.findByAthlete(athleteId)).thenReturn(list);

		// When
		final Response response = service.getTrainingGoals(httpHeaders);

		// Then
		verify(repository).findByAthlete(athleteId);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
		assertThat(response.getEntity(), is(equalTo(list)));
	}

	@Test
	void testAddTargetCreate() {
		final Map<String, String> data = new HashMap<>();
		// yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
		data.put("begin", "2018-12-03T11:12:55.123Z");
		data.put("unit", "DISTANCE");
		data.put("duration", "WEEK");
		data.put("distanceOrHour", "10");

		final long athleteId = 42;
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);

		// When
		final Response response = service.addTarget(httpHeaders, data);

		// Then
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
		final TrainingGoalDto entity = (TrainingGoalDto) response.getEntity();
		assertThat(entity.getBegin(), Matchers.equalTo(LocalDate.of(2018, 12, 03)));
		assertThat(entity.getEnd(), Matchers.notNullValue());
		assertThat(entity.getDistanceOrHour(), Matchers.is(10));
		assertThat(entity.getProgress(), Matchers.is(0d));
		Mockito.verify(newTrainingGoalEvent).fire(entity);
		Mockito.verifyNoMoreInteractions(newTrainingGoalEvent);
		Mockito.verifyZeroInteractions(updatedTrainingGoalEvent);
	}

	@Test
	void testAddTargetUpdate() {
		final Map<String, String> data = new HashMap<>();
		// yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
		data.put("id", "42");
		data.put("begin", "2018-12-03T11:12:55.123Z");
		data.put("unit", "DISTANCE");
		data.put("duration", "WEEK");
		data.put("distanceOrHour", "10");

		final long athleteId = 42;
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);

		// When
		final Response response = service.addTarget(httpHeaders, data);

		// Then
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
		final TrainingGoalDto entity = (TrainingGoalDto) response.getEntity();
		assertThat(entity.getBegin(), Matchers.equalTo(LocalDate.of(2018, 12, 03)));
		assertThat(entity.getEnd(), Matchers.notNullValue());
		assertThat(entity.getDistanceOrHour(), Matchers.is(10));
		assertThat(entity.getProgress(), Matchers.is(0d));
		Mockito.verify(updatedTrainingGoalEvent).fire(entity);
		Mockito.verifyNoMoreInteractions(updatedTrainingGoalEvent);
		Mockito.verifyZeroInteractions(newTrainingGoalEvent);
	}

}
