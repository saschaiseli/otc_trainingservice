package ch.opentrainingcenter.otc.training.boundary;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.domain.raw.Sport;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;

@Path("goals")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrainingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);

	@Inject
	private TrainingRepository dao;

	@Context
	private UriInfo uriInfo;

	@GET
	public List<SimpleTraining> getTrainings() {
		final List<Training> trainings = dao.findTrainingByAthlete(1);
		final List<SimpleTraining> st = trainings.stream().map(SimpleTraining::new).collect(Collectors.toList());
		final URI uri = uriInfo.getBaseUriBuilder().path(TrainingService.class).build();
		LOGGER.info(uri.getPath());
		return st;
	}

	@POST
	public void createSport(final Sport sport) {

	}
}
