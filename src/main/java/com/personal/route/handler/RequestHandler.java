package com.personal.route.handler;

import com.personal.exception.RequestHandlerException;
import spark.Request;
import spark.Response;

public interface RequestHandler<T> {
    T handle(Request req, Response res) throws RequestHandlerException;
}
