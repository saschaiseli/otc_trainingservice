package ch.opentrainingcenter.otc.training.boundary;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;

import ch.opentrainingcenter.otc.training.boundary.security.SecretService;
import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Path("/users/authenticate")
@Slf4j
public class AuthenticationService {
	@Inject
	private AthleteRepository dao;
	@Inject
	private SecretService secret;

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(final HashMap<String, String> datas) {
		try {
			final String email = datas.get("username");
			final Athlete athlete = dao.authenticate(email, datas.get("password"));
			log.info("Athlete {} authenticated", email);

			// Issue a token for the user
			final String token = issueToken(email);
			log.info("issued token {} for user {}", token, email);
			athlete.setLastLogin(LocalDateTime.now());
			athlete.setToken(token);
			dao.update(athlete);

			// Return the token on the response
			return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).entity(athlete).build();

		} catch (final Exception e) {
			return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
		}

	}

	private String issueToken(final String login) {
		final LocalDateTime add = LocalDateTime.now().plusMinutes(1L);
		final String jwtToken = Jwts.builder()//
				.setSubject(login)//
				.setIssuer(uriInfo.getAbsolutePath().toString())//
				.setIssuedAt(new Date())//
				.setExpiration(Date.from(add.atZone(ZoneId.systemDefault()).toInstant()))//
				.signWith(SignatureAlgorithm.HS256, secret.getSigner())//
				.compact();
		return jwtToken;
	}
}
