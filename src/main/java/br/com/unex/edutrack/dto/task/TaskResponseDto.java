package br.com.unex.edutrack.dto.task;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponseDto(
        int id,
        String name,
        BigDecimal grade,
        boolean isCompleted,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
