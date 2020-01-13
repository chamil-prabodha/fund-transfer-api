package com.personal.exception;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
