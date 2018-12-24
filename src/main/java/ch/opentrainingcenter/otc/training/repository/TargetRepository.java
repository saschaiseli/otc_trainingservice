package ch.opentrainingcenter.otc.training.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import lombok.extern.slf4j.Slf4j;

@Stateless
@Slf4j
public class TargetRepository extends RepositoryServiceBean<TrainingGoal> {

	public List<TrainingGoal> findByAthlete(final long athleteId) {
		log.info("Get targets for athlete {}", athleteId);
		final TypedQuery<TrainingGoal> query = em.createNamedQuery("TrainingGoal.findByAthlete", TrainingGoal.class);
		query.setParameter("athleteId", athleteId);
		final List<TrainingGoal> resultList = query.getResultList();
		log.info("{} Targets found", resultList.size());
		return resultList;
	}

	public void storeTarget(final TrainingGoal goal, final Long athleteId) {
		final List<TrainingGoal> goals = new ArrayList<>();
		goals.add(goal);
		storeTargets(goals, athleteId);
	}

	public void storeTargets(final List<TrainingGoal> goals, final Long athleteId) {
		log.info("Store Target for athlete {}", athleteId);
		final Athlete athlete = em.find(Athlete.class, athleteId);
		goals.forEach(athlete::addTarget);
		em.persist(athlete);
		log.info("Target stored for athlete {}", athleteId);
	}
}