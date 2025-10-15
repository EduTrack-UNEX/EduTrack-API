package br.com.unex.edutrack.dto.user.user;

import java.time.LocalDateTime;

public record AuthResponseDto(
        int id,
        String name,
        String email,
        String token,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
