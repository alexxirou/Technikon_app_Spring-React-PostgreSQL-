package com.scytalys.technikon.exception;
import com.scytalys.technikon.utility.HeaderUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
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
        HttpHeaders headers = HeaderUtility.createHeaders("Error-message", e.getMessage());
        return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpHeaders headers = HeaderUtility.createHeaders("Error-message", e.getMessage());
        return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<String> handleDatabaseAccessFailure(DataAccessResourceFailureException e){
        HttpHeaders headers = HeaderUtility.createHeaders("Error-message", e.getMessage());
        return new ResponseEntity<>(null, headers, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        HttpHeaders headers = HeaderUtility.createHeaders("Error-message", e.getMessage());
        return new ResponseEntity<>("Failed to modify resource because of high traffic. Please try again.", headers, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataException(Exception e) {
        HttpHeaders headers = HeaderUtility.createHeaders("Error-message", e.getMessage());
        return new ResponseEntity<>( headers, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpHeaders headers = HeaderUtility.createHeaders("Error-message", e.getMessage());
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }
}
