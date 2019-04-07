package ch.opentrainingcenter.otc.training.service.db;

import java.io.IOException;

public interface DataMigration {
    void migrate() throws IOException;
}
