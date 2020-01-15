package com.personal.route.handler;

import com.personal.exception.ObjectValidationException;
import com.personal.exception.RequestHandlerException;
import com.personal.model.Account;
import com.personal.model.response.ErrorCode;
import com.personal.model.response.TransferResponse;
import com.personal.service.AccountService;
import com.personal.validator.AccountValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;

public class GetBalanceRequestHandler implements RequestHandler<TransferResponse> {
    private static final Logger LOGGER = LogManager.getLogger(GetBalanceRequestHandler.class);
    private final AccountService accountService;

    public GetBalanceRequestHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public TransferResponse handle(Request req, Response res) throws RequestHandlerException {
        String accountNumber = req.params(":accNum");
        try {
            Account account = accountService.getAccount(accountNumber);
            new AccountValidator().checkIfNull(account);
            res.type("application/json");
            res.status(200);
            return new TransferResponse(0, account.getBalance().toPlainString());
        } catch (ObjectValidationException e) {
            LOGGER.error("unable to locate account: {}", accountNumber);
            throw new RequestHandlerException(ErrorCode.ACC_NOT_FOUND, "unable to locate account");
        }

    }
}
