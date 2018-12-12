package ch.opentrainingcenter.otc.training.boundary;

import java.time.LocalDateTime;
import java.util.HashMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import lombok.extern.slf4j.Slf4j;

@Path("/users/authenticate")
@RequestScoped
@Slf4j
public class AuthenticationService {
	@Inject
	private AthleteRepository dao;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(final HashMap<String, String> datas) {
		final Athlete athlete = dao.authenticate(datas.get("username"), datas.get("password"));
		log.info("Athlete {} authenticated", athlete == null ? "not" : "");
		if (athlete != null) {
			log.info("Login successful {}", athlete.getEmail());
			athlete.setLastLogin(LocalDateTime.now());
			dao.update(athlete);
		}
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(athlete).build();
	}
}
