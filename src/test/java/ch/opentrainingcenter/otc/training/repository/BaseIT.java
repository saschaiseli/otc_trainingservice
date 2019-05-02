package ch.opentrainingcenter.otc.training.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseIT {

    private final EntityTransaction tx;


    private final EntityManager em;

    public BaseIT() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("integration-test");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    public final <R, T> R executeInTransaction(final Function<T, R> function, final T t) {
        begin();
        final R r = function.apply(t);
        commit();
        return r;
    }

    public final <T> void executeInTransaction(final Consumer<T> consumer, final T t) {
        begin();
        consumer.accept(t);
        commit();
    }


    public EntityManager getEntityManager() {
        return em;
    }

    private void begin() {
        tx.begin();
    }

    private void commit() {
        tx.commit();
    }
}
