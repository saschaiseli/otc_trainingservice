package ch.opentrainingcenter.otc.training.service.lifecycle;

import ch.opentrainingcenter.otc.training.boundary.security.BCryptService;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.service.db.DBService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class StartUp {

    @Inject
    protected AthleteRepository repo;
    @Inject
    protected BCryptService cryptService;
    @EJB
    private DBService dbService;

    @PostConstruct
    void init() {
        dbService.initialize();
        final Athlete athlete = repo.findByEmail("sascha.iseli@gmx.ch");
        if (athlete == null) {
            final Athlete a = CommonTransferFactory.createAthleteHashedPass("sascha", "iseli", "sascha.iseli@gmx.ch",
                    cryptService.hashPassword("secret"));
            repo.doSave(a);
        }
    }
}
