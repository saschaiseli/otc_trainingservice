package ch.opentrainingcenter.otc.training.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Health
@ApplicationScoped
public class HealthChecker implements HealthCheck {

	private static final Logger LOGGER = LoggerFactory.getLogger(HealthChecker.class);

	@Override
	public HealthCheckResponse call() {
		HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name("TrainingService").withData("key",
				"value");

		if (Math.random() < 0.5) {
			LOGGER.info("Down");
			builder.down();
		} else {
			LOGGER.info("Up");
			builder.up();
		}
		return builder.build();
	}

}
