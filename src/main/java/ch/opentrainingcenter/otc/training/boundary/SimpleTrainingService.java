package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/trainings")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@JWTTokenNeeded
public class SimpleTrainingService {

    static final String DATE_PATTERN = "dd-MM-yyyy";
    @Inject
    protected JWTService jwtService;

    @Inject
    protected TrainingRepository dao;

    @GET
    public Response getSimpleTrainingByAthlete(@Context final HttpHeaders httpHeaders, @QueryParam("start") final String start, @QueryParam("end") final String end) {
        final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
        try {
            final LocalDate startDate = parseOr(start, LocalDate.MIN);
            final LocalDate endDate = parseOr(end, LocalDate.MAX);
            log.info("Find SimpleTraining by Athlete ID {} and date {} bis {}", athleteId, start, end);
            final List<SimpleTraining> list = dao.findByAthleteAndDate(athleteId, startDate, endDate);
            return Response.status(200).entity(list).build();
        } catch (final DateTimeParseException exception) {
            return Response.status(500).build();
        }
    }

    private LocalDate parseOr(final String dateAsString, final LocalDate ifNull) {
        return dateAsString != null ? LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern(DATE_PATTERN)) : ifNull;
    }

    @GET
    @Path("/{fileName}")
    public Response existsTraining(@Context final HttpHeaders httpHeaders,
                                   @PathParam("fileName") final String fileName) {
        final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
        log.info("Test if File is already imported [Athlete ID {}, FileName {}]", athleteId, fileName);
        final boolean exists = dao.existsFile(athleteId, fileName);
        return Response.status(200).entity(exists).build();
    }
}
