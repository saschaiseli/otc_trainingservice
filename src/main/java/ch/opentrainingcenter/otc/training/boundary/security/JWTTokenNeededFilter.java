package ch.opentrainingcenter.otc.training.boundary.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
@Slf4j
public class JWTTokenNeededFilter implements ContainerRequestFilter {

	static final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();
	@Inject
	JWTService secret;

	@Override
	public void filter(final ContainerRequestContext requestContext) throws IOException {
		// Get the HTTP Authorization header from the request
		final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		// Extract the token from the HTTP Authorization header
		if (authorizationHeader != null) {
			validateToken(requestContext, authorizationHeader);
		} else {
			requestContext.abortWith(UNAUTHORIZED);
		}
	}

	private void validateToken(final ContainerRequestContext requestContext, final String authorizationHeader) {
		final String token = authorizationHeader.substring("Bearer".length()).trim();
		try {
			Jwts.parser().setSigningKey(secret.getSigningKey()).parseClaimsJws(token);
			log.debug("#### valid token: {}", token);

		} catch (final Exception e) {
			log.info("#### invalid token: {}", token);
			requestContext.abortWith(UNAUTHORIZED);
		}
	}
}
