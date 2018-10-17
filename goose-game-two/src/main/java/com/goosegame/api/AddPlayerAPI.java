package com.goosegame.api;

import com.goosegame.domain.Game;
import com.goosegame.domain.Player;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;

import static com.goosegame.infrastructure.json.PlayerJson.toJson;

public class AddPlayerAPI {
    private static Logger logger = LoggerFactory.getLogger(AddPlayerAPI.class);

    private Game game;

    public AddPlayerAPI(Game game) {
        this.game = game;
    }

    public String addPlayer(Request request, Response response) {
        Player candidate = playerFrom(request);
        if (game.hasAlready(candidate)) {
            return nicknameAlreadyTakenError(response, candidate);
        }
        if (game.isFull()) {
            return tooManyPlayersError(response);
        }
        game.add(candidate);

        logger.info("{} joined the game!", candidate);
        return playerAdded(response, candidate);
    }

    private Player playerFrom(Request request) {
        JSONObject json = new JSONObject(request.body());
        String name = json.getString("name");
        String nickname = json.getString("nickname");
        return new Player(name, nickname);
    }

    private String playerAdded(Response response, Player player) {
        response.status(201);
        response.type("application/json");
        return toJson(player);
    }

    private String tooManyPlayersError(Response response) {
        response.status(400);
        response.type("application/json");
        return "{\"error\": \"too many players already: " + printNames(game.getPlayers()) + "\"}";
    }

    private String nicknameAlreadyTakenError(Response response, Player candidate) {
        response.status(400);
        response.type("application/json");
        return "{\"error\": \"nickname already taken: " + candidate.getNickname() + "\"}";
    }

    private String printNames(List<Player> players) {
        String names = "";
        for (Player p : players) {
            names += (!names.equals("") ? ", " : "") + p.getName();
        }
        return names;
    }
}
