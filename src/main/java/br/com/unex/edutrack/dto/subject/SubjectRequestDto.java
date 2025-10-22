package br.com.unex.edutrack.dto.subject;

import jakarta.validation.constraints.NotBlank;

public record SubjectRequestDto(

        @NotBlank(message = "O nome é obrigatório")
        String name) {
}
