package ch.opentrainingcenter.otc.training.repository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.opentrainingcenter.otc.training.domain.Athlete;

@Stateless
public class AthleteRepository extends RepositoryServiceBean<Athlete> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AthleteRepository.class);

	public Athlete findByEmail(final String email) {
		final TypedQuery<Athlete> query = em.createNamedQuery("Athlete.findByEmail", Athlete.class);
		query.setParameter("email", email);
		Athlete result = null;
		try {
			result = query.getSingleResult();
			LOGGER.info("athlete with email '{}' found", email);
		} catch (final NoResultException noResult) {
			LOGGER.info("Athlete with email '{}' not found ", email);
		}
		return result;
	}

	public Athlete authenticate(final String email, final String password) {
		final TypedQuery<Athlete> query = em.createNamedQuery("Athlete.authenticate", Athlete.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		Athlete result = null;
		try {
			result = query.getSingleResult();
			LOGGER.info("athlete with email '{}' and password {} found", email, password);
		} catch (final NoResultException noResult) {
			LOGGER.info("Athlete with email '{}' password {} not found ", email, password);
		}
		return result;
	}

}
