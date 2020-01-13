package com.personal.service;

import com.personal.dao.Repository;
import com.personal.exception.PersistenceException;
import com.personal.model.Account;


public class AccountServiceImpl implements AccountService {
    private final Repository<String, Account> accountRepository;

    public AccountServiceImpl(Repository<String, Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(String accountNumber) {
        return null;
    }

    @Override
    public Account createAccount() throws PersistenceException {
        return null;
    }
}
