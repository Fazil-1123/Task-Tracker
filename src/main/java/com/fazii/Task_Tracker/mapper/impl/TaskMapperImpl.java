package com.fazii.Task_Tracker.mapper.impl;

import com.fazii.Task_Tracker.domain.dto.TaskDto;
import com.fazii.Task_Tracker.domain.entities.Task;
import com.fazii.Task_Tracker.mapper.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                null,null,null);
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority());
    }
}
