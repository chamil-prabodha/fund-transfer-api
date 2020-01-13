package com.personal.service;

import com.personal.dao.AccountRepository;
import com.personal.dao.Repository;
import com.personal.exception.PersistenceException;
import com.personal.model.Account;

import java.math.BigDecimal;


public class AccountServiceImpl implements AccountService {
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
        Account account = new Account(accountNumber, BigDecimal.ZERO);
        return accountRepository.save(account);
    }
}
