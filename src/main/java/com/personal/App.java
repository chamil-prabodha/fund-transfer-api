package com.personal;

import com.personal.route.ApiRoute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger LOGGER = LogManager.getLogger(App.class);
    public static void main( String[] args ) {
        port(8000);
        before((req, res) -> LOGGER.info("{}", req.pathInfo()));
        ApiRoute.init();
    }
}
