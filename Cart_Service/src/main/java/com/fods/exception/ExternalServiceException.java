package com.fods.exception;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message) {
        super(message);
    }

    public ExternalServiceException(String failedToFetchItemDetails, Exception e) {
    }
}
