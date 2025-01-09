package com.fazii.Task_Tracker.mapper.impl;

import com.fazii.Task_Tracker.domain.dto.TaskListDto;
import com.fazii.Task_Tracker.domain.entities.Task;
import com.fazii.Task_Tracker.domain.entities.TaskList;
import com.fazii.Task_Tracker.domain.entities.TaskStatus;
import com.fazii.Task_Tracker.mapper.TaskListMapper;
import com.fazii.Task_Tracker.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks->tasks.stream().map(taskMapper::fromDto).toList()).orElse(null),
                null,null);


    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTask()).map(List::size)
                        .orElse(0),
                calculateTaskListProgress(taskList.getTask()),
                Optional.ofNullable(taskList.getTask()).map(
                        tasks -> tasks.stream().map(taskMapper::toDto).toList()).orElse(null)
                );

    }

    private Double calculateTaskListProgress(List<Task> tasks){
        if(null == tasks){
            return null;
        }
        long closedTaskCount = tasks.stream().filter(task ->
                task.getStatus() == TaskStatus.CLOSED).count();

        return (double) closedTaskCount / tasks.size();
    }
}
