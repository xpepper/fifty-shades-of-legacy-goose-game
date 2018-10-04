package com.goosegame;

import com.goosegame.App;

import static spark.Spark.port;
import static spark.Spark.post;

public class main {

    private static App app = new App();

    public static void main(String[] args) {
        port(8080);
        post("/players", (req, res) -> app.createPlayer(req, res));
        post("/players/:id/roll", (req, res) -> app.roll(req, res));
    }

}
