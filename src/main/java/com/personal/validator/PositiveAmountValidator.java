package com.personal.validator;

import com.personal.exception.ObjectValidationException;

import java.math.BigDecimal;

public class PositiveAmountValidator implements Validator<BigDecimal> {
    private Validator<BigDecimal> nextValidator;

    @Override
    public void setNext(Validator<BigDecimal> next) {
        this.nextValidator = next;
    }

    @Override
    public void validate(BigDecimal data) throws ObjectValidationException {
        checkIfNull(data);
        if (data.compareTo(BigDecimal.ZERO) < 0) {
            throw new ObjectValidationException("amount is not positive");
        } else if (nextValidator != null) {
            nextValidator.validate(data);
        }
    }
}
