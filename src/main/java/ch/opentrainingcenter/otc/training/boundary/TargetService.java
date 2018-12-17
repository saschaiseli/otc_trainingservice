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
		final List<Target> targets = repository.findByAthlete(Long.valueOf(athleteId));
		return Response.status(200).entity(targets).build();
	}

	@POST
	public Response addTarget(@Context final HttpHeaders httpHeaders, final Map<String, String> datas) {
		final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
		log.info("add targets");
		final Target target = new Target();
		target.setAmount(Integer.valueOf(datas.get("amount")));
		target.setDistance(Integer.valueOf(datas.get("distance")));
		target.setDuration(Duration.valueOf(datas.get("duration")));
		target.setTargetBegin(LocalDate.parse(datas.get("targetBegin")));
		repository.storeTarget(target, Long.valueOf(athleteId));
		return Response.status(200).entity(target).build();
	}

}
