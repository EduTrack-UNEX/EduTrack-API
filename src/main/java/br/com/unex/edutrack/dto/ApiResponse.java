package br.com.unex.edutrack.dto;

public record ApiResponse<T>(

        String message,
        T data
) {
}
