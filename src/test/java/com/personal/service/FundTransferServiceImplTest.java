package com.personal.service;

import com.personal.exception.FundTransferException;
import com.personal.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferServiceImplTest {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferServiceImplTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void transferSuccessfulTest() {
        Account fromAccount = new Account("abc", BigDecimal.valueOf(100.00));
        Account toAccount = new Account("xyz", new BigDecimal(0));

        try {
            FundTransferServiceImpl.getInstance().transfer(fromAccount, toAccount, BigDecimal.valueOf(10.00));
            assertEquals(BigDecimal.valueOf(90.00), fromAccount.getBalance());
            assertEquals(BigDecimal.valueOf(10.00), toAccount.getBalance());
        } catch (Exception e) {
            fail("should not throw an exception");
        }
    }

    @Test(expected = FundTransferException.class)
    public void transferThrowsInsufficientBalanceExceptionTest() throws FundTransferException{
        Account fromAccount = new Account("abc", BigDecimal.valueOf(10.00));
        Account toAccount = new Account("xyz", new BigDecimal(0));
        try {
            FundTransferServiceImpl.getInstance().transfer(fromAccount, toAccount, BigDecimal.valueOf(100.00));
        } catch (FundTransferException e) {
            assertEquals(BigDecimal.valueOf(10.00), fromAccount.getBalance());
            assertEquals(BigDecimal.valueOf(0), toAccount.getBalance());
            throw e;
        }
    }

//    @Test
//    public void transferTestMultiThreaded() throws Exception {
//        Account account1 = new Account("1", BigDecimal.valueOf(1000.00));
//        Account account2 = new Account("2", BigDecimal.valueOf(1000.00));
//        Account account3 = new Account("3", BigDecimal.valueOf(1000.00));
//
//        Account[] accounts = new Account[3];
//        List<Thread> threads = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 1000; i++) {
//            Thread t = new Thread(() -> {
//                int choice = random.nextInt(2);
//                double amount = random.nextInt(1000) / 100.00;
//                switch (choice) {
//                    case 0:
//                        try {
//                            LOGGER.info("withdrawing amount: {}", amount);
//                            account1.withdraw(BigDecimal.valueOf(amount));
//                        } catch (InsufficientBalanceException e) {
//                            LOGGER.error(e);
//                        }
//                        break;
//                    case 1:
//                        LOGGER.info("depositing amount: {}", amount);
//                        account1.deposit(BigDecimal.valueOf(amount));
//                        break;
//                    default:
//                        LOGGER.error("invalid option: {}", choice);
//                }
//            });
//            threads.add(t);
//        }
//
//        Long start = System.currentTimeMillis();
//        threads.forEach(Thread::start);
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                LOGGER.error(e);
//                Thread.currentThread().interrupt();
//            }
//        });
//        Long end = System.currentTimeMillis();
//        LOGGER.info("balance: {}, time: {} ms", account1.getBalance(), end - start);
//    }
}