package ch.opentrainingcenter.otc.training.boundary;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;

@Path("/trainings")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class SimpleTrainingService {

	@Inject
	private TrainingRepository dao;

	@GET
	@Path("{athleteId}")
	public Response getSimpleTrainingByAthlete(@PathParam("athleteId") final long athleteId) {
		log.info("Find SimpleTraining by Athlete ID {}", athleteId);
		final List<SimpleTraining> list = dao.findSimpleTrainingByAthlete(athleteId);
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(list).build();
	}

	@GET
	@Path("{athleteId}/{fileName}")
	public Response existsTraining(@PathParam("athleteId") final long athleteId,
			@PathParam("fileName") final String fileName) {
		log.info("Test if File is already imported [Athlete ID {}, FileName {}]", athleteId, fileName);
		final boolean exists = dao.existsFile(athleteId, fileName);
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(exists).build();
	}
}
