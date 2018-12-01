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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;

@Path("/trainings")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimpleTrainingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTrainingService.class);

	@Inject
	private TrainingRepository dao;

	@GET
	@Path("{athleteId}")
	public Response status(@PathParam("athleteId") final long athleteId) {
		LOGGER.info("Find SimpleTraining by Athlete ID {}", athleteId);
		final List<SimpleTraining> list = dao.findSimpleTrainingByAthlete(athleteId);
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(list).build();
	}
}
