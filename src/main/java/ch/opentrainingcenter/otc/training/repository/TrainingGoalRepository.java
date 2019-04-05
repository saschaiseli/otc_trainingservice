package ch.opentrainingcenter.otc.training.repository;

import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.TrainingGoal;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Slf4j
public class TrainingGoalRepository extends RepositoryServiceBean<TrainingGoal> {

    public List<TrainingGoal> findByAthlete(final long athleteId) {
        log.info("Get TrainingGoal for athlete {}", athleteId);
        final TypedQuery<TrainingGoal> query = em.createNamedQuery("TrainingGoal.findByAthlete", TrainingGoal.class);
        query.setParameter("athleteId", athleteId);
        final List<TrainingGoal> resultList = query.getResultList();
        log.info("{} TrainingGoal found", resultList.size());
        return resultList;
    }

    public void storeTrainingGoal(final TrainingGoal goal, final Long athleteId) {
        final List<TrainingGoal> goals = new ArrayList<>();
        goals.add(goal);
        storeTrainingGoals(goals, athleteId);
    }

    public void storeTrainingGoals(final List<TrainingGoal> goals, final Long athleteId) {
        log.info("Store TrainingGoal for athlete {}", athleteId);
        final Athlete athlete = em.find(Athlete.class, athleteId);
        goals.forEach(athlete::addTarget);
        em.persist(athlete);
        log.info("TrainingGoal stored for athlete {}", athleteId);
    }

    public List<TrainingGoal> findTrainingGoalsByAthleteAndDate(final long athleteId, final LocalDate date) {
        log.info("Get goals for athlete {} and date {}", athleteId, date);
        final TypedQuery<TrainingGoal> query = em.createNamedQuery("TrainingGoal.findByAthleteAndDate",
                TrainingGoal.class);
        query.setParameter("athleteId", athleteId);
        query.setParameter("date", date);
        final List<TrainingGoal> resultList = query.getResultList();
        log.info("{} TrainingGoal found", resultList.size());
        return resultList;
    }
}