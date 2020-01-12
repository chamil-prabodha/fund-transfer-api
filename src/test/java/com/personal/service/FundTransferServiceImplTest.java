package com.personal.service;

import com.personal.dao.AccountRepository;
import com.personal.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferServiceImplTest {
    @InjectMocks
    private FundTransferServiceImpl fundTransferService;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void transfer() {
        Account fromAccount = new Account("abc", BigDecimal.valueOf(100.00));
        Account toAccount = new Account("xyz", new BigDecimal(0));

        fundTransferService.transfer(fromAccount, toAccount);
        assertEquals(fromAccount.getBalance(), BigDecimal.valueOf(90.00));
        assertEquals(toAccount.getBalance(), BigDecimal.valueOf(10.00));

    }
}