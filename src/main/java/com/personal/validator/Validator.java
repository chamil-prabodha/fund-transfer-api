package com.personal.validator;


import com.personal.exception.ObjectValidationException;
import com.personal.model.response.ErrorCode;

public interface Validator<T> {
    void setNext(Validator<T> next);
    void validate(T data) throws ObjectValidationException;
    default void checkIfNull(T data) throws ObjectValidationException {
        if (data == null) {
            throw new ObjectValidationException(ErrorCode.UNEXPECTED_ERROR, "object should not be null");
        }
    }
}
