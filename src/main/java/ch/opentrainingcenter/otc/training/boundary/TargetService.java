package ch.opentrainingcenter.otc.training.boundary;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.domain.Duration;
import ch.opentrainingcenter.otc.training.domain.Target;
import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.repository.TargetRepository;
import lombok.extern.slf4j.Slf4j;

@Path("/targets")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@JWTTokenNeeded
public class TargetService {

	@Inject
	protected JWTService jwtService;

	@Inject
	protected TargetRepository repository;

	@GET
	public Response getTargets(@Context final HttpHeaders httpHeaders) {
		final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
		log.info("get targets from Athlete {}", athleteId);
		final List<Target> targets = repository.findByAthlete(athleteId);
		return Response.status(200).entity(targets).build();
	}

	@POST
	public Response addTarget(@Context final HttpHeaders httpHeaders, final Map<String, String> datas) {
		final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
		log.info("add targets");
		final Target target = new Target();
		target.setTargetBegin(LocalDate.parse(datas.get("targetBegin")));
		target.setGoalUnit(TargetUnit.valueOfFromClient(datas.get("kind")));
		target.setDuration(Duration.valueOfFromClient(datas.get("duration")));
		target.setDistanceOrHours(Integer.valueOf(datas.get("distanceOrHours")));
		repository.storeTarget(target, athleteId);
		return Response.status(200).entity(target).build();
	}

}
