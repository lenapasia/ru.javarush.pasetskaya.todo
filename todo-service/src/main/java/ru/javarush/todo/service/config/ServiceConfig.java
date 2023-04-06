package ru.javarush.todo.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "ru.javarush.todo.service" })
public class ServiceConfig {
}
