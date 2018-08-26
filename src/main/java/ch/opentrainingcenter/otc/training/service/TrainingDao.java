package ch.opentrainingcenter.otc.training.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.service.credentials.CouchConnection;

@ApplicationScoped
@Health
@Timeout(1000)
//@Timed(displayName = "Data Layer Times", description = "The time it takes this DAO method to complete, as a histogram.")
public class TrainingDao implements HealthCheck {
	private static final Logger LOGGER = LoggerFactory.getLogger(HealthChecker.class);
	@Inject
	private CouchConnection couch;

	@Override
	public HealthCheckResponse call() {
		LOGGER.info("credentials isch {}", couch);
		return null;
	}

}
