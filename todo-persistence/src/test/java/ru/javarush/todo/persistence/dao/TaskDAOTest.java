package ru.javarush.todo.persistence.dao;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.javarush.todo.persistence.config.TestPersistenceConfig;
import ru.javarush.todo.persistence.entity.Status;
import ru.javarush.todo.persistence.entity.Task;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(TestPersistenceConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class TaskDAOTest {

    @Autowired
    TaskDAO dao;

    @Test
    void create_shouldStoreTask() {
        Task task = new Task();
        task.setDescription("New task");
        task.setStatus(Status.IN_PROGRESS);
        dao.create(task);

        Task foundTask = dao.findById(task.getId());

        assertThat(foundTask).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void update_shouldModifyTask() {
        Task task = new Task();
        task.setDescription("New task");
        task.setStatus(Status.IN_PROGRESS);
        dao.create(task);

        task.setDescription("Updated task");
        dao.update(task);

        Task foundTask = dao.findById(task.getId());

        assertThat(foundTask).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void delete_shouldRemoveTask() {
        Task task = new Task();
        task.setDescription("Deleting task");
        task.setStatus(Status.IN_PROGRESS);
        dao.create(task);

        dao.delete(task);

        Task foundTask = dao.findById(task.getId());

        assertThat(foundTask).isNull();
    }

    @Test
    void countAll_shouldReturnCountOfAllStoredTasks() {
        Task task1 = new Task();
        task1.setDescription("Task 1");
        task1.setStatus(Status.PAUSED);

        Task task2 = new Task();
        task2.setDescription("Task 2");
        task2.setStatus(Status.DONE);

        dao.create(task1);
        dao.create(task2);

        long tasksCount = dao.countAll();

        assertThat(tasksCount).isEqualTo(2);
    }

    @Test
    void findAll_shouldReturnAllStoredTasks() {
        Task task1 = new Task();
        task1.setDescription("Task 1");
        task1.setStatus(Status.PAUSED);

        Task task2 = new Task();
        task2.setDescription("Task 2");
        task2.setStatus(Status.DONE);

        List<Task> expectedTasks = Arrays.asList(task1, task2);

        dao.create(task1);
        dao.create(task2);

        List<Task> foundTasks = dao.findAll();
        assertThat(foundTasks).usingRecursiveComparison().isEqualTo(expectedTasks);
    }

    @Test
    void findById_shouldReturnStoredTask_whenGivenId() {
        Task task = new Task();
        task.setDescription("Task 1");
        task.setStatus(Status.PAUSED);

        dao.create(task);

        Task foundTask = dao.findById(task.getId());

        assertThat(foundTask).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void findInRange_shouldReturnStoredTasksInGivenRange() {
        final Task task1 = new Task();
        task1.setDescription("Task 1");
        task1.setStatus(Status.PAUSED);

        final Task task2 = new Task();
        task2.setDescription("Task 2");
        task2.setStatus(Status.DONE);

        final Task task3 = new Task();
        task3.setDescription("Task 3");
        task3.setStatus(Status.IN_PROGRESS);

        final Task task4 = new Task();
        task4.setDescription("Task 4");
        task4.setStatus(Status.DONE);

        dao.create(task1);
        dao.create(task2);
        dao.create(task3);
        dao.create(task4);

        List<Task> foundTasks = dao.findInRange(1, 2);

        List<Task> expected = Arrays.asList(task2, task3);
        assertThat(foundTasks).usingRecursiveComparison().isEqualTo(expected);
    }
}