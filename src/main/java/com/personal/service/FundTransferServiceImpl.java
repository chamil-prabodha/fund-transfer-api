package com.personal.service;

import com.personal.dao.Repository;
import com.personal.exception.InsufficientBalanceException;
import com.personal.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class FundTransferServiceImpl implements FundTransferService {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferServiceImpl.class);

    private Repository accountRepository;

    public FundTransferServiceImpl(Repository repository) {
        this.accountRepository = repository;
    }

    @Override
    public boolean transfer(Account from, Account to, BigDecimal amount) {
        try {
            from.withdraw(amount);
            to.deposit(amount);
        } catch (InsufficientBalanceException e) {
            LOGGER.error("insufficient funds to transfer from account: {}", from.getAccountNumber());
            return false;
        }
        LOGGER.info("transfer of: {} from account: {} to account: {}", amount, from.getAccountNumber(), to.getAccountNumber());
        return true;
    }
}
