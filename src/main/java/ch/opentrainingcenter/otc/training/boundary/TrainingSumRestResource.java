package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/trainings/overview")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@JWTTokenNeeded
public class TrainingSumRestResource {

    @Inject
    protected JWTService jwtService;

    @Inject
    protected TrainingRepository dao;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getTrainingSumFromTo(@Context final HttpHeaders httpHeaders, @QueryParam("from") final String from, @QueryParam("to") final String to) throws JsonProcessingException {
        log.info("getTraining Sum from: {} to {}", from, to);
        final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
        return Response.status(200).entity(null).build();
    }
}
