package ch.opentrainingcenter.otc.training.boundary.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JWTTokenNeededFilterTest {

	private JWTTokenNeededFilter jwtFilter;
	private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJmaXJzdG5hbWUiOiJzYXNjaGEiLCJpc3MiOiJodHRwOi8vMTI3LjAuMC4xOjgxODEvdHJhaW5pbmdzZXJ2aWNlL2FwaS91c2Vycy9hdXRoZW50aWNhdGUiLCJpZCI6MSwiaWF0IjoxNTQ0NzA1NzQ0LCJlbWFpbCI6InNhc2NoYS5pc2VsaUBnbXguY2giLCJsYXN0bmFtZSI6ImlzZWxpIn0.zgyRzdCa68Ejo4Ueu-7QKYdIHDVVzOg32rPKT1HIgeA";
	@Mock
	private ContainerRequestContext context;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		jwtFilter = new JWTTokenNeededFilter();
		jwtFilter.secret = new JWTService();
	}

	@Test
	void testNoAuthorization() throws IOException {
		jwtFilter.filter(context);

		verify(context).abortWith(JWTTokenNeededFilter.UNAUTHORIZED);
	}

	@Test
	void testInvalidAuthorization() throws IOException {
		when(context.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer abc");

		jwtFilter.filter(context);

		verify(context).abortWith(JWTTokenNeededFilter.UNAUTHORIZED);
	}

	@Test
	void testValidAuthorization() throws IOException {
		when(context.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);

		jwtFilter.filter(context);

		verify(context).getHeaderString(HttpHeaders.AUTHORIZATION);
		verifyZeroInteractions(context);
	}
}
