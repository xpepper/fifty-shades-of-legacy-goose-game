package com.goosegame.api;

import com.goosegame.domain.Game;
import com.goosegame.domain.IllegalTurnException;
import com.goosegame.domain.MoveResult;
import com.goosegame.domain.Player;
import spark.Request;
import spark.Response;

import java.util.Optional;

public class MovePlayerAPI {
    private Game game;

    public MovePlayerAPI(Game game) {
        this.game = game;
    }

    public String roll(Request request, Response response) {
        if (isNotValid(request)) {
            return invalidRequestError(response);
        }
        if (game.isWaitingForMorePlayers()) {
            return gameNotStartedError(response);
        }
        if (game.isOver()) {
            return gameOverError(response);
        }

        Optional<Player> player = game.playerFrom(request.params("id"));
        if (!player.isPresent()) {
            return playerNotFoundError(response);
        }

        Player currentPlayer = player.get();
        try {
            MoveResult moveResult = game.move(currentPlayer);
            return moveResultResponse(moveResult, response);
        } catch (IllegalTurnException e) {
            return illegalTurnError(response, e.player());
        }
    }

    private String illegalTurnError(Response response, Player player) {
        response.type("application/json");
        response.status(400);
        return "{\"error\": \"Is not your turn " + player + "!\"}";
    }

    private String moveResultResponse(MoveResult moveResult, Response response) {
        response.type("application/json");
        response.status(200);
        return "{\"roll\":" + moveResult.getRoll() + ", \"position\":" + moveResult.getNewPosition() + ", \"message\": \"" + moveResult.getMessage() + "\" }";
    }

    private String playerNotFoundError(Response response) {
        response.type("application/json");
        response.status(400);
        return "{\"error\": \"User rolling dice was not in game!\"}";
    }

    private String invalidRequestError(Response response) {
        response.type("application/json");
        response.status(400);
        return "{\"error\": \"User rolling dice was not specified!\"}";
    }

    private boolean isNotValid(Request request) {
        return request.params("id") == null;
    }

    private String gameOverError(Response response) {
        response.status(400);
        response.type("application/json");
        return "{\"error\": \"The game is over\"}";
    }

    private String gameNotStartedError(Response response) {
        response.status(400);
        response.type("application/json");
        return "{\"error\": \"Game not started, waiting for more players\"}";
    }
}
