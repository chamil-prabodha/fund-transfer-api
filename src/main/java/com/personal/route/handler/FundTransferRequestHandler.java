package com.personal.route.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.exception.InsufficientBalanceException;
import com.personal.exception.ObjectValidationException;
import com.personal.exception.RequestHandlerException;
import com.personal.model.Account;
import com.personal.model.request.TransferRequest;
import com.personal.model.response.ResponseCode;
import com.personal.model.response.TransferResponse;
import com.personal.service.AccountService;
import com.personal.service.FundTransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;

public class FundTransferRequestHandler implements RequestHandler<TransferResponse> {
    private static final Logger LOGGER = LogManager.getLogger(FundTransferRequestHandler.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AccountService accountService;
    private final FundTransferService fundTransferService;

    public FundTransferRequestHandler(AccountService accountService, FundTransferService fundTransferService) {
        this.accountService = accountService;
        this.fundTransferService = fundTransferService;
    }

    @Override
    public TransferResponse handle(Request req, Response res) throws RequestHandlerException {
        try {
            TransferRequest transferRequest = OBJECT_MAPPER.readValue(req.body(), TransferRequest.class);
            transferRequest.validate();
            Account fromAccount = accountService.getAccount(transferRequest.getFromAccount());
            Account toAccount = accountService.getAccount(transferRequest.getToAccount());
            fundTransferService.transfer(fromAccount, toAccount, transferRequest.getAmount());
            res.status(200);
            res.type("application/json");
            return new TransferResponse(0, "transfer successful");
        } catch (JsonProcessingException e) {
            LOGGER.error("unable to parse request", e);
            throw new RequestHandlerException(ResponseCode.INVALID_REQUEST, "unable to parse request");
        } catch (ObjectValidationException e) {
            throw new RequestHandlerException(ResponseCode.INVALID_REQUEST, "invalid request");
        } catch (InsufficientBalanceException e) {
            throw new RequestHandlerException(ResponseCode.INSUFFICIENT_BALANCE, "insufficient balance");
        }
    }
}
