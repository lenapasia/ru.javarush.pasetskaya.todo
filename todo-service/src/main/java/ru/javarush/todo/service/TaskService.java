package ru.javarush.todo.service;

import ru.javarush.todo.service.domain.Status;
import ru.javarush.todo.service.domain.Task;
import ru.javarush.todo.service.exception.ServiceException;

import java.util.List;

public interface TaskService {

    Task create(String description, Status status);

    void update(long id, String description, Status status) throws ServiceException;

    void delete(long id) throws ServiceException;

    List<Task> getInRange(int offset, int limit);

    long countAll();

}
