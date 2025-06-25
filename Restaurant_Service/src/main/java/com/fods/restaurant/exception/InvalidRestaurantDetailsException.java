package com.fods.restaurant.exception;

public class InvalidRestaurantDetailsException extends RuntimeException {
    public InvalidRestaurantDetailsException(String s) {
    }

    public InvalidRestaurantDetailsException(String message, Exception cause) {
        super(message, cause);
    }
}
