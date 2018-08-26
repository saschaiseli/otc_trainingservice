//package ch.opentrainingcenter.otc.training.web.internal;
//
//import java.util.Map;
//
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.ws.rs.core.Response;
//
//import org.eclipse.microprofile.config.inject.ConfigProperty;
//import org.eclipse.microprofile.metrics.annotation.Timed;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import ch.opentrainingcenter.otc.training.service.WorkerBean;
//import ch.opentrainingcenter.otc.training.web.ITrainingController;
//
//@RequestScoped
//public class TrainingController implements ITrainingController {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerBean.class);
//
//	@Inject
//	@ConfigProperty(defaultValue = "injected value")
//	private String fromProperties;
//
//	@Inject
//	@ConfigProperty(name = "dbhost", defaultValue = "default value")
//	private String dbHost;
//
//	@Inject
//	private WorkerBean work;
//
//	@Override
//	@Timed
//	public Response getAll() {
//		work.doCount();
//		LOGGER.info("Get All {}", fromProperties);
//		LOGGER.info("DB Host isch: {}", dbHost);
//		final Map<String, String> envs = System.getenv();
//		envs.keySet().iterator().forEachRemaining((key) -> {
//			if (key.contains("db")) {
//				LOGGER.info("key {} value {}", key, envs.get(key));
//			}
//		});
//		return Response.ok(fromProperties).build();
//	}
//
//}
