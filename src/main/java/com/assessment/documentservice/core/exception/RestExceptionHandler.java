package com.assessment.documentservice.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResponse> handleAppExceptions(AppException e) {
        return new ResponseEntity<>(
                new ExceptionResponse(LocalDateTime.now(), e.getStatus().value(), e.getStatus().getReasonPhrase(), e.getMessage()),
                e.getStatus()
        );
    }
}
