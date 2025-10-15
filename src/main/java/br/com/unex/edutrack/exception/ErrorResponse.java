package br.com.unex.edutrack.exception;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(int code, String message, Map<String, String> errors, LocalDateTime timestamp) {
}
