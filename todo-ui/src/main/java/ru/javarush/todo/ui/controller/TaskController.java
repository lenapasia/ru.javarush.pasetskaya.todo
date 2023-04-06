package ru.javarush.todo.ui.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javarush.todo.annotation.Log;
import ru.javarush.todo.service.TaskService;
import ru.javarush.todo.service.domain.Status;
import ru.javarush.todo.service.domain.Task;
import ru.javarush.todo.service.exception.ServiceException;
import ru.javarush.todo.ui.dto.SaveTaskModel;
import ru.javarush.todo.ui.dto.TaskDto;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class TaskController {

    public static final String TASKS = "tasks";
    private static @Log Logger logger;

    final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/")
    public String getTasks(Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) {
        List<Task> tasks = taskService.getInRange((page - 1) * limit, limit);
        List<TaskDto> dtoTasks = tasks.stream()
                .map(this::convertTask)
                .toList();

        model.addAttribute("tasks", dtoTasks);
        model.addAttribute("current_page", page);

        long tasksCount = taskService.countAll();
        int totalPages = (int) Math.ceil(1.0 * tasksCount / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
        }

        return TASKS;
    }

    TaskDto convertTask(Task task) {
        return new TaskDto(task.getId(), task.getDescription(), task.getStatus().name());
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createTask(Model model, @RequestBody SaveTaskModel saveTaskModel) {
        logger.debug("Start creating task ...");

        Status taskStatus = Enum.valueOf(Status.class, saveTaskModel.getStatus());
        Task newTask = taskService.create(saveTaskModel.getDescription(), taskStatus);

        logger.info("New task has been created: {}.", newTask);

        return getTasks(model, 1, 10);
    }

    @PutMapping(value = "/{id}")
    public String updateTask(Model model, @PathVariable long id, @RequestBody SaveTaskModel saveTaskModel)
            throws ServiceException {
        logger.debug("Start updating task: id={}", id);

        Status taskStatus = Enum.valueOf(Status.class, saveTaskModel.getStatus());
        taskService.update(id, saveTaskModel.getDescription(), taskStatus);

        logger.info("Task with id '{}' has been updated.", id);

        return getTasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable long id) throws ServiceException {
        logger.debug("Start deleting task: id={}", id);

        taskService.delete(id);

        logger.info("Task with id '{}' has been deleted.", id);

        return TASKS;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleException(ServiceException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}