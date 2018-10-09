package com.goosegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

import static spark.Spark.*;

public class main {
    private static Logger logger = LoggerFactory.getLogger(main.class);

    public static LinkedList<Player> players = new LinkedList<Player>();
    private static GooseGame game = new GooseGame(players);
    private static AppApi appApi = new AppApi(game);

    public static void main(String[] args) {
        port(8080);
        before((req, res) -> {
            logger.info("URL request: {} {} - body: {}", req.requestMethod(), req.uri(), req.body());
        });

        post("/players", (req, res) -> appApi.createPlayer(req, res));
        post("/players/:id/roll", (req, res) -> appApi.roll(req, res));
    }

}
