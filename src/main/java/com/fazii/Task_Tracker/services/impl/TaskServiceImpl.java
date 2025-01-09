package com.fazii.Task_Tracker.services.impl;

import com.fazii.Task_Tracker.domain.entities.Task;
import com.fazii.Task_Tracker.domain.entities.TaskList;
import com.fazii.Task_Tracker.domain.entities.TaskPriority;
import com.fazii.Task_Tracker.domain.entities.TaskStatus;
import com.fazii.Task_Tracker.repositories.TaskListRepository;
import com.fazii.Task_Tracker.repositories.TaskRepository;
import com.fazii.Task_Tracker.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }


    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {

        if(null!= task.getId()){
            throw new IllegalArgumentException("Task already has an ID");
        }
        if(null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task title should not be empty");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(
                TaskPriority.MEDIUM
        );
        TaskStatus status = TaskStatus.OPEN;

        LocalDateTime now = LocalDateTime.now();

        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(()->
                new IllegalArgumentException("Task list does not exist"));

        Task createdTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                status,
                taskPriority,
                taskList,
                now,now
        );

        return taskRepository.save(createdTask);
    }

    @Override
    public Optional<Task> getTaskById(UUID taskListId, UUID taskId) {

        return taskRepository.findByTaskListIdAndId(taskListId,taskId);

    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(null == task.getId()){
            throw new IllegalArgumentException("Task should have an ID");
        }
        if(!Objects.equals(taskId, task.getId())){
            throw new IllegalArgumentException("Task id does not match");
        }
        if(null == task.getPriority()){
            throw new IllegalArgumentException("Task should have a priority");
        }
        if(null == task.getStatus()){
            throw new IllegalArgumentException("Task should have a status");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId).orElseThrow(
                ()-> new IllegalArgumentException("Task not found")
        );
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
