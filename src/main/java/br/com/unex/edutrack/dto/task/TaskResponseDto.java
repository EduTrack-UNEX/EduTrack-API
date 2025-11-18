package br.com.unex.edutrack.dto.task;

import br.com.unex.edutrack.model.Task;

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
    public static TaskResponseDto fromEntity(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getName(),
                task.getGrade(),
                task.isCompleted(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
