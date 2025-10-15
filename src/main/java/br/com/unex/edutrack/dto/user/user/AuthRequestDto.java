package br.com.unex.edutrack.dto.user.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(


        @NotBlank(message = "O email é obrigatorio ")
        @Email(message = "Email invalido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password

) {
}
