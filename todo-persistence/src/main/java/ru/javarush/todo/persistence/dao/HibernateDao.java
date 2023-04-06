package ru.javarush.todo.persistence.dao;

import java.util.List;
public interface HibernateDao<T> {
    T create(final T entity);
    T update(final T entity);
    void delete(final T entity);
    long countAll();
    List<T> findAll();
    T findById(final long id);
    List<T> findInRange(int offset, int limit);
}
