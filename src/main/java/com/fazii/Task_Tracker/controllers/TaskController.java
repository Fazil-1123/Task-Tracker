package com.fazii.Task_Tracker.controllers;

import com.fazii.Task_Tracker.domain.dto.TaskDto;
import com.fazii.Task_Tracker.domain.entities.Task;
import com.fazii.Task_Tracker.mapper.TaskMapper;
import com.fazii.Task_Tracker.repositories.TaskRepository;
import com.fazii.Task_Tracker.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "task-lists/{task-list-id}/tasks")
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }


    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task-list-id")UUID taskListId){
        return taskService.listTasks(taskListId).stream()
                .map(taskMapper::toDto).toList();
    }

    @PostMapping
    private TaskDto createTask(@PathVariable("task-list-id")UUID taskListId,@RequestBody TaskDto taskDto){
        Task task = taskMapper.fromDto(taskDto);
        Task savedTask = taskService.createTask(taskListId, task);
        return taskMapper.toDto(savedTask);
    }

    @GetMapping(path = "{task-id}")
    public Optional<TaskDto> getTaskById(@PathVariable("task-list-id") UUID taskListId, @PathVariable("task-id")UUID taskId){
        return taskService.getTaskById(taskListId, taskId)
                .map(taskMapper::toDto);

    }

    @PutMapping(path = "{task-id}")
    public TaskDto updateTask(@PathVariable("task-list-id") UUID taskListId, @PathVariable("task-id")UUID taskId,
                @RequestBody TaskDto taskDto){
        Task updateTask = taskService.updateTask(taskListId,taskId,taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updateTask);
    }

    @DeleteMapping(path = "{task-id}")
    public void deleteTask(@PathVariable("task-list-id") UUID taskListId, @PathVariable("task-id")UUID taskId){
        taskService.deleteTask(taskListId,taskId);
    }
}
