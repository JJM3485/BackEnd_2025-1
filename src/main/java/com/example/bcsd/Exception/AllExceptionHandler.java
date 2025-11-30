package com.example.bcsd.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(AllException.class)
    public ResponseEntity<Map<String, Object>> handleAllException(AllException ex) {
        Map<String, Object> response = Map.of(
                "status", ex.getHttpStatus().value(),
                "error", ex.getHttpStatus().name(),
                "message", ex.getMessage()
        );

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }
}
