package ch.opentrainingcenter.otc.training.boundary;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.service.WorkerBean;

@Path("none")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrainingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerBean.class);

	@Inject
	@ConfigProperty(defaultValue = "injected value")
	private String fromProperties;

	@Inject
	@ConfigProperty(name = "dbhost", defaultValue = "default value")
	private String dbHost;

	@Inject
	private WorkerBean work;

	@GET
	public Response getAll() {
		work.doCount();
		LOGGER.info("Get All {}", fromProperties);
		LOGGER.info("DB Host isch: {}", dbHost);
		final Map<String, String> envs = System.getenv();
		envs.keySet().iterator().forEachRemaining((key) -> {
			if (key.contains("db")) {
				LOGGER.info("key {} value {}", key, envs.get(key));
			}
		});
		return Response.ok(fromProperties).build();
	}

}
