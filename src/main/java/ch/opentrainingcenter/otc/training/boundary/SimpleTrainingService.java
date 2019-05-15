package ch.opentrainingcenter.otc.training.boundary;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;

@Path("/trainings")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@JWTTokenNeeded
public class SimpleTrainingService {

	@Inject
	protected JWTService jwtService;

	@Inject
	protected TrainingRepository dao;

	@GET
	public Response getSimpleTrainingByAthlete(@Context final HttpHeaders httpHeaders) {
		final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
		log.info("Find SimpleTraining by Athlete ID {}", athleteId);
		final List<SimpleTraining> list = dao.findSimpleTrainingByAthlete(athleteId);
		return Response.status(200).entity(list).build();
	}

	@GET
	@Path("/{fileName}")
	public Response existsTraining(@Context final HttpHeaders httpHeaders,
			@PathParam("fileName") final String fileName) {
		final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
		log.info("Test if File is already imported [Athlete ID {}, FileName {}]", athleteId, fileName);
		final boolean exists = dao.existsFile(athleteId, fileName);
		return Response.status(200).entity(exists).build();
	}
}
