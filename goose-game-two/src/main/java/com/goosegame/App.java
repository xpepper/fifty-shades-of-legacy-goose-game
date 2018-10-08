package com.goosegame;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.*;
import java.util.stream.Collectors;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    private final DiceRollerService diceRollerService = new DiceRollerService();

    public LinkedList<Player> players = new LinkedList<Player>();
    private boolean gameOver = false;
    private Player nextPlayer = null;

    public String createPlayer(Request req, Response res) {
        JSONObject json = new Utils().fromJson(req.body());
        Player wannabePlayer = new Player(json);
        if (exist(wannabePlayer)) {
            res.status(400);
            return "{\"error\": \"name already taken: " + wannabePlayer.getName() + "\"}";
        } else {
            if (!moreThanFourPlayer()) {
                players.add(wannabePlayer);
                if (players.size() == 4)
                    nextPlayer = players.getFirst();

                logger.info("{} joined the game!", wannabePlayer);
                res.status(201);
                res.type("application/json");
                return "{\"id\": \"" + wannabePlayer.getUuid() + "\", \"name\": \"" + wannabePlayer.getName() + "\"}";
            } else {
                res.status(400);
                return "{\"error\": \"too many players already: " + printNames(players) + "\"}";
            }
        }
    }

    private boolean exist(Player nickname) {
        for (Player p : players) {
            if (p.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public String roll(Request req, Response res) {
        res.type("application/json");
        if (!moreThanFourPlayer()) {
            res.status(400);
            return "{\"error\": \"Game not started, waiting for more players\"}";
        }
        if (gameOver) {
            res.status(400);
            return "{\"error\": \"The game is over\"}";
        }
        if (req.params("id") != null) {
            // Does it still throw exception?
            try {
                Player player = players.stream().filter(it -> it.getUuid().toString().equals(req.params("id"))).collect(Collectors.toList()).get(0);
                if (!nextPlayer.equals(player)){
                    res.status(400);
                    return "{\"error\": \"Is not your turn " + player.getName() + "!\"}";
                }
                String movePlayer = movePlayer(player);
                nextPlayer = players.get((players.indexOf(player) + 1 ) % players.size());
                logger.info("next player is {}", nextPlayer);

                res.status(200);
                return movePlayer;
            } catch (Exception e) {
                logger.error(e.getMessage());
                res.status(400);
                return "{\"error\": \"User rolling dice was not in game!\"}";
            }
        } else {
            res.status(400);
            return "{\"error\": \"User rolling dice was not specified!\"}";
        }
    }

    private boolean moreThanFourPlayer() {
        return players.size() >= 4;
    }

    private String printNames(LinkedList<Player> players) {
        String names = "";
        for (Player p : players) {
            names += (!names.equals("") ? ", " : "") + p.getName();
        }
        return names;
    }

    private String movePlayer(Player currentPlayer) {
        JSONArray roll = roll();
        int firstThrow = roll.getJSONObject(0).getInt("value");
        int secondThrow = roll.getJSONObject(1).getInt("value");

        int startPosition = 0, newPosition = 0;
        startPosition = currentPlayer.getPosition();
        newPosition = currentPlayer.getPosition() + firstThrow + secondThrow;
        currentPlayer.setPosition(newPosition);
        String message = String.format("%s moves from %s to %s. ", currentPlayer.getName(), cellName(startPosition), cellName(newPosition));

        Optional<Player> playerInPosition = players.stream().filter(p -> p.getPosition() == currentPlayer.getPosition() && !p.getUuid().equals(currentPlayer.getUuid())).findFirst();
        if (playerInPosition.isPresent()){
            playerInPosition.get().setPosition(startPosition);
            message += String.format("On %s there was %s, who is moved back to %s. ", newPosition, playerInPosition.get().getName(), startPosition);
        }

        if (isGoose(currentPlayer.getPosition())) {
            startPosition = currentPlayer.getPosition();
            newPosition = currentPlayer.getPosition() + firstThrow + secondThrow;
            currentPlayer.setPosition(newPosition);
            message = message.substring(0, message.length()-2);
            message += String.format(", goose. %s moves from %s to %s. ", currentPlayer.getName(), cellName(startPosition), cellName(newPosition));
        }
        if(currentPlayer.getPosition()> 63) {
            currentPlayer.setPosition(63 - (currentPlayer.getPosition() - 63));
            message += String.format("%s bounced! %s goes back to %s", currentPlayer.getName(), currentPlayer.getName(), currentPlayer.getPosition());
        }
        else if (currentPlayer.getPosition() == 6) {
            currentPlayer.setPosition(currentPlayer.getPosition() + 6);
            message += String.format("%s jumps to %s", currentPlayer.getName(), currentPlayer.getPosition());
        }
        if (currentPlayer.getPosition() == 63) {
            message += String.format("%s wins!", currentPlayer.getName());
            gameOver = true;
        }

        logger.info("{} moved", currentPlayer);
        return "{\"roll\":" + printRoll(firstThrow, secondThrow) + ", \"position\":" + currentPlayer.getPosition() + ", \"message\": \"" + message.trim() + "\" }";
    }

    private JSONArray roll() {
        JSONObject jsonObject = diceRollerService.roll();
        return jsonObject.getJSONArray("dice");
    }

    private boolean isGoose(int position) {
        return Arrays.asList(5,14,23,9,18,27).contains(position);
    }

    private String printRoll(int firstThrow, int secondThrow) {
        return "[" +firstThrow + ", " + secondThrow + "]";
    }

    private String cellName(int position) {
        if (position == 0)
            return "Start";
        if (position == 6)
            return "The Bridge";
        return String.valueOf(position);
    }
}
