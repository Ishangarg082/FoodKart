package com.fods.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CartServiceGlobalExceptionHandler {

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<Object> handleExternalServiceException(ExternalServiceException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Restaurant Service (Food Menu service) Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<Object> handleCartEmptyException(CartEmptyException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Restaurant Service (Food Menu service) Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Restaurant Service (Food Menu service) Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
