package com.personal.model.response;

public enum ErrorCode {
    INVALID_ACC_NUM(1, "the account number is not valid"),
    ACC_NOT_FOUND(2, "the account not found"),
    INVALID_AMOUNT(3, "the amount is not valid"),
    INVALID_REQUEST(4, "the request is not valid"),
    INSUFFICIENT_BALANCE(5,"insufficient balance"),
    NEGATIVE_AMOUNT(6, "amount cannot be negative"),
    INVALID_DECIMAL_PLACES(7, "should contain exactly two decimal palces"),
    INVALID_STRING(8, "invalid string"),
    UNEXPECTED_ERROR(9, "unexpected error"),
    NULL_OBJECT(10, "null object detected");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
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
