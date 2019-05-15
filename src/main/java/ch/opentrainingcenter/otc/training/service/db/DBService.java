package ch.opentrainingcenter.otc.training.service.db;

public interface DBService {
    void initialize();

    void clean();

    void migrate();
}
