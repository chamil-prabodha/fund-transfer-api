package com.personal.service;

import com.personal.dao.AccountRepository;
import com.personal.dao.Repository;
import com.personal.exception.PersistenceException;
import com.personal.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

    private final Repository<String, Account> accountRepository;
    private final int offset;

    public AccountServiceImpl(Repository<String, Account> accountRepository, int offset) {
        this.accountRepository = accountRepository;
        this.offset = offset;
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accountRepository.findOne(accountNumber);
    }

    @Override
    public Account createAccount() throws PersistenceException {
        String accountNumber = String.valueOf(offset);
        int currentIndex = ((AccountRepository) accountRepository).getSize();
        if (currentIndex != 0) {
            accountNumber = String.valueOf(offset + currentIndex);
        }
        Account account = new Account(accountNumber, BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY));
        Account createdAccount = accountRepository.save(account);
        LOGGER.info("created account: {}", createdAccount.getAccountNumber());
        return createdAccount;
    }
}
