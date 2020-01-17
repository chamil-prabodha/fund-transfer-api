package com.personal;

import static org.junit.Assert.*;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppIntegrationTest {
    private static final String BASE_URL = "http://localhost:8000";
    private static CloseableHttpClient client;
    @BeforeClass
    public static void setUp() throws Exception {
        App.main(new String[]{});
        awaitInitialization();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        stop();
    }

    @Before
    public void initClient() {
        client = HttpClients.createDefault();
    }

    @After
    public void closeClient() throws IOException {
        if (client != null) {
            client.close();
        }
    }

    @Test
    public void fundTransferApiRouteSuccessfulTest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/api/transfer");

        String jsonBody = "{\"fromAccount\":100, \"toAccount\": 101, \"amount\": 10.00}";
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");
        CloseableHttpResponse response = client.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void fundTransferApiRouteInvalidRequestTest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/api/transfer");

        String jsonBody = "{\"toAccount\": 101, \"amount\": 10.00}";
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");
        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void fundTransferApiRouteInsufficientBalanceTest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL + "/api/transfer");

        String jsonBody = "{\"fromAccount\":100, \"toAccount\": 101, \"amount\": 10000.00}";
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");
        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }
}
