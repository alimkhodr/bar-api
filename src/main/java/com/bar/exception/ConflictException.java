package com.bar.exception;

public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return 409;
    }
}
