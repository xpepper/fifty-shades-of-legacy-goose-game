package com.goosegame;

import com.goosegame.api.AddPlayerAPI;
import com.goosegame.api.MovePlayerAPI;
import com.goosegame.domain.Game;
import com.goosegame.infrastructure.HttpDiceRollerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;

public class GooseGameApp {
    private static final Logger logger = LoggerFactory.getLogger(GooseGameApp.class);

    private final Game game = new Game(new HttpDiceRollerService());
    private final AddPlayerAPI addPlayerAPI = new AddPlayerAPI(game);
    private final MovePlayerAPI movePlayerAPI = new MovePlayerAPI(game);

    public void start() {
        port(8080);
        before((req, res) -> logger.info("Request: {} {} - body: {}", req.requestMethod(), req.uri(), req.body()));
        after((req, res) -> logger.info("Response {} - body: {}", res.status(), res.body()));

        post("/players", addPlayerAPI::addPlayer);
        post("/players/:id/roll", movePlayerAPI::roll);
    }

    public void stop() {
        Spark.stop();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
