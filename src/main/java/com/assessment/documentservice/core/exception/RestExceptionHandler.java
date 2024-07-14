package com.assessment.documentservice.core.exception;

import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAppExceptions(Exception e) {
        return new ResponseEntity<>(
                new ExceptionResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
