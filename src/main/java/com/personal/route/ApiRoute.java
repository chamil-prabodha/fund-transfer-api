package com.personal.route;

import static spark.Spark.*;


public class ApiRoute {
    private static ApiRoute apiRouteInstance = null;

    private ApiRoute() {
        setupRoutes();
    }

    public static void init() {
        if (apiRouteInstance == null) {
            apiRouteInstance = new ApiRoute();
        }
    }

    private void setupRoutes() {
        path("/api", () -> get("/healthcheck", (req, res) -> {
            res.status(200);
            res.type("application/json");
            res.body("ok");
            return res.body();
        }));
    }
}
