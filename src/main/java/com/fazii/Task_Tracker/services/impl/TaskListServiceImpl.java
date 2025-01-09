package com.fazii.Task_Tracker.services.impl;

import com.fazii.Task_Tracker.domain.entities.TaskList;
import com.fazii.Task_Tracker.repositories.TaskListRepository;
import com.fazii.Task_Tracker.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService{

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null!= taskList.getId()){
            throw  new IllegalArgumentException("Task list already has an ID!");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present!");
        }
        LocalDateTime now = LocalDateTime.now();

        return taskListRepository.save(
                new TaskList(
                        null,taskList.getTitle(),
                        taskList.getDescription(),
                        null,now,now)

                );
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);

    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if(null == taskList.getId()){
            throw new IllegalArgumentException("Task List must have an id");
        }
        if(!Objects.equals(taskList.getId(),id)){
            throw new IllegalArgumentException("Request to change ID is not accepted");
        }
        TaskList taskListToUpdate = taskListRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("Task list doesnt exist"));
        taskListToUpdate.setTitle(taskList.getTitle());
        taskListToUpdate.setDescription(taskList.getDescription());
        taskListToUpdate.setUpdated(LocalDateTime.now());
        return taskListRepository.save(taskListToUpdate);

    }

    @Override
    public void deleteById(UUID id) {
        taskListRepository.deleteById(id);
    }
}
