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
    public void transferWhenToAccountIsNullTest() throws FundTransferException {
        Account fromAccount = new Account("abc", BigDecimal.valueOf(10.00));
        try {
            FundTransferServiceImpl.getInstance().transfer(fromAccount, null, BigDecimal.valueOf(100.00));
        } catch (FundTransferException e) {
            assertEquals(BigDecimal.valueOf(10.00), fromAccount.getBalance());
            throw e;
        }
    }

    @Test(expected = FundTransferException.class)
    public void transferWhenFromAccountIsNullTest() throws FundTransferException {
        Account toAccount = new Account("xyz", new BigDecimal(0));
        try {
            FundTransferServiceImpl.getInstance().transfer(null, null, BigDecimal.valueOf(100.00));
        } catch (FundTransferException e) {
            assertEquals(BigDecimal.valueOf(0), toAccount.getBalance());
            throw e;
        }
    }

    @Test(expected = FundTransferException.class)
    public void transferWhenInsufficientBalanveTest() throws FundTransferException{
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
}