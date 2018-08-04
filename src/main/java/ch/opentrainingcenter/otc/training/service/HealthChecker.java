package ch.opentrainingcenter.otc.training.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import lombok.extern.slf4j.Slf4j;

@Health
@Slf4j
@ApplicationScoped
public class HealthChecker implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name("TrainingService").withData("key",
				"value");

		if (Math.random() < 0.5) {
			log.info("Down");
			builder.down();
		} else {
			log.info("Up");
			builder.up();
		}
		return builder.build();
	}

}
