package com.personal.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.exception.ObjectValidationException;
import com.personal.model.Validatable;
import com.personal.model.response.ErrorCode;
import com.personal.validator.DecimalAmountValidator;
import com.personal.validator.NonEmptyStringValidator;
import com.personal.validator.PositiveAmountValidator;
import com.personal.validator.Validator;

import java.math.BigDecimal;

public class TransferRequest implements Validatable {
    @JsonProperty
    private String fromAccount;
    @JsonProperty
    private String toAccount;
    @JsonProperty
    private BigDecimal amount;

    @JsonIgnore
    private Validator<BigDecimal> amountValidator;
    @JsonIgnore
    private Validator<String> nonEmptyAccountValidator;

    public TransferRequest() {
        Validator<BigDecimal> positiveAmountValidator = new PositiveAmountValidator();
        amountValidator = new DecimalAmountValidator();
        nonEmptyAccountValidator = new NonEmptyStringValidator();
        amountValidator.setNext(positiveAmountValidator);
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferRequest{" +
                "fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public void validate() throws ObjectValidationException {
        amountValidator.validate(amount);
        try {
            nonEmptyAccountValidator.validate(fromAccount);
            nonEmptyAccountValidator.validate(toAccount);
        } catch (ObjectValidationException e) {
            throw new ObjectValidationException(ErrorCode.INVALID_ACC_NUM, "invalid account number");
        }
    }
}
