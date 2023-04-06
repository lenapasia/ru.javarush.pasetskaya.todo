package ru.javarush.todo.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.javarush.todo.persistence.dao.TaskDAO;

@Profile("test")
@Configuration
class TaskServiceTestConfig {

    @Primary
    @Bean
    public TaskDAO taskDAO() {
        return Mockito.mock(TaskDAO.class);
    }
}
