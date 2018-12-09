package ch.opentrainingcenter.otc.training.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import lombok.extern.slf4j.Slf4j;

@Path("/athlete")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class AthleteService {

	@Inject
	private AthleteRepository dao;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAthlete(@FormParam("firstName") final String firstName,
			@FormParam("lastName") final String lastName, @FormParam("email") final String email) {
		log.info("Create Athlete with {}, {}, {}", firstName, lastName, email);
		Athlete athlete = CommonTransferFactory.createAthlete(firstName, lastName, email, "password");
		log.info("Create Athlete {}", athlete);
		athlete = dao.doSave(athlete);
		log.info("Athlete {} created in DB", athlete);

		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(athlete).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{athleteId}")
	public Response getAthlete(@PathParam("athleteId") final long athleteId) {
		log.info("getAthlete with id {}", athleteId);
		final Athlete athlete = dao.find(Athlete.class, athleteId);
		log.info("athlete found: {}", athlete != null);
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(athlete).build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{athleteId}")
	public Response deleteAthlete(@PathParam("athleteId") final long athleteId) {
		log.info("Delete Athlete with id {}", athleteId);
		dao.remove(Athlete.class, athleteId);
		log.info("Athlete with id {} deleted in DB", athleteId);

		return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
	}
}
