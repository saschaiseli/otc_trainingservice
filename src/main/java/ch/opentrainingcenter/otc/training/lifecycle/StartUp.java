package ch.opentrainingcenter.otc.training.lifecycle;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;

@Startup
@Singleton
public class StartUp {

	@Inject
	private AthleteRepository repo;

	@PostConstruct
	void init() {
		final Athlete athlete = repo.findByEmail("sascha.iseli@gmx.ch");
		if (athlete == null) {
			final Athlete a = CommonTransferFactory.createAthlete("sascha", "iseli", "sascha.iseli@gmx.ch", "secret");
			a.setId(0);
			repo.doSave(a);
		}
	}
}
