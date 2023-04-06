package ru.javarush.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.todo.persistence.dao.TaskDAO;
import ru.javarush.todo.persistence.entity.Task;
import ru.javarush.todo.service.converters.StatusConverter;
import ru.javarush.todo.service.domain.Status;
import ru.javarush.todo.service.exception.ServiceException;

import java.util.List;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class TaskServiceImpl implements TaskService {

    final TaskDAO dao;

    final StatusConverter statusConverter;

    @Transactional
    @Override
    public ru.javarush.todo.service.domain.Task create(String description, Status status) {
        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setStatus(statusConverter.toDestination(status));
        return convertTask(dao.create(newTask));
    }

    @Transactional
    @Override
    public void update(long id, String description, Status status) throws ServiceException {
        Task updatingTask = dao.findById(id);
        if (updatingTask == null)
            throw new ServiceException("Task not found");

        updatingTask.setDescription(description);
        updatingTask.setStatus(statusConverter.toDestination(status));
        dao.update(updatingTask);
    }

    @Transactional
    @Override
    public void delete(long id) throws ServiceException {
        final Task deletingTask = dao.findById(id);
        if (deletingTask == null)
            throw new ServiceException("Task not found");

        dao.delete(deletingTask);
    }

    @Override
    public List<ru.javarush.todo.service.domain.Task> getInRange(int offset, int limit) {
        List<Task> tasks = dao.findInRange(offset, limit);

        return tasks.stream()
                .map(this::convertTask)
                .toList();
    }

    @Override
    public long countAll() {
        return dao.countAll();
    }

    private ru.javarush.todo.service.domain.Task convertTask(Task source) {
        return new ru.javarush.todo.service.domain.Task(
                source.getId(),
                source.getDescription(),
                statusConverter.toSource(source.getStatus())
        );
    }
}
