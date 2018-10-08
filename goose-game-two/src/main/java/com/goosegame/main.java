package com.goosegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class main {
    private static Logger logger = LoggerFactory.getLogger(main.class);

    private static App app = new App();

    public static void main(String[] args) {
        port(8080);
        before((req, res) -> logger.info("Request: {} {} - body: {}", req.requestMethod(), req.uri(), req.body()));
        after((req, res) -> logger.info("Response {} - body: {}", res.status(), res.body()));

        post("/players", (req, res) -> app.createPlayer(req, res));
        post("/players/:id/roll", (req, res) -> app.roll(req, res));
    }

}
