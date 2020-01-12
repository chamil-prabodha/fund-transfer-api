package com.personal.exception;

public class AccountCreationException extends Exception {
    private final String message;

    public AccountCreationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
