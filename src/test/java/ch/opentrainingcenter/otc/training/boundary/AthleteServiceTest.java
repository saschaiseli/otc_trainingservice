package ch.opentrainingcenter.otc.training.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Collections;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;

class AthleteServiceTest {

	private AthleteService service;
	@Mock
	private AthleteRepository dao;
	private final String firstName = "firstName";
	private final String lastName = "lastName";
	private final String email = "email";
	private final String password = "password";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new AthleteService();
		service.dao = dao;
	}

	@Test
	void testCreateAthlete() {
		// Given
		final Athlete athlete = CommonTransferFactory.createAthlete(firstName, lastName, email, password);
		Mockito.when(dao.doSave(athlete)).thenReturn(athlete);

		// When
		final Response response = service.createAthlete(firstName, lastName, email, password);

		// Then
		Mockito.verify(dao).doSave(athlete);
		Mockito.verifyNoMoreInteractions(dao);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		final Athlete entity = (Athlete) response.getEntity();
		assertThat(entity, is(athlete));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
	}

	@Test
	void testGetAthlete() {
		// Given
		final Athlete athlete = CommonTransferFactory.createAthlete(firstName, lastName, email, password);
		final long athleteId = 42L;
		athlete.setId(athleteId);

		Mockito.when(dao.find(Athlete.class, athleteId)).thenReturn(athlete);

		// When
		final Response response = service.getAthlete(athleteId);

		// Then
		Mockito.verify(dao).find(Athlete.class, athleteId);
		Mockito.verifyNoMoreInteractions(dao);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		final Athlete entity = (Athlete) response.getEntity();
		assertThat(entity, is(athlete));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
	}

	@Test
	void testDeleteAthlete() {
		// Given
		final Athlete athlete = CommonTransferFactory.createAthlete(firstName, lastName, email, password);
		final long athleteId = 42L;
		athlete.setId(athleteId);

		// When
		final Response response = service.deleteAthlete(athleteId);

		// Then
		Mockito.verify(dao).remove(Athlete.class, athleteId);
		Mockito.verifyNoMoreInteractions(dao);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		assertThat(response.getEntity(), Matchers.nullValue());
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
	}
}
