package com.fods.exception;

public class TagFetchException extends RuntimeException {
    public TagFetchException(String failedToFetchCuisineTags, Exception e) {
    }

    public TagFetchException(String message) {
        super(message);
    }
}
