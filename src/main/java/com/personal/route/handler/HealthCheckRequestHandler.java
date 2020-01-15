package com.personal.route.handler;

import com.personal.exception.RequestHandlerException;
import spark.Request;
import spark.Response;

public class HealthCheckRequestHandler implements RequestHandler<String> {
    @Override
    public String handle(Request req, Response res) throws RequestHandlerException {
        res.status(200);
        res.type("text/plain");
        res.body("ok");
        return res.body();
    }
}
