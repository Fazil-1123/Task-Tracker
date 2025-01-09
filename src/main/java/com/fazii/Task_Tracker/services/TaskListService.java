package com.fazii.Task_Tracker.services;

import com.fazii.Task_Tracker.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {

    List<TaskList> listTaskLists();

    TaskList createTaskList(TaskList taskList);

    Optional<TaskList> getTaskList(UUID id);

    TaskList updateTaskList(UUID id, TaskList taskList);

    void deleteById(UUID id);
}
