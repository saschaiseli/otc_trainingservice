package ch.opentrainingcenter.otc.training.service.db;

import java.io.IOException;
import java.net.URISyntaxException;

public interface DataMigration {
    void migrate() throws IOException, URISyntaxException;
}
