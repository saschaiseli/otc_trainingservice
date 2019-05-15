package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/ping")
@Slf4j
@ApplicationScoped
public class PingService {

    @Inject
    protected AthleteRepository dao;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        log.info("ping....");
        return "pong";
    }

}

