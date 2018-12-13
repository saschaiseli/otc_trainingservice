package ch.opentrainingcenter.otc.training.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;

@RequestScoped
public class JWTAthleteService {
	@Inject
	AthleteRepository athleteRepo;
	@Inject
	JWTService jwtService;

	public Athlete getAthlete(final HttpHeaders headers) {
		final String email = jwtService.getClaims(headers).get("email", String.class);
		return athleteRepo.findByEmail(email);
	}
}
