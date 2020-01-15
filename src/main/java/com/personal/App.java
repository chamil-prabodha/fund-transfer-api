package com.personal;

import com.personal.dao.AccountRepository;
import com.personal.dao.MapDataSource;
import com.personal.exception.PersistenceException;
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
    public static void main( String[] args ) throws PersistenceException {
        port(8000);
        before((req, res) -> LOGGER.info("{}", req.pathInfo()));
        AccountRepository.init(new MapDataSource<>());
        ApiRoute.init();
    }
}
