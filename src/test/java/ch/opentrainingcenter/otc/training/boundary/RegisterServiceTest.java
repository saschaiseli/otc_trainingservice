package ch.opentrainingcenter.otc.training.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.boundary.security.BCryptService;
import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;

class RegisterServiceTest {

	private RegisterService service;
	@Mock
	private AthleteRepository dao;
	private final String firstName = "firstName";
	private final String lastName = "lastName";
	private final String email = "email";
	private final String password = "password";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new RegisterService();
		service.dao = dao;
		service.cryptService = new BCryptService();
	}

	@Test
	public void testRegisterWithHashedPassword() {
		// Given
		final Athlete athlete = CommonTransferFactory.createAthleteHashedPass(firstName, lastName, email, password);
		Mockito.when(dao.doSave(athlete)).thenReturn(athlete);

		final Map<String, String> datas = new HashMap<>();
		datas.put("firstName", firstName);
		datas.put("lastName", lastName);
		datas.put("username", email);
		datas.put("password", password);
		// When
		final Response response = service.registerWithHashedPassword(firstName, lastName, email, password);

		// Then
		Mockito.verify(dao).doSave(athlete);
		Mockito.verifyNoMoreInteractions(dao);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		final Athlete entity = (Athlete) response.getEntity();
		assertThat(entity, is(athlete));
		assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
	}

}
