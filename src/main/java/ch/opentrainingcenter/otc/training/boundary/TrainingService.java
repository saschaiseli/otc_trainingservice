package ch.opentrainingcenter.otc.training.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.dto.TrainingDto;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;

@Path("/trainings/all")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@JWTTokenNeeded
public class TrainingService {

	@Inject
	protected TrainingRepository dao;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("{trainingId}")
	public Response getTrainingById(@PathParam("trainingId") final long trainingId) throws JsonProcessingException {
		log.info("getTrainingById {}", trainingId);
		final Training training = dao.findFullTraining(trainingId);
		log.info(training != null ? "Training found. ID {}" : "Training not found. ID {}", trainingId);
		final TrainingDto dto = new TrainingDto(training);
		return Response.status(200).entity(dto).build();
	}

	@DELETE
	@Path("{trainingId}")
	public Response deleteTraining(@PathParam("trainingId") final long trainingId) throws JsonProcessingException {
		log.info("delete Training. ID: {}", trainingId);
		dao.remove(Training.class, trainingId);
		return Response.status(200).build();
	}
}
