package com.personal.route.handler;

import com.personal.exception.FundTransferException;
import com.personal.exception.InsufficientBalanceException;
import com.personal.exception.RequestHandlerException;
import com.personal.model.Account;
import com.personal.model.response.ErrorCode;
import com.personal.service.AccountServiceImpl;
import com.personal.service.FundTransferServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferRequestHandlerTest {
    @InjectMocks
    private FundTransferRequestHandler fundTransferRequestHandler;

    @Mock
    private AccountServiceImpl accountService;
    @Mock
    private FundTransferServiceImpl fundTransferService;
    @Mock
    private Request request;
    @Mock
    private Response response;

    @Test
    public void handleSuccessfulTest() {
        Account fromAccount = new Account("100", BigDecimal.valueOf(100.00).setScale(2, RoundingMode.UNNECESSARY));
        Account toAccount = new Account("101", BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY));

        when(request.body()).thenReturn("{\"fromAccount\":100, \"toAccount\": 101, \"amount\": 10.00}");
        when(accountService.getAccount("100")).thenReturn(fromAccount);
        when(accountService.getAccount("101")).thenReturn(toAccount);
        try {
            doNothing().when(fundTransferService).transfer(refEq(fromAccount), refEq(toAccount), any(BigDecimal.class));
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException | FundTransferException e) {
            fail("should not throw any exception");
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleUnableToParseRequestTest() throws RequestHandlerException {
        when(request.body()).thenReturn("");
        try {
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.INVALID_REQUEST, e.getErrorCode());
            throw e;
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleInvalidRequestTest() throws RequestHandlerException {
        when(request.body()).thenReturn("{}");
        try {
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.NULL_OBJECT, e.getErrorCode());
            throw e;
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleNoFromAccountTest() throws RequestHandlerException {
        when(request.body()).thenReturn("{\"toAccount\": 101, \"amount\": 10.00}");
        try {
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.INVALID_ACC_NUM, e.getErrorCode());
            throw e;
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleNoToAccountTest() throws RequestHandlerException {
        when(request.body()).thenReturn("{\"fromAccount\":100,\"amount\": 10.00}");
        try {
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.INVALID_ACC_NUM, e.getErrorCode());
            throw e;
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleNoAccountsTest() throws RequestHandlerException {
        when(request.body()).thenReturn("{\"amount\": 10.00}");
        try {
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.INVALID_ACC_NUM, e.getErrorCode());
            throw e;
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleInvalidDecimalPlacesTest() throws RequestHandlerException {
        when(request.body()).thenReturn("{\"fromAccount\":100, \"toAccount\": 101, \"amount\": 10.001}");
        try {
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.INVALID_DECIMAL_PLACES, e.getErrorCode());
            throw e;
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleInvalidAmountTest() throws RequestHandlerException {
        when(request.body()).thenReturn("{\"fromAccount\":100, \"toAccount\": 101, \"amount\": -10.00}");
        try {
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.NEGATIVE_AMOUNT, e.getErrorCode());
            throw e;
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleInsufficientBalanceTest() throws RequestHandlerException, FundTransferException {
        Account fromAccount = new Account("100", BigDecimal.valueOf(100.00).setScale(2, RoundingMode.UNNECESSARY));
        Account toAccount = new Account("101", BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY));

        when(request.body()).thenReturn("{\"fromAccount\":100, \"toAccount\": 101, \"amount\": 10000.00}");
        when(accountService.getAccount("100")).thenReturn(fromAccount);
        when(accountService.getAccount("101")).thenReturn(toAccount);
        try {
            doThrow(new FundTransferException(ErrorCode.INSUFFICIENT_BALANCE, "insufficient balance"))
                    .when(fundTransferService).transfer(refEq(fromAccount), refEq(toAccount), any(BigDecimal.class));
            fundTransferRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.INSUFFICIENT_BALANCE, e.getErrorCode());
            throw e;
        }
    }
}