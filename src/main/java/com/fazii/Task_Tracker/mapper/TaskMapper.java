package com.fazii.Task_Tracker.mapper;

import com.fazii.Task_Tracker.domain.dto.TaskDto;
import com.fazii.Task_Tracker.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
