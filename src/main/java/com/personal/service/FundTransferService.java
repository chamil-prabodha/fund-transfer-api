package com.personal.service;

import com.personal.model.Account;

public interface FundTransferService {
    boolean transfer(Account from, Account to);
}
