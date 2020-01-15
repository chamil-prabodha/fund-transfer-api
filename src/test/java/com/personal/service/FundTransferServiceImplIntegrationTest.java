package com.personal.service;

import com.personal.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class FundTransferServiceImplIntegrationTest {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferServiceImplIntegrationTest.class);
    private static final int THREAD_COUNT = 1000;
    private static final double ACCOUNT_BALANCE = 1000.00;

    private final Map<Account, List<BigDecimal>> transactionLog = new Hashtable<>();
    private final Random random = new Random();

    private List<Account> accounts;
    private List<Thread> threads;

    @Before
    public void setUp() throws Exception {
        Account account1 = new Account("1", BigDecimal.valueOf(ACCOUNT_BALANCE));
        Account account2 = new Account("2", BigDecimal.valueOf(ACCOUNT_BALANCE));
        Account account3 = new Account("3", BigDecimal.valueOf(ACCOUNT_BALANCE));
        accounts = new ArrayList<>(Arrays.asList(account1, account2, account3));
        threads = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(this::runTest);
            threads.add(t);
        }
    }

    @Test
    public void transferUsingMultipleThreadsTest() {
        Long start = System.currentTimeMillis();
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                LOGGER.error(e);
                Thread.currentThread().interrupt();
            }
        });
        Long end = System.currentTimeMillis();

        transactionLog.forEach((account, list) -> {
            LOGGER.debug("account: {} transactions", account.getAccountNumber());
            final BigDecimal[] sum = {BigDecimal.ZERO};
            list.forEach(trans -> {
                LOGGER.debug(trans);
                sum[0] = sum[0].add(trans);
            });
            BigDecimal expectedBalance = BigDecimal.valueOf(1000.0).add(sum[0]);
            LOGGER.info("account: {}, expected balance: {}, actual balance:{}", account.getAccountNumber(), expectedBalance, account.getBalance());
            assertEquals(expectedBalance, account.getBalance());
        });
        LOGGER.info("execution time: {} ms", end - start);
    }

    private void runTest() {
        int choiceOrdinal = random.nextInt(2);
        int accountIndex = random.nextInt(3);
        double amount = random.nextInt((int) ACCOUNT_BALANCE) / 100.00;

        Choice choice = Choice.values()[choiceOrdinal];
        try {
            Account account = accounts.get(accountIndex);
            BigDecimal changeAmount = choice.getOperation().process(account, BigDecimal.valueOf(amount));
            addTransactionLogEntry(account, changeAmount);
        } catch (Exception e) {
            LOGGER.error(e);
            fail("should not throw any exceptions");
        }
    }

    private synchronized void addTransactionLogEntry(Account account, BigDecimal amount) {
        List<BigDecimal> transactions = transactionLog.get(account);
        if (transactions != null) {
            transactions.add(amount);
        } else {
            transactions = new ArrayList<>();
            transactions.add(amount);
            transactionLog.put(account, transactions);
        }
    }

    private enum Choice {
        WITHDRAW(((account, amount) -> {
            LOGGER.debug("withdrawing amount: {}", amount);
            account.withdraw(amount);
            return BigDecimal.ZERO.subtract(amount);
        })),
        DEPOSIT(((account, amount) -> {
            LOGGER.debug("depositing amount: {}", amount);
            account.deposit(amount);
            return BigDecimal.ZERO.add(amount);
        }));

        private Operation operation;

        Choice(Operation operation) {
            this.operation = operation;
        }

        public Operation getOperation() {
            return operation;
        }
    }

    @FunctionalInterface
    private static interface Operation {
        BigDecimal process(Account account, BigDecimal amount) throws Exception;
    }

}