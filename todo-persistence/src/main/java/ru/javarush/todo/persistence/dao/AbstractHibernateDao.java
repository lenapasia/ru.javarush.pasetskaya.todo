package ru.javarush.todo.persistence.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

abstract class AbstractHibernateDao<T> implements HibernateDao<T> {
    private final Class<T> clazz;

    private final SessionFactory sessionFactory;

    protected AbstractHibernateDao(SessionFactory sessionFactory, Class<T> clazzToSet) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazzToSet;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T create(final T entity) {
        getSession().persist(entity);
        return entity;
    }

    @Override
    public T update(final T entity) {
        return getSession().merge(entity);
    }

    @Override
    public void delete(final T entity) {
        getSession().remove(entity);
    }

    @Override
    public long countAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(clazz)));

        Query<Long> query = getSession().createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public List<T> findAll() {
        return getSession().createQuery("from " + clazz.getName(), clazz).list();
    }

    @Override
    public T findById(final long id) {
        return getSession().get(clazz, id);
    }

    @Override
    public List<T> findInRange(int offset, int limit) {
        Query<T> query = getSession().createQuery("from " + clazz.getName(), clazz);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }
}