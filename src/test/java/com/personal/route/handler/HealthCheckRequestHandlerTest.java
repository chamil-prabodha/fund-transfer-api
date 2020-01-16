package com.personal.route.handler;

import com.personal.exception.RequestHandlerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HealthCheckRequestHandlerTest {

    @InjectMocks
    private HealthCheckRequestHandler healthCheckRequestHandler;

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Test
    public void handleSuccessTest() {
        doNothing().when(response).status(anyInt());
        doNothing().when(response).type(anyString());
        doNothing().when(response).body(anyString());
        when(response.body()).thenReturn("ok");
        try {
            String resp = healthCheckRequestHandler.handle(request, response);
            assertEquals("ok", resp);
        } catch (RequestHandlerException e) {
           fail("should not throw any exception");
        }
    }
}