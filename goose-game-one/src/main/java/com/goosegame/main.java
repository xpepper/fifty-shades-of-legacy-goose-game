package com.goosegame;

import com.goosegame.App;

import static spark.Spark.post;

public class main {

    private static App app = new App();

    public static void main(String[] args) {
        post("/players", (req, res) -> app.createPlayer(req, res));
        post("/players/:id/roll", (req, res) -> app.roll(req, res));
    }

}
