package com.example.bcsd.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AllException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AllException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}