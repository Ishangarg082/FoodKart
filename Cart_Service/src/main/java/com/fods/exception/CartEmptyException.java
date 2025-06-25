package com.fods.exception;

import java.util.UUID;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String s, UUID userId) {
    }
}
