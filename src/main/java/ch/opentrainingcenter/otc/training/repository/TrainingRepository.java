package ch.opentrainingcenter.otc.training.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;

@Stateless
public class TrainingRepository extends RepositoryServiceBean<Training> {

	public List<Training> findTrainingByAthlete(final long athleteId) {
		final TypedQuery<Training> query = em.createNamedQuery("Training.getTrainingByAthlete", Training.class);
		final Athlete athlete = em.find(Athlete.class, athleteId);
		query.setParameter("athlete", athlete);
		return query.getResultList();
	}

	public Training findFullTraining(final long id) {
		final Training training = em.find(Training.class, id);
		training.getTrackPoints().size();
		training.getLapInfos().size();
		return training;
	}

}
