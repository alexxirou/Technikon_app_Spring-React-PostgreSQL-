package com.scytalys.technikon.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", e.getMessage());

        return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", "Invalid request body: " + e.getMessage());

        return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
    }

}
