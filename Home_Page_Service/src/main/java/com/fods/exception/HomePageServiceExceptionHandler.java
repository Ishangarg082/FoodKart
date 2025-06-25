package com.fods.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HomePageServiceExceptionHandler {

    @ExceptionHandler(RestaurantFetchException.class)
    public ResponseEntity<Object> handleRestaurantFetchException(RestaurantFetchException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Restaurant Service Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(TagFetchException.class)
    public ResponseEntity<Object> handleTagFetchException(TagFetchException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Tag Service Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
