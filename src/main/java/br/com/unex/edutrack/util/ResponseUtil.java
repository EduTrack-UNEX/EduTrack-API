package br.com.unex.edutrack.util;

import br.com.unex.edutrack.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private ResponseUtil() {}

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(message, data));
    }

}
