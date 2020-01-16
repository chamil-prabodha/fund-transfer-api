package com.personal.service;

import com.personal.exception.FundTransferException;
import com.personal.exception.InsufficientBalanceException;
import com.personal.exception.ObjectValidationException;
import com.personal.model.Account;
import com.personal.model.response.ErrorCode;
import com.personal.validator.AccountValidator;
import com.personal.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class FundTransferServiceImpl implements FundTransferService {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferServiceImpl.class);
    private static FundTransferService instance;

    private final Validator<Account> accountValidator;

    private FundTransferServiceImpl() {
        accountValidator = new AccountValidator();
    }

    public static FundTransferService getInstance() {
        if (instance == null) {
            instance = new FundTransferServiceImpl();
        }
        return instance;
    }

    @Override
    public void transfer(Account from, Account to, BigDecimal amount) throws FundTransferException {
        try {
            accountValidator.checkIfNull(from);
            accountValidator.checkIfNull(to);
            LOGGER.info("starting transaction - transfer of: {} from account: {} to account: {}", amount, from.getAccountNumber(), to.getAccountNumber());
            from.withdraw(amount);
            to.deposit(amount);
            LOGGER.info("ending transaction - transfer of: {} from account: {} to account: {}", amount, from.getAccountNumber(), to.getAccountNumber());
        } catch (ObjectValidationException e) {
            LOGGER.info("transaction failed due to validation error. rolled back");
            throw new FundTransferException(ErrorCode.ACC_NOT_FOUND, "null account object detected");
        } catch (InsufficientBalanceException e) {
            LOGGER.info("transaction failed due to insufficient balance. rolled back");
            throw new FundTransferException(ErrorCode.INSUFFICIENT_BALANCE, "insufficient balance");
        }
    }
}
