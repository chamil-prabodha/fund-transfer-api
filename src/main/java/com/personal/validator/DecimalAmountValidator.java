package com.personal.validator;

import com.personal.exception.ObjectValidationException;
import com.personal.model.response.ErrorCode;

import java.math.BigDecimal;

public class DecimalAmountValidator implements Validator<BigDecimal> {
    private Validator<BigDecimal> nextValidator;

    @Override
    public void setNext(Validator<BigDecimal> next) {
        this.nextValidator = next;
    }

    @Override
    public void validate(BigDecimal data) throws ObjectValidationException {
        checkIfNull(data);
        if (data.scale() != 2) {
            throw new ObjectValidationException(ErrorCode.INVALID_DECIMAL_PLACES, "amount is not in two decimal places");
        } else if (nextValidator != null) {
            nextValidator.validate(data);
        }
    }
}
