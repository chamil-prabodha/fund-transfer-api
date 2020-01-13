package com.personal.dao;

import com.personal.exception.AccountCreationException;
import com.personal.exception.PersistenceException;
import com.personal.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements Repository<String, Account> {
    private static AccountRepository instance = null;
    private MapDataSource<String, Account> mapDataSource;

    private AccountRepository() {
        mapDataSource = new MapDataSource<>();
    }

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    @Override
    public Account findOne(String id) {
        return mapDataSource.getSource().get(id);
    }

    @Override
    public List<Account> findAll(String id) {
        List<Account> accounts = new ArrayList<>();
        if (mapDataSource.getSource().get(id) != null) {
            accounts.add(mapDataSource.getSource().get(id));
        }
        return accounts;
    }

    @Override
    public Account save(Account obj) throws PersistenceException {
        if (obj == null || obj.getAccountNumber() == null) {
            throw new AccountCreationException("account or account number cannot be null");
        }

        if (findOne(obj.getAccountNumber()) != null) {
            throw new AccountCreationException("an account with a given account number already exists");
        }

        mapDataSource.getSource().put(obj.getAccountNumber(), obj);
        return mapDataSource.getSource().get(obj.getAccountNumber());
    }

    @Override
    public void saveAll(List<Account> list) {
        // not implemented
    }

}
