package com.personal.exception;

import com.personal.model.response.ResponseCode;

public class RequestHandlerException extends Exception {
    private final ResponseCode responseCode;
    public RequestHandlerException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
