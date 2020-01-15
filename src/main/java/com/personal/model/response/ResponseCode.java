package com.personal.model.response;

public enum ResponseCode {
    INVALID_ACC_NUM(1, "the account number is not valid"),
    ACC_NOT_FOUND(2, "the account not found"),
    INVALID_AMOUNT(3, "the amount is not valid"),
    INVALID_REQUEST(4, "the request is not valid"),
    INSUFFICIENT_BALANCE(5,"insufficient balance");

    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
