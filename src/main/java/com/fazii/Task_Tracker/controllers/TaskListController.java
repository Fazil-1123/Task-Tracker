package com.fazii.Task_Tracker.controllers;

import com.fazii.Task_Tracker.domain.dto.TaskListDto;
import com.fazii.Task_Tracker.domain.entities.TaskList;
import com.fazii.Task_Tracker.mapper.TaskListMapper;
import com.fazii.Task_Tracker.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListService taskListService;

    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }


    @GetMapping
    public List<TaskListDto> listTaskList(){
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){
        TaskList taskList = taskListMapper.fromDto(taskListDto);
        TaskList savedTaskList = taskListService.createTaskList(taskList);
        return taskListMapper.toDto(savedTaskList);
    }
    @GetMapping(path = "/{task-list-id}")
    public Optional<TaskListDto> getTaskListById(@PathVariable("task-list-id") UUID id){
        return taskListService.getTaskList(id)
                .map(taskListMapper ::toDto);
    }
    @PutMapping(path = "/{id}")
    public TaskListDto updateTaskList(@PathVariable("id") UUID id, @RequestBody TaskListDto taskListDto){
        TaskList taskList = taskListService.updateTaskList(id,taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(taskList);

    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") UUID id){
        taskListService.deleteById(id);
    }


}
