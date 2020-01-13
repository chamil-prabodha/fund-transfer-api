package com.personal.service;

import com.personal.dao.Repository;
import com.personal.exception.InsufficientBalanceException;
import com.personal.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class FundTransferServiceImpl implements FundTransferService {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferServiceImpl.class);
    private static FundTransferService instance;

    private FundTransferServiceImpl() {
    }

    public static FundTransferService getInstance() {
        if (instance == null) {
            instance = new FundTransferServiceImpl();
        }
        return instance;
    }

    @Override
    public void transfer(Account from, Account to, BigDecimal amount) throws InsufficientBalanceException {
        LOGGER.info("starting transaction - transfer of: {} from account: {} to account: {}", amount, from.getAccountNumber(), to.getAccountNumber());
        from.withdraw(amount);
        to.deposit(amount);
        LOGGER.info("ending transaction - transfer of: {} from account: {} to account: {}", amount, from.getAccountNumber(), to.getAccountNumber());
    }
}
