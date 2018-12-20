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

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.domain.Duration;
import ch.opentrainingcenter.otc.training.domain.Target;
import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.repository.TargetRepository;
import io.jsonwebtoken.Claims;

class TargetServiceTest {
	private TargetService service;
	@Mock
	private HttpHeaders httpHeaders;
	@Mock
	private TargetRepository repository;
	@Mock
	private JWTService jwtService;
	@Mock
	private Claims claims;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new TargetService();
		service.repository = repository;
		service.jwtService = jwtService;
	}

	@Test
	void testGetTargets() {
		// Given
		final long athleteId = 42;
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);
		final List<Target> list = new ArrayList<>();
		when(repository.findByAthlete(athleteId)).thenReturn(list);

		// When
		final Response response = service.getTargets(httpHeaders);

		// Then
		verify(repository).findByAthlete(athleteId);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
		assertThat(response.getEntity(), is(equalTo(list)));
	}

	@Test
	void testAddTarget() {
		final Map<String, String> data = new HashMap<>();
		data.put("targetBegin", "2018-12-03");
		data.put("kind", "DISTANCE");
		data.put("duration", "WEEK");
		data.put("distanceOrHours", "10");

		final long athleteId = 42;
		when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
		when(claims.get("id", Long.class)).thenReturn(athleteId);

		// When
		final Response response = service.addTarget(httpHeaders, data);

		// Then
		final Target target = new Target();
		target.setTargetBegin(LocalDate.parse("2018-12-03"));
		target.setGoalUnit(TargetUnit.valueOfFromClient(data.get("kind")));
		target.setDuration(Duration.valueOfFromClient(data.get("duration")));
		target.setDistanceOrHours(Integer.valueOf(data.get("distanceOrHours")));

		verify(repository).storeTarget(target, athleteId);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
	}

}
