package ch.opentrainingcenter.otc.training.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.domain.raw.Sport;
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

//	@GET
//	public List<TrainingGoal> retrieveTrainingGoals() {
//		final List<TrainingGoal> goals = dao.findAll();
//		final URI uri = uriInfo.getBaseUriBuilder().path(TrainingService.class).build();
//		LOGGER.info(uri.getPath());
//		return goals;
//	}

	@POST
	public void createSport(final Sport sport) {

	}
}
