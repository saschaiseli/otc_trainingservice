package ch.opentrainingcenter.otc.training.repository;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class RepositoryServiceBean<T> {

    @PersistenceContext
    protected EntityManager em;

    public T doSave(final T t) {
        em.persist(t);
        return t;
    }

    public T update(final T t) {
        return em.merge(t);
    }

    public void remove(final Class<T> type, final long id) {
        final T managed = em.find(type, id);
        em.remove(managed);
    }

    public T find(final Class<T> type, final long id) {
        return em.find(type, id);
    }

}
