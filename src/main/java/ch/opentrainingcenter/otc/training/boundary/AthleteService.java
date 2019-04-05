package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/athlete")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class AthleteService {

    @Inject
    protected AthleteRepository dao;

    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{athleteId}")
    @JWTTokenNeeded
    public Response getAthlete(@PathParam("athleteId") final long athleteId) {
        log.info("getAthlete with id {}", athleteId);
        final Athlete athlete = dao.find(Athlete.class, athleteId);
        log.info("athlete found: {}", athlete != null);
        return Response.status(200).entity(athlete).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{athleteId}")
    @JWTTokenNeeded
    public Response deleteAthlete(@PathParam("athleteId") final long athleteId) {
        log.info("Delete Athlete with id {}", athleteId);
        dao.remove(Athlete.class, athleteId);
        log.info("Athlete with id {} deleted in DB", athleteId);

        return Response.status(200).build();
    }
}
