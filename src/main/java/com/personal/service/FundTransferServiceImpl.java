package com.personal.service;

import com.personal.dao.Repository;
import com.personal.model.Account;

public class FundTransferServiceImpl implements FundTransferService {
    private Repository accountRepository;

    public FundTransferServiceImpl(Repository repository) {
        this.accountRepository = repository;
    }

    @Override
    public boolean transfer(Account from, Account to) {
        return false;
    }
}
