package br.com.unex.edutrack.dto.subject;

import java.time.LocalDateTime;

public record SubjectResponseDto(
        int id,
        String name,
        int progress,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        float average

) {
}
