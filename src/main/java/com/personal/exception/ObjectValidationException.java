package com.personal.exception;

import com.personal.model.response.ErrorCode;

public class ObjectValidationException extends Exception {
    private final ErrorCode errorCode;
    public ObjectValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
