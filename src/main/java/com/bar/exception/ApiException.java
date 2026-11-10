package com.bar.exception;

public abstract class ApiException extends RuntimeException {

    protected ApiException(String message) {
        super(message);
    }

    public abstract int getStatus();
}
