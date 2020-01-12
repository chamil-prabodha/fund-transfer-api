package com.personal.model;

import com.personal.exception.InsufficientBalanceException;

import java.math.BigDecimal;

public class Account {
    private String accountNumber;
    private BigDecimal balance;

    public Account(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    private synchronized void withdraw(BigDecimal amount) throws InsufficientBalanceException {
        if (balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("insufficient balance in account to transfer from account: " + accountNumber);
        }
        balance = balance.subtract(amount);
    }

    private synchronized void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
