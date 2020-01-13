package com.personal.service;

import com.personal.dao.AccountRepository;
import com.personal.exception.AccountCreationException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository repository;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountServiceImpl(repository, 1000);
    }

    @Test
    public void getAccountSuccessfulTest() {
        Account account = new Account("1", BigDecimal.ZERO);
        when(repository.findOne(anyString())).thenReturn(account);
        Account retrievedAccount;
        try {
            retrievedAccount = accountService.getAccount("1");
            assertNotNull(retrievedAccount);
            assertEquals(account, retrievedAccount);
            assertEquals(account.getBalance(), retrievedAccount.getBalance());
            assertEquals(account.getAccountNumber(), retrievedAccount.getAccountNumber());
        } catch (Exception e) {
            fail("should not throw an exception");
        }
    }

    @Test
    public void createAccountSuccessfulTest() throws PersistenceException {
        Account account = new Account("1", BigDecimal.ZERO);
        when(repository.getSize()).thenReturn(0);
        when(repository.save(any(Account.class))).thenReturn(account);
        try {
            Account retrievedAccount = accountService.createAccount();
            assertNotNull(retrievedAccount);
        } catch (Exception e) {
            fail("should not throw an exception");
        }
    }

    @Test
    public void createAccountSuccessfulWhenStoreNotEmptyTest() throws PersistenceException {
        Account account = new Account("1", BigDecimal.ZERO);
        when(repository.getSize()).thenReturn(1);
        when(repository.save(any(Account.class))).thenReturn(account);
        try {
            Account retrievedAccount = accountService.createAccount();
            assertNotNull(retrievedAccount);
        } catch (Exception e) {
            fail("should not throw an exception");
        }
    }

    @Test(expected = AccountCreationException.class)
    public void createAccountThrowsExceptionTest() throws PersistenceException {
        when(repository.getSize()).thenReturn(0);
        when(repository.save(any(Account.class))).thenThrow(new AccountCreationException("unable to create account"));
        try {
            accountService.createAccount();
        } catch (AccountCreationException e) {
            throw e;
        }
    }
}