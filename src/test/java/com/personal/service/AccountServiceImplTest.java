package com.personal.service;

import com.personal.dao.AccountRepository;
import com.personal.exception.PersistenceException;
import com.personal.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository repository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getAccountSuccessfulTest() {
        when(repository.findOne(anyString())).thenReturn(new Account("1", BigDecimal.ZERO));
        Account retrievedAccount = accountService.getAccount("1");
        assertNotNull(retrievedAccount);
    }

    @Test
    public void createAccount() throws PersistenceException {
        Account retrievedAccount = accountService.createAccount();
        assertNotNull(retrievedAccount);
    }
}