package com.goosegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class main {
    private static Logger logger = LoggerFactory.getLogger(main.class);

    private static App app = new App();

    public static void main(String[] args) {
        port(8080);
        before((req, res) -> {
            logger.info("URL request: {} {} - body: {}", req.requestMethod(), req.uri(), req.body());
        });

        post("/players", (req, res) -> app.createPlayer(req, res));
        post("/players/:id/roll", (req, res) -> app.roll(req, res));
    }

}
