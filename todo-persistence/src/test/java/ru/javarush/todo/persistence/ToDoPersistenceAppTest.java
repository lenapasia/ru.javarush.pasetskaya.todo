package ru.javarush.todo.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.todo.persistence.config.PersistenceConfig;
import ru.javarush.todo.persistence.dao.TaskDAO;
import ru.javarush.todo.persistence.entity.Task;

import java.util.List;

@ActiveProfiles("prod")
@Disabled
@SpringJUnitConfig(PersistenceConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToDoPersistenceAppTest {

    @Autowired
    TaskDAO dao;

    @Test
    @Transactional
    void initializationOfSpringContextWithActualConfig_shouldNotThrowException() {
        Assertions.assertDoesNotThrow(() ->
        {
            List<Task> tasks = dao.findInRange(0, 100);
            tasks.forEach(System.out::println);
        });
    }
}
