package com.example.demo.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Advice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIncommingRequest(Exception exception) {
        String errorMessage = exception.getLocalizedMessage();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Action impossible : " + errorMessage);
    }
}