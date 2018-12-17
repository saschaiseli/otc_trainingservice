package ch.opentrainingcenter.otc.training.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;

@RequestScoped
public class JWTAthleteService {
	static final String EMAIL = "email";
	@Inject
	protected AthleteRepository athleteRepo;
	@Inject
	protected JWTService jwtService;

	public Athlete getAthlete(final HttpHeaders headers) {
		final String email = jwtService.getClaims(headers).get(EMAIL, String.class);
		return athleteRepo.findByEmail(email);
	}
}
