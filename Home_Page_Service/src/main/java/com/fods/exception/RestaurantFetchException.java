package com.fods.exception;

public class RestaurantFetchException extends RuntimeException {
    public RestaurantFetchException(String s, Exception e) {
    }

    public RestaurantFetchException(String message) {
        super(message);
    }
}
