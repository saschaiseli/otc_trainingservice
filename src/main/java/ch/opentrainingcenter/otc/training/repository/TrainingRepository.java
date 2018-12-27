package ch.opentrainingcenter.otc.training.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;

@Stateless
public class TrainingRepository extends RepositoryServiceBean<Training> {

	@Override
	public Training doSave(final Training training) {
		training.setDateOfImport(LocalDate.now());
		em.persist(training);
		return training;
	}

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

	public List<SimpleTraining> findSimpleTrainingByAthlete(final long athleteId) {
		final TypedQuery<SimpleTraining> query = em.createNamedQuery("Training.getSimpleTrainingByAthlete",
				SimpleTraining.class);
		query.setParameter("athleteId", athleteId);
		return query.getResultList();
	}

	public boolean existsFile(final long athleteId, final String fileName) {
		final TypedQuery<Training> query = em.createNamedQuery("Training.existsFileByAthlete", Training.class);
		query.setParameter("athleteId", athleteId);
		query.setParameter("fileName", fileName);
		return !query.getResultList().isEmpty();
	}

	public List<SimpleTraining> findByAthleteAndDate(final long athleteId, final LocalDate beginDate,
			final LocalDate endDate) {
		final TypedQuery<SimpleTraining> query = em.createNamedQuery("Training.findByAthleteAndDate",
				SimpleTraining.class);
		query.setParameter("athleteId", athleteId);
		query.setParameter("beginDate", LocalDateTime.of(beginDate, LocalTime.of(0, 0)));
		query.setParameter("endDate", LocalDateTime.of(endDate, LocalTime.of(0, 0)));
		return query.getResultList();
	}
}
