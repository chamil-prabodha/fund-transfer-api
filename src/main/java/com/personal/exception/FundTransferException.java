package com.personal.exception;

import com.personal.model.response.ErrorCode;

public class FundTransferException extends Exception {
    private final ErrorCode errorCode;

    public FundTransferException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
