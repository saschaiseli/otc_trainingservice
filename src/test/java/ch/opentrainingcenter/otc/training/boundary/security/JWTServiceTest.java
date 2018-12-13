package ch.opentrainingcenter.otc.training.boundary.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.jsonwebtoken.Claims;

class JWTServiceTest {

	private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJmaXJzdG5hbWUiOiJzYXNjaGEiLCJpc3MiOiJodHRwOi8vMTI3LjAuMC4xOjgxODEvdHJhaW5pbmdzZXJ2aWNlL2FwaS91c2Vycy9hdXRoZW50aWNhdGUiLCJpZCI6MSwiaWF0IjoxNTQ0NzA1NzQ0LCJlbWFpbCI6InNhc2NoYS5pc2VsaUBnbXguY2giLCJsYXN0bmFtZSI6ImlzZWxpIn0.zgyRzdCa68Ejo4Ueu-7QKYdIHDVVzOg32rPKT1HIgeA";
	private JWTService service;
	@Mock
	private HttpHeaders httpHeaders;
	@Mock
	private MultivaluedMap<String, String> map;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		service = new JWTService();
	}

	@Test
	void test() {
		when(httpHeaders.getRequestHeaders()).thenReturn(map);
		when(map.getFirst(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);

		final Claims claims = service.getClaims(httpHeaders);

		assertThat(1, is(equalTo(claims.get("id", Integer.class))));
		assertThat("sascha.iseli@gmx.ch", is(equalTo(claims.get("email", String.class))));
		assertThat("sascha", is(equalTo(claims.get("firstname", String.class))));
		assertThat("iseli", is(equalTo(claims.get("lastname", String.class))));
	}

}
