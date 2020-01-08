package ch.opentrainingcenter.otc.training.service.db.internal;

import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;
import ch.opentrainingcenter.otc.training.service.db.DataMigration;
import org.slf4j.Logger;

import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class BulkDataMigration implements DataMigration {

    @Inject
    protected GarminConverter garminConverter;

    @Inject
    TrainingRepository repository;

    @Inject
    AthleteRepository athleteRepository;

    @Inject
    Logger logger;

    @Override
    public void migrate() throws IOException, URISyntaxException {
        final Athlete athlete = athleteRepository.findByEmail("sascha.iseli@gmx.ch");
        logger.info("Import for {}", athlete.getEmail());
        logger.info("------------------------------------------------------------");
        final Path path = Paths.get("/opt/jboss/wildfly/data_files");
//        logger.info("Pfad :{}", path.toString());
//        final File f = new File("test.tmp");
//        final String current = new java.io.File(".").getCanonicalPath();
//        final String filename = "data_files/README.md";
//        final ClassLoader classLoader = new BulkDataMigration().getClass().getClassLoader();
//        final File f = new File(classLoader.getResource(filename).getFile());
//        logger.info("file {}", f);
//        logger.info("file {}", f.exists());
//        final Path data_files = Paths.get(f.toURI());
        logger.info("data_files {}", path);
        final List<Path> files = Files.walk(path).filter(x -> x.getFileName().toString().contains("FIT")).collect(toList());
        logger.info("------------------------------------------------------------");


        for (final Path file : files) {
            final InputStream inputStream = Files.newInputStream(file);
            final Training training = garminConverter.convert(inputStream);
            training.setAthlete(athlete);
            training.setFileName(file.getFileName().toString());
            if (!repository.existsFile(athlete.getId(), training.getFileName())) {
                repository.doSave(training);
                logger.info("Training gespeichert: {}", file);
            } else {
                logger.info("Training bereits gespeichert: {}", file);
            }
        }

    }
}
