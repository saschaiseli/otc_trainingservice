package ch.opentrainingcenter.otc.training.boundary;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import lombok.extern.slf4j.Slf4j;

@Path("/users/register")
@RequestScoped
@Slf4j
public class RegisterService {

	@Inject
	protected AthleteRepository dao;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(final Map<String, String> datas) {
		final String firstName = datas.get("firstName");
		final String lastName = datas.get("lastName");
		final String email = datas.get("username");
		final String password = datas.get("password");
		log.info("Create Athlete with {}, {}, {}", firstName, lastName, email);
		Athlete athlete = CommonTransferFactory.createAthlete(firstName, lastName, email, password);
		log.info("Create Athlete {}", athlete);
		athlete = dao.doSave(athlete);
		log.info("Athlete {} created in DB", athlete);

		return Response.status(200).entity(athlete).build();
	}
}
