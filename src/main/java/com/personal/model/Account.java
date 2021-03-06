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

    public void withdraw(BigDecimal amount) throws InsufficientBalanceException {
        synchronized (this) {
            BigDecimal resultingAmount = balance.subtract(amount);
            if (resultingAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException("insufficient balance in account to transfer from account: " + accountNumber, null);
            }
            balance = resultingAmount;
        }
    }

    public void deposit(BigDecimal amount) {
        synchronized (this) {
            balance = balance.add(amount);
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        synchronized (this) {
            return balance;
        }
    }
}
