package ch.opentrainingcenter.otc.training.web.internal;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Timed;

import ch.opentrainingcenter.otc.training.service.WorkerBean;
import ch.opentrainingcenter.otc.training.web.ITrainingController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
public class TrainingController implements ITrainingController {

	@Inject 
	@ConfigProperty(defaultValue="injected value")
	private String fromProperties;
	
	@Inject private WorkerBean work;
	
	@Timed
	public Response getAll() {
		work.doCount();
		log.info("Get All {}", fromProperties);
		return Response.ok(fromProperties).build();
	}

}
