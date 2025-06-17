package com.handleGod.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRefillStrategy.class)
    public ResponseEntity<String> handleInvalidRefillStrategy(InvalidRefillStrategy e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

}
