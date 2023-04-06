package ru.javarush.todo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.todo.persistence.config.PersistenceConfig;
import ru.javarush.todo.service.config.ServiceConfig;
import ru.javarush.todo.service.domain.Task;

import java.util.List;

@Disabled
@SpringJUnitConfig(classes = {ServiceConfig.class, PersistenceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoServiceAppTest {

    @Autowired
    TaskService service;

    @Test
    @Transactional
    void initializationOfSpringContextWithActualConfig_shouldNotThrowException() {
        Assertions.assertDoesNotThrow(() ->
        {
            List<Task> tasks = service.getInRange(0, 20);
            tasks.forEach(System.out::println);
        });
    }
}
