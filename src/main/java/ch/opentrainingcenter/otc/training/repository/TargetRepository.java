package ch.opentrainingcenter.otc.training.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.Target;
import lombok.extern.slf4j.Slf4j;

@Stateless
@Slf4j
public class TargetRepository extends RepositoryServiceBean<Target> {

	public List<Target> findByAthlete(final long athleteId) {
		log.info("Get targets for athlete {}", athleteId);
		final TypedQuery<Target> query = em.createNamedQuery("Target.findByAthlete", Target.class);
		query.setParameter("athleteId", athleteId);
		final List<Target> resultList = query.getResultList();
		log.info("{} Targets found", resultList.size());
		return resultList;
	}

	public void storeTarget(final Target target, final Long athleteId) {
		final List<Target> targets = new ArrayList<>();
		targets.add(target);
		storeTargets(targets, athleteId);
	}

	public void storeTargets(final List<Target> targets, final Long athleteId) {
		log.info("Store Target for athlete {}", athleteId);
		final Athlete athlete = em.find(Athlete.class, athleteId);
		targets.forEach(athlete::addTarget);
		em.persist(athlete);
		log.info("Target stored for athlete {}", athleteId);
	}
}