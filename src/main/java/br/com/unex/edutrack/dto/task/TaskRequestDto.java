package br.com.unex.edutrack.dto.task;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskRequestDto(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotNull(message = "A nota é obrigatória")
        @DecimalMin(value = "0.00", message = "A nota mínima é 0.00")
        @DecimalMax(value = "10.00", message = "A nota máxima é 10.00")
        @Digits(integer = 2, fraction = 2, message = "A nota deve ter no máximo 2 dígitos inteiros e " +
                "até 2 casas decimais (ex: 7.85)")
        BigDecimal grade,

        @NotNull(message = "A data de vencimento é obrigatória")
        LocalDate dueDate

) {
}
