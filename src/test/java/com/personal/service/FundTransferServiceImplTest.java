package com.personal.service;

import com.personal.dao.AccountRepository;
import com.personal.exception.InsufficientBalanceException;
import com.personal.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferServiceImplTest {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferServiceImplTest.class);
    @InjectMocks
    private FundTransferServiceImpl fundTransferService;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void transferSuccessfulTest() {
        Account fromAccount = new Account("abc", BigDecimal.valueOf(100.00));
        Account toAccount = new Account("xyz", new BigDecimal(0));

        boolean result = fundTransferService.transfer(fromAccount, toAccount, BigDecimal.valueOf(10.00));
        assertEquals(fromAccount.getBalance(), BigDecimal.valueOf(90.00));
        assertEquals(toAccount.getBalance(), BigDecimal.valueOf(10.00));
        assertTrue(result);
    }
}