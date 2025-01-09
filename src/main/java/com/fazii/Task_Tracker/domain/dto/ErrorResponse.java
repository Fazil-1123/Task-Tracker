package com.fazii.Task_Tracker.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
