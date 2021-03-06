package com.personal.exception;

import com.personal.model.response.ErrorCode;

public class RequestHandlerException extends Exception {
    private final ErrorCode errorCode;

    public RequestHandlerException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
