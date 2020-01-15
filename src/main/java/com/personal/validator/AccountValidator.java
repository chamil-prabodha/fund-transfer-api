package com.personal.validator;

import com.personal.exception.ObjectValidationException;
import com.personal.model.Account;
import com.personal.model.response.ErrorCode;

import java.math.BigDecimal;

public class AccountValidator implements Validator<Account> {
    private Validator<String> accountNumberValidator;
    private Validator<BigDecimal> balanceValidator;

    public AccountValidator() {
        accountNumberValidator = new NonEmptyStringValidator();
        balanceValidator = new DecimalAmountValidator();
        balanceValidator.setNext(new PositiveAmountValidator());
    }
    @Override
    public void setNext(Validator<Account> next) {
       // not implemented
    }

    @Override
    public void validate(Account data) throws ObjectValidationException {
        checkIfNull(data);
        balanceValidator.validate(data.getBalance());
        try {
            accountNumberValidator.validate(data.getAccountNumber());
        } catch (ObjectValidationException e) {
            throw new ObjectValidationException(ErrorCode.INVALID_ACC_NUM, "invalid account number");
        }
    }
}
