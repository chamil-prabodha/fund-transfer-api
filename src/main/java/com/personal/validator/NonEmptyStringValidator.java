package com.personal.validator;

import com.personal.exception.ObjectValidationException;

public class NonEmptyStringValidator implements Validator<String> {
    private Validator<String> nextValidator;

    @Override
    public void setNext(Validator<String> next) {
        this.nextValidator = next;
    }

    @Override
    public void validate(String data) throws ObjectValidationException {
        checkIfNull(data);
        if (data.isEmpty()) {
            throw new ObjectValidationException("string is null or empty");
        } else if (nextValidator != null) {
            nextValidator.validate(data);
        }
    }
}
