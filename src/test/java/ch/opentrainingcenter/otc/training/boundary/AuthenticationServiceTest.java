package ch.opentrainingcenter.otc.training.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.training.boundary.security.BCryptService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;

class AuthenticationServiceTest {
	private AuthenticationService service;

	@Mock
	private AthleteRepository dao;
	@Mock
	private UriInfo uriInfo;
	private final JWTService secret = new JWTService();
	private final String firstName = "firstName";
	private final String lastName = "lastName";
	private final String email = "email";
	private final String plainPassword = "password";
	private String hashedPassword;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new AuthenticationService();
		service.dao = dao;
		service.secret = secret;
		service.uriInfo = uriInfo;
		service.cryptService = new BCryptService();
		hashedPassword = service.cryptService.hashPassword(plainPassword);
	}

	@Test
	void testAuthenticateSuccess() throws URISyntaxException {
		// Given
		final HashMap<String, String> datas = new HashMap<>();
		datas.put("username", "newUser");
		datas.put("password", "secret");
		final Athlete athlete = CommonTransferFactory.createAthleteHashedPass(firstName, lastName, email,
				hashedPassword);
		Mockito.when(dao.findByEmail(datas.get("username"))).thenReturn(athlete);
		Mockito.when(uriInfo.getAbsolutePath()).thenReturn(new URI("http://localhost:8080"));

		// When
		final Response response = service.authenticate(datas);

		// Then
		Mockito.verify(dao).findByEmail(datas.get("username"));
		Mockito.verify(dao).update(athlete);
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
		assertThat(response.getHeaders().get(HttpHeaders.AUTHORIZATION), Matchers.notNullValue());
		assertThat(((Athlete) response.getEntity()).getToken(), Matchers.notNullValue());
	}

	@Test
	void testAuthenticateFailedUserNotFound() throws URISyntaxException {
		// Given
		final HashMap<String, String> datas = new HashMap<>();
		datas.put("username", "newUser");
		datas.put("password", "secret");
		Mockito.when(dao.findByEmail(datas.get("username"))).thenReturn(null);
		Mockito.when(uriInfo.getAbsolutePath()).thenReturn(new URI("http://localhost:8080"));

		// When
		final Response response = service.authenticate(datas);

		// Then
		Mockito.verify(dao).findByEmail(datas.get("username"));
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_UNAUTHORIZED)));
		assertThat(response.getHeaders().get(HttpHeaders.AUTHORIZATION), Matchers.nullValue());
	}

	@Test
	void testAuthenticateFailedUserFound() throws URISyntaxException {
		// Given
		final HashMap<String, String> datas = new HashMap<>();
		datas.put("username", "newUser");
		datas.put("password", "secret");
		final Athlete athlete = CommonTransferFactory.createAthleteHashedPass(firstName, lastName, email, "not_hashed");
		Mockito.when(dao.findByEmail(datas.get("username"))).thenReturn(athlete);
		Mockito.when(uriInfo.getAbsolutePath()).thenReturn(new URI("http://localhost:8080"));

		// When
		final Response response = service.authenticate(datas);

		// Then
		Mockito.verify(dao).findByEmail(datas.get("username"));
		assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_UNAUTHORIZED)));
		assertThat(response.getHeaders().get(HttpHeaders.AUTHORIZATION), Matchers.nullValue());
	}
}
