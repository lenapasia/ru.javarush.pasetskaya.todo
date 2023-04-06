package ru.javarush.todo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.javarush.todo.persistence.dao.TaskDAO;
import ru.javarush.todo.service.config.ServiceConfig;
import ru.javarush.todo.service.converters.StatusConverter;
import ru.javarush.todo.service.domain.Status;
import ru.javarush.todo.service.domain.Task;
import ru.javarush.todo.service.exception.ServiceException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = {ServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskServiceTest {

    @Autowired
    TaskDAO dao;

    @Autowired
    TaskService service;

    @Autowired
    StatusConverter statusConverter;

    @ParameterizedTest
    @MethodSource("provideStringsForCreate")
    void create_shouldReturnTaskWithGivenProperties(String description, Status status) {
        Mockito.doReturn(
                new ru.javarush.todo.persistence.entity.Task(1L, description, statusConverter.toDestination(status))
        ).when(dao).create(Mockito.any());

        Task newTask = service.create(description, status);

        assertThat(newTask.getDescription()).isEqualTo(description);
        assertThat(newTask.getStatus()).isEqualTo(status);
        assertThat(newTask.getId()).isPositive();
    }

    private static Stream<Arguments> provideStringsForCreate() {
        return Stream.of(
                Arguments.of("New task", Status.IN_PROGRESS)
        );
    }

    @Test
    void update_shouldUpdateStoredTask() throws ServiceException {
        final String newDescription = "Updating task";
        final Status newStatus = Status.DONE;
        final Long id = 1L;

        Mockito.doReturn(
                new ru.javarush.todo.persistence.entity.Task(id, "Task", statusConverter.toDestination(Status.IN_PROGRESS))
        ).when(dao).findById(id);

        service.update(id, newDescription, newStatus);

        var dbTask = dao.findById(id);

        assertThat(dbTask.getId()).isEqualTo(id);
        assertThat(dbTask.getDescription()).isEqualTo(newDescription);
        assertThat(statusConverter.toSource(dbTask.getStatus())).isEqualTo(newStatus);
    }

    @Test
    void delete_shouldDeleteStoredTask() throws ServiceException {
        final long id = 1L;

        Mockito.doReturn(
                new ru.javarush.todo.persistence.entity.Task(id, "Deleting task", statusConverter.toDestination(Status.IN_PROGRESS))
        ).when(dao).findById(id);

        service.delete(id);

        Mockito.doReturn(null).when(dao).findById(id);

        var dbTask = dao.findById(id);

        assertThat(dbTask).isNull();
    }

    @Test
    void getInRange_shouldReturnTasksInGivenRange() {
        Task task1 = new Task(1L, "Task 1", Status.DONE);
        Task task2 = new Task(2L, "Task 2", Status.PAUSED);

        Mockito.doReturn(
                Arrays.asList(
                        new ru.javarush.todo.persistence.entity.Task(task1.getId(), task1.getDescription(), statusConverter.toDestination(task1.getStatus())),
                        new ru.javarush.todo.persistence.entity.Task(task2.getId(), task2.getDescription(), statusConverter.toDestination(task2.getStatus())))
        ).when(dao).findInRange(0, 2);

        List<Task> tasks = service.getInRange(0, 2);

        assertThat(tasks.get(0)).usingRecursiveComparison().isEqualTo(task1);
        assertThat(tasks.get(1)).usingRecursiveComparison().isEqualTo(task2);
    }
}