package com.personal.route.handler;

import com.personal.exception.RequestHandlerException;
import com.personal.model.Account;
import com.personal.model.response.ErrorCode;
import com.personal.model.response.TransferResponse;
import com.personal.service.AccountServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetBalanceRequestHandlerTest {

    @InjectMocks
    private GetBalanceRequestHandler getBalanceRequestHandler;

    @Mock
    private AccountServiceImpl accountService;
    @Mock
    private Request request;
    @Mock
    private Response response;

    @Test
    public void handleSuccessfulTest() {
        String accountNumber = "123";
        Account account = new Account(accountNumber, BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY));
        when(request.params(anyString())).thenReturn(accountNumber);
        when(accountService.getAccount(accountNumber)).thenReturn(account);
        doNothing().when(response).type(anyString());
        doNothing().when(response).status(anyInt());
        try {
            TransferResponse transferResponse = getBalanceRequestHandler.handle(request, response);
            assertNotNull(transferResponse);
            assertEquals(0, transferResponse.getCode());
            assertEquals("0.00", transferResponse.getMessage());
        } catch (RequestHandlerException e) {
            fail("should not throw any exception");
        }
    }

    @Test(expected = RequestHandlerException.class)
    public void handleWhenAccountNotFoundTest() throws RequestHandlerException {
        String accountNumber = "123";
        when(request.params(anyString())).thenReturn(accountNumber);
        when(accountService.getAccount(accountNumber)).thenReturn(null);
        try {
            TransferResponse transferResponse = getBalanceRequestHandler.handle(request, response);
        } catch (RequestHandlerException e) {
            assertEquals(ErrorCode.ACC_NOT_FOUND, e.getErrorCode());
            throw e;
        }
    }
}