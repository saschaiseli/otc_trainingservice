package ch.opentrainingcenter.otc.training.service.db.internal;

import ch.opentrainingcenter.otc.training.service.db.DBService;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class FlywayService implements DBService {

    private final String datasourceJndiName = "java:/ServiceDS";

    private Flyway flyway;

    @Inject
    Logger logger;


    @PostConstruct
    private void init() {
        final DataSource dataSource;
        try {
            dataSource = InitialContext.doLookup(datasourceJndiName);
            flyway = Flyway.configure().dataSource(dataSource).locations("db/migration").baselineOnMigrate(true).load();
        } catch (final NamingException e) {
            logger.error("Naming Exception!!!!!!!!!!!!!!!!");
        }

    }


    @Override
    public void initialize() {
        logger.info("*****************************************");
        logger.info("*    --> Flyway clean / migration       *");
        logger.info("*****************************************");
//        clean();
        migrate();
    }

    @Override
    public void clean() {
        logger.info("About to clean database");
        flyway.clean();
    }

    @Override
    public void migrate() {
        logger.info("About to migrate database");
        try {
            flyway.migrate();
        } catch (final FlywayException e) {
            logger.warn("Flyway has encountered an error while performing migrate. About to repair");
            flyway.repair();
            flyway.migrate();
        }
    }
}
