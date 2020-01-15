package com.personal.route;

import com.personal.dao.AccountRepository;
import com.personal.exception.PersistenceException;
import com.personal.exception.RequestHandlerException;
import com.personal.model.response.ResponseCode;
import com.personal.model.response.TransferResponse;
import com.personal.route.handler.FundTransferRequestHandler;
import com.personal.route.handler.GetBalanceRequestHandler;
import com.personal.route.handler.HealthCheckRequestHandler;
import com.personal.route.handler.RequestHandler;
import com.personal.service.AccountService;
import com.personal.service.AccountServiceImpl;
import com.personal.service.FundTransferService;
import com.personal.service.FundTransferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import static spark.Spark.*;


public class ApiRoute {
    private static final Logger LOGGER = LogManager.getLogger(ApiRoute.class);
    private static final String CONTENT_TYPE = "application/json";
    private static ApiRoute apiRouteInstance = null;
    private final RequestHandler<TransferResponse> fundTransferRequestHandler;
    private final RequestHandler<TransferResponse> getBalanceRequestHandler;
    private final RequestHandler<String> healthCheckRequestHandler;

    private ApiRoute() throws PersistenceException {
        FundTransferService fundTransferService = FundTransferServiceImpl.getInstance();
        AccountService accountService = new AccountServiceImpl(AccountRepository.getInstance(), 100);

        this.fundTransferRequestHandler = new FundTransferRequestHandler(accountService, fundTransferService);
        this.getBalanceRequestHandler = new GetBalanceRequestHandler(accountService);
        this.healthCheckRequestHandler = new HealthCheckRequestHandler();

        accountService.createAccount().deposit(BigDecimal.valueOf(100.00));
        accountService.createAccount();
        setupRoutes();
    }

    public static void init() throws PersistenceException{
        if (apiRouteInstance == null) {
            apiRouteInstance = new ApiRoute();
        }
    }

    private void setupRoutes() {
        path("/api", () -> {
            get("/healthcheck", healthCheckRequestHandler::handle);

            post("/transfer", fundTransferRequestHandler::handle, JsonTransformer.getInstance());
            get("/balance/:accNum", getBalanceRequestHandler::handle, JsonTransformer.getInstance());

            exception(RequestHandlerException.class, (ex, req, res) -> {
                ResponseCode responseCode = ex.getResponseCode();
                res.type(CONTENT_TYPE);
                try {
                    res.status(400);
                    res.body(JsonTransformer.getInstance().render(new TransferResponse(responseCode.getCode(), responseCode.getMessage())));
                } catch (Exception e) {
                    LOGGER.error("unexpected exception occurred", e);
                    res.status(500);
                    res.body("Internal Server Error");
                }
            });
        });
    }
}
