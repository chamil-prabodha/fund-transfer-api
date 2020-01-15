package com.personal.dao;

import com.personal.exception.AccountCreationException;
import com.personal.exception.ObjectValidationException;
import com.personal.exception.PersistenceException;
import com.personal.model.Account;
import com.personal.validator.AccountValidator;
import com.personal.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements Repository<String, Account> {
    private static final Logger LOGGER = LogManager.getLogger(AccountRepository.class);
    private static AccountRepository instance = null;
    private MapDataSource<String, Account> mapDataSource;
    private final Validator<Account> accountValidator;

    private AccountRepository(MapDataSource<String, Account> mapDataSource) {
        this.mapDataSource = mapDataSource;
        accountValidator = new AccountValidator();
    }

    public static void init(MapDataSource<String, Account> mapDataSource) throws PersistenceException {
        if (instance == null) {
            instance = new AccountRepository(mapDataSource);
        } else {
            throw new PersistenceException("cannot call init on already initialized repository", null);
        }
    }

    public static AccountRepository getInstance() throws PersistenceException {
        if (instance == null) {
            throw new PersistenceException("cannot call getInstance on an uninitialized repository. call init first", null);
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
        if (findOne(id) != null) {
            accounts.add(mapDataSource.getSource().get(id));
        }
        return accounts;
    }

    @Override
    public Account save(Account obj) throws PersistenceException {
        try {
            accountValidator.validate(obj);
        } catch (ObjectValidationException e) {
            String error = e.getErrorCode() != null ? e.getErrorCode().getMessage() : "";
            LOGGER.error("unable to validate account. error: {}", error, e);
            throw new AccountCreationException("unable to validate account");
        }

        if (findOne(obj.getAccountNumber()) != null) {
            throw new AccountCreationException("an account with the given account number already exists");
        }

        mapDataSource.getSource().put(obj.getAccountNumber(), obj);
        return mapDataSource.getSource().get(obj.getAccountNumber());
    }

    @Override
    public void saveAll(List<Account> list) {
        // not implemented
    }

    public int getSize() {
        return mapDataSource.getSource().size();
    }

}
