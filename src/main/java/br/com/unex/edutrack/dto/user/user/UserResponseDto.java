package br.com.unex.edutrack.dto.user.user;

import java.time.LocalDateTime;

public record UserResponseDto(
        int id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedA
) {

}
