package com.fods.restaurant.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class RestaurantServiceExceptionHandler {
    private static final Logger logger = LogManager.getLogger(RestaurantServiceExceptionHandler.class);

    @ExceptionHandler(InvalidRestaurantDetailsException.class)
    public ResponseEntity<Object> handleInvalidRestaurantDetailsException(InvalidRestaurantDetailsException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Restaurant Service (Food Menu service) Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(FoodItemNotPresentException.class)
    public ResponseEntity<Object> handleFoodItemNotPresentException(FoodItemNotPresentException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Restaurant Service (Food Menu service) Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Menu Service Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MenuIdCannotByBlankException.class)
    public ResponseEntity<Object> handleMenuIdCannotByBlankException(MenuIdCannotByBlankException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        errorMap.put("error", "Menu Service Error");
        errorMap.put("message", exception.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
