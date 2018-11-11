package ch.opentrainingcenter.otc.training.repository;

public interface RepositoryService<T> {

	T find(Class<T> type, long id);

	T doSave(T t);

	T update(T t);

	void remove(Class<T> type, long id);

}
