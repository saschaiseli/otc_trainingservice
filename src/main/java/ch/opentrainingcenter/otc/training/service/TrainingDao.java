package ch.opentrainingcenter.otc.training.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.domain.TrainingGoal;

@ApplicationScoped
public class TrainingDao implements HealthCheck {
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDao.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	@Health
	public HealthCheckResponse call() {
		final HealthCheckResponseBuilder builder = HealthCheckResponse.builder().name("TrainingDao").withData("key",
				"value");

		if (Math.random() < 0.5) {
			LOGGER.info("Down");
			builder.down();
		} else {
			LOGGER.info("Up");
			builder.up();
		}
		LOGGER.info("EM null ? {}", em);
		return builder.build();
	}

	@Transactional
	public List<TrainingGoal> findAll() {
		final ArrayList<TrainingGoal> goals = new ArrayList<>();
		final TrainingGoal goal = new TrainingGoal();
		goal.setAthleteId(1L);
		goal.setBegin(LocalDate.now());
		goal.setId(42L);
		goal.setName("was auch immer");
		em.merge(goal);
//		goal.setSport(new Sport());
		goals.add(goal);
		return goals;
	}

}
