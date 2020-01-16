package com.personal.service;

import com.personal.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class FundTransferServiceImplIntegrationTest {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferServiceImplIntegrationTest.class);
    private static final int THREAD_COUNT = 1000;
    private static final double ACCOUNT_BALANCE = 1000.00;

    private final List<TransactionLogEntry> transactionLog = new ArrayList<>();
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
            //select two random index from accounts list
            List<Integer> list= IntStream.range(0, accounts.size()).boxed().collect(Collectors.toList());
            Collections.shuffle(list);

            double amount = random.nextInt((int) ACCOUNT_BALANCE) / 100.00;
            Account fromAccount = accounts.get(list.get(0));
            Account toAccount = accounts.get(list.get(1));

            Thread t = new Thread(() -> {
                runTest(fromAccount, toAccount, BigDecimal.valueOf(amount));
            });
            threads.add(t);
        }
    }

    @Test
    public void transferUsingMultipleThreadsTest() {
        Map<String, BigDecimal> balanceMap = new HashMap<>();
        accounts.forEach(account -> balanceMap.put(account.getAccountNumber(), account.getBalance()));

        // run multi-threaded test
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

        // run sequentially to verify
        transactionLog.forEach((entry) -> {
            LOGGER.debug("from account: {} to account: {} amount: {}", entry.fromAccount.getAccountNumber(), entry.toAccount, entry.amount);

            BigDecimal fromAccountChange = balanceMap.get(entry.getFromAccount().getAccountNumber()).subtract(entry.getAmount());
            balanceMap.put(entry.getFromAccount().getAccountNumber(), fromAccountChange);

            BigDecimal toAccountChange = balanceMap.get(entry.getToAccount().getAccountNumber()).add(entry.getAmount());
            balanceMap.put(entry.getToAccount().getAccountNumber(), toAccountChange);
        });

        // compare multi-threaded and sequential result;
        accounts.forEach(account -> {
            BigDecimal expectedBalance = balanceMap.get(account.getAccountNumber());
            LOGGER.info("account: {}, expected balance: {}, actual balance:{}", account.getAccountNumber(), expectedBalance, account.getBalance());
            assertEquals(expectedBalance, account.getBalance());
        });
        LOGGER.info("execution time: {} ms", end - start);
    }

    private void runTest(Account fromAccount, Account toAccount, BigDecimal amount) {
        try {
            FundTransferServiceImpl.getInstance().transfer(fromAccount, toAccount, amount);
            addTransactionLogEntry(fromAccount, toAccount, amount);
        } catch (Exception e) {
            LOGGER.error(e);
            fail("should not throw any exceptions");
        }
    }

    private synchronized void addTransactionLogEntry(Account fromAccount, Account toAccount, BigDecimal amount) {
        transactionLog.add(new TransactionLogEntry(fromAccount, toAccount, amount));
    }

    private static class TransactionLogEntry {
        private Account fromAccount;
        private Account toAccount;
        private BigDecimal amount;

        TransactionLogEntry (Account fromAccount, Account toAccount, BigDecimal amount) {
            this.fromAccount = fromAccount;
            this.toAccount = toAccount;
            this.amount = amount;
        }

        public Account getFromAccount() {
            return fromAccount;
        }

        public Account getToAccount() {
            return toAccount;
        }

        public BigDecimal getAmount() {
            return amount;
        }
    }

}