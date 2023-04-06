package ru.javarush.todo.persistence.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javarush.todo.persistence.entity.Task;
@Repository
public class TaskDAOImpl extends AbstractHibernateDao<Task> implements TaskDAO {
    @Autowired
    public TaskDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Task.class);
    }
}
