package com.personal.validator;

import com.personal.exception.ObjectValidationException;
import com.personal.model.Account;

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
        accountNumberValidator.validate(data.getAccountNumber());
    }
}
