package com.personal.dao;

import com.personal.exception.AccountCreationException;
import com.personal.exception.PersistenceException;
import com.personal.model.Account;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryTest {
    @BeforeClass
    public static void setUp() throws Exception {
        Account account = new Account("123", BigDecimal.valueOf(10.00).setScale(2, RoundingMode.UNNECESSARY));
        MapDataSource<String, Account> mapDataSource = new MapDataSource<>();
        mapDataSource.getSource().putIfAbsent(account.getAccountNumber(), account);
        AccountRepository.init(mapDataSource);
    }

    @Test
    public void getInstanceTest() {
        try {
            AccountRepository accountRepository = AccountRepository.getInstance();
            assertNotNull(accountRepository);
        } catch (Exception e) {
            fail("should not throw any exception");
        }
    }

    @Test(expected = PersistenceException.class)
    public void callInitWhenInstanceIsPresent() throws PersistenceException {
        AccountRepository.init(new MapDataSource<>());
    }

    @Test
    public void findOneSuccessfulTest() throws PersistenceException {
        Account actualAccount = AccountRepository.getInstance().findOne("123");
        assertNotNull(actualAccount);
        assertEquals("123", actualAccount.getAccountNumber());
        assertEquals(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.UNNECESSARY), actualAccount.getBalance());
    }

    @Test
    public void findOneUnableToFindAccountTest() throws PersistenceException {
        Account actualAccount = AccountRepository.getInstance().findOne("321");
        assertNull(actualAccount);
    }

    @Test
    public void findAllSuccessfulTest() throws PersistenceException {
        List<Account> accountList = AccountRepository.getInstance().findAll("123");
        assertNotNull(accountList);
        assertEquals(1, accountList.size());
        Account actualAccount = accountList.get(0);
        assertNotNull(actualAccount);
        assertEquals("123", actualAccount.getAccountNumber());
        assertEquals(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.UNNECESSARY), actualAccount.getBalance());
    }

    @Test
    public void findAllWhenUnableToFindAccountTest() throws PersistenceException {
        List<Account> accountList = AccountRepository.getInstance().findAll("321");
        assertNotNull(accountList);
        assertTrue(accountList.isEmpty());
    }

    @Test
    public void saveSuccessfulTest() throws PersistenceException {
        Account account = new Account("1245", BigDecimal.valueOf(12.00).setScale(2, RoundingMode.UNNECESSARY));
        try {
            Account actualAccount = AccountRepository.getInstance().save(account);
            assertNotNull(actualAccount);
            assertEquals("1245", actualAccount.getAccountNumber());
            assertEquals(BigDecimal.valueOf(12.00).setScale(2, RoundingMode.UNNECESSARY), actualAccount.getBalance());
        } catch (Exception e) {
            fail("should not throw any exception");
        }
    }

    @Test(expected = AccountCreationException.class)
    public void saveNullAccountThrowsExceptionTest() throws PersistenceException {
        try {
            AccountRepository.getInstance().save(null);
        } catch (AccountCreationException e) {
            assertEquals("unable to validate account", e.getMessage());
            throw e;
        }
    }

    @Test(expected = AccountCreationException.class)
    public void saveInvalidAccountNumberThrowsExceptionTest() throws PersistenceException {
        Account account = new Account("", BigDecimal.valueOf(12.00).setScale(2, RoundingMode.UNNECESSARY));
        try {
            AccountRepository.getInstance().save(account);
        } catch (AccountCreationException e) {
            assertEquals("unable to validate account", e.getMessage());
            throw e;
        }
    }

    @Test(expected = AccountCreationException.class)
    public void saveExistingAccountThrowsExceptionTest() throws PersistenceException {
        try {
            Account account = new Account("123", BigDecimal.valueOf(10.00).setScale(2, RoundingMode.UNNECESSARY));
            AccountRepository.getInstance().save(account);
        } catch (AccountCreationException e) {
            assertEquals("an account with the given account number already exists", e.getMessage());
            throw e;
        }
    }
}