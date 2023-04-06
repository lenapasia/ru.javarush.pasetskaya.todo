package ru.javarush.todo.service.converters;

import org.springframework.stereotype.Component;
import ru.javarush.todo.converters.EnumToEnumConverter;
import ru.javarush.todo.converters.EnumToEnumMapping;
import ru.javarush.todo.service.domain.Status;

@Component
public class StatusConverter extends EnumToEnumConverter<Status, ru.javarush.todo.persistence.entity.Status> {
    @Override
    protected EnumToEnumMapping buildMapping() {
        return EnumToEnumMapping.builder()
                .source(Status.class)
                .destination(ru.javarush.todo.persistence.entity.Status.class)
                .add(Status.IN_PROGRESS, ru.javarush.todo.persistence.entity.Status.IN_PROGRESS)
                .add(Status.DONE, ru.javarush.todo.persistence.entity.Status.DONE)
                .add(Status.PAUSED, ru.javarush.todo.persistence.entity.Status.PAUSED)
                .build();
    }
}