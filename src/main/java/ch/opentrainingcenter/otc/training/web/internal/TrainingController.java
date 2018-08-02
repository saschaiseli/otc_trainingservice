package ch.opentrainingcenter.otc.training.web.internal;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import ch.opentrainingcenter.otc.training.web.ITrainingController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
public class TrainingController implements ITrainingController {

	@Inject 
	@ConfigProperty(defaultValue="injected value")
	String greeting;
	
	public Response getAll() {
		log.info("Get All {}", greeting);
		return Response.ok(greeting).build();
	}

}
