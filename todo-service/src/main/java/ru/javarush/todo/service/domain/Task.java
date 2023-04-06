package ru.javarush.todo.service.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {
    private long id;
    private String description;
    private Status status;
}
