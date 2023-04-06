package ru.javarush.todo.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "ru.javarush.todo.persistence.dao" })
@ImportResource({ "classpath:persistence-config.xml" })
public class PersistenceConfig {
}
