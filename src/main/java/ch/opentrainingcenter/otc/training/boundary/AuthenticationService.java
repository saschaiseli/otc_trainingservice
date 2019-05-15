package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.boundary.security.BCryptService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("/users/authenticate")
@Slf4j
public class AuthenticationService {
    @Inject
    protected AthleteRepository dao;
    @Inject
    protected JWTService secret;
    @Context
    protected UriInfo uriInfo;
    @Inject
    protected BCryptService cryptService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(final Map<String, String> datas) {
        try {
            final String email = datas.get("username");
            final String plainPassword = datas.get("password");
            final Athlete athlete = dao.findByEmail(email);

            if (!cryptService.checkPassword(plainPassword, athlete.getPassword())) {
                throw new NotAuthorizedException("Wrong Password");
            }

            log.info("Athlete {} authenticated", email);

            // Issue a token for the user
            final String token = issueToken(athlete);
            log.info("issued token {} for user {}", token, email);
            athlete.setLastLogin(LocalDateTime.now());
            athlete.setToken(token);
            dao.update(athlete);

            // Return the token on the response
            return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).entity(athlete).build();

        } catch (final Exception e) {
            return Response.status(HttpStatus.SC_UNAUTHORIZED).entity("ausser spesen nix gewesen").build();
        }
    }

    private String issueToken(final Athlete athlete) {
        final LocalDateTime add = LocalDateTime.now().plusHours(24L);
        final Map<String, Object> claims = convert(athlete);
        return Jwts.builder()//
                .setSubject(athlete.getEmail())//
                .setClaims(claims)//
                .setIssuer(uriInfo.getAbsolutePath().toString())//
                .setIssuedAt(new Date())//
                .setExpiration(Date.from(add.atZone(ZoneId.systemDefault()).toInstant()))//
                .signWith(SignatureAlgorithm.HS256, secret.getSigner())//
                .compact();
    }

    private Map<String, Object> convert(final Athlete athlete) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", athlete.getId());
        claims.put("firstname", athlete.getFirstName());
        claims.put("lastname", athlete.getLastName());
        claims.put("email", athlete.getEmail());
        return claims;
    }
}
