package com.fazii.Task_Tracker.domain.dto;

import com.fazii.Task_Tracker.domain.entities.TaskPriority;
import com.fazii.Task_Tracker.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status,
        TaskPriority priority
) {
}
