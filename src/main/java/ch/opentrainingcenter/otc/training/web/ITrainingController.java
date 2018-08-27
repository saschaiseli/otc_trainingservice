package ch.opentrainingcenter.otc.training.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;

@Path("/api/training")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/api/training", tags = "training", produces = MediaType.APPLICATION_JSON)
public interface ITrainingController {

	@GET
	@Path("/getAll")
	Response getAll();
}
