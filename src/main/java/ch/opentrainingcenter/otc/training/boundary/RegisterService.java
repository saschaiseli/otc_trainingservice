package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.boundary.security.BCryptService;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/users/register")
@RequestScoped
@Slf4j
public class RegisterService {

    @Inject
    protected AthleteRepository dao;
    @Inject
    protected BCryptService cryptService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(final Map<String, String> datas) {
        final String firstName = datas.get("firstName");
        final String lastName = datas.get("lastName");
        final String email = datas.get("username");
        final String hashed = cryptService.hashPassword(datas.get("password"));
        return registerWithHashedPassword(firstName, lastName, email, hashed);
    }

    protected Response registerWithHashedPassword(final String firstName, final String lastName, final String email,
                                                  final String hashed) {
        log.info("Create Athlete with {}, {}, {}", firstName, lastName, email);
        Athlete athlete = CommonTransferFactory.createAthleteHashedPass(firstName, lastName, email, hashed);
        log.info("Create Athlete {}", athlete);
        athlete = dao.doSave(athlete);
        log.info("Athlete {} created in DB", athlete);

        return Response.status(200).entity(athlete).build();
    }

}
