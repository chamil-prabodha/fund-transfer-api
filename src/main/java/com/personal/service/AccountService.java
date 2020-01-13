package com.personal.service;

import com.personal.exception.PersistenceException;
import com.personal.model.Account;

public interface AccountService {
    Account getAccount(String accountNumber);
    Account createAccount() throws PersistenceException;
}
