package ru.javarush.todo.ui.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaveTaskModel {
    private String description;
    private String status;
}
