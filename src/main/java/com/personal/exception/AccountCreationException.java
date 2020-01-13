package com.personal.exception;

public class AccountCreationException extends PersistenceException {
    public AccountCreationException(String message) {
        super(message, null);
    }
}
