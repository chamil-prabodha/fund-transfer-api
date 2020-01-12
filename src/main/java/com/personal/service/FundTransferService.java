package com.personal.service;

import com.personal.model.Account;

import java.math.BigDecimal;

public interface FundTransferService {
    boolean transfer(Account from, Account to, BigDecimal amount);
}
