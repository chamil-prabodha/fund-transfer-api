package com.personal.service;

import com.personal.exception.InsufficientBalanceException;
import com.personal.model.Account;

import java.math.BigDecimal;

public interface FundTransferService {
    void transfer(Account from, Account to, BigDecimal amount) throws InsufficientBalanceException;
}
