package com.scytalys.technikon.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.OptimisticLockingFailureException;
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

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<String> handleDatabaseAccessFailure(DataAccessResourceFailureException e){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", "Database resource access failed: " + e.getMessage());

        return new ResponseEntity<>(null, headers, HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", "Request resource is busy: " + e.getMessage());
        return new ResponseEntity<>("Failed to update due to concurrent modification. Please try again.", headers, HttpStatus.CONFLICT);
    }

}
