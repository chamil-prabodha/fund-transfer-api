package com.personal.exception;

public class InsufficientBalanceException extends Exception {
    private final String message;

    public InsufficientBalanceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
