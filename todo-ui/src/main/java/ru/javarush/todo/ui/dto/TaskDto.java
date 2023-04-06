package ru.javarush.todo.ui.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskDto {
    private long id;
    private String description;
    private String status;
}
