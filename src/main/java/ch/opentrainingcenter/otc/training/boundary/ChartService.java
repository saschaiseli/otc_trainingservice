package ch.opentrainingcenter.otc.training.boundary;

import java.math.BigDecimal;
import java.util.function.Function;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.domain.raw.Tracktrainingproperty;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.json.TrackPointSerializer;
import lombok.extern.slf4j.Slf4j;

@Path("/charts")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@JWTTokenNeeded
public class ChartService {
	@Inject
	protected TrainingRepository repo;

	@GET
	@Path("/heart/{trainingId}")
	public Response getHeartById(@PathParam("trainingId") final long trainingId) throws JsonProcessingException {
		log.info("get heart Track Point Chart for Training {}", trainingId);
		final Training training = repo.findFullTraining(trainingId);
		log.debug("Found training by id {}", trainingId);

		final String heart = extractValue(training, "Herzfrequenz", x -> BigDecimal.valueOf(x.getHeartBeat()));
		return Response.status(200).entity(heart).build();
	}

	@GET
	@Path("/altitude/{trainingId}")
	public Response getAltitudeById(@PathParam("trainingId") final long trainingId) throws JsonProcessingException {
		log.info("get altitude Track Point Chart for Training {}", trainingId);
		final Training training = repo.findFullTraining(trainingId);
		log.info("Found {} training by id {}", training, trainingId);

		final String alt = extractValue(training, "Höhenmeter", x -> BigDecimal.valueOf(x.getAltitude()));
		return Response.status(200).entity(alt).build();
	}

	private String extractValue(final Training training, final String name,
			final Function<Tracktrainingproperty, BigDecimal> function) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		final SimpleModule module = new SimpleModule();
		module.addSerializer(Training.class, new TrackPointSerializer(name, function));
		mapper.registerModule(module);

		return mapper.writeValueAsString(training);
	}

}
