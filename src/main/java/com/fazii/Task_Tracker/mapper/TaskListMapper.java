package com.fazii.Task_Tracker.mapper;

import com.fazii.Task_Tracker.domain.dto.TaskListDto;
import com.fazii.Task_Tracker.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
