package com.goosegame;

import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class App {
    public LinkedList<Player> players = new LinkedList<Player>();
    private boolean gameOver = false;
    private Player nextPlayer = null;

    public String createPlayer(Request req, Response res) {
        UUID value = UUID.randomUUID();
        JSONObject json = new Utils().fromJson(req.body());
        String name = json.getString("name");
        if (exist(name)) {
            res.status(400);
            return "{\"error\": \"name already taken: " + name + "\"}";
        } else {
            if (!moreThanFourPlayer()) {
                players.add(new Player(name, value));
                if (players.size() == 4)
                    nextPlayer = players.getFirst();

                res.status(201);
                res.type("application/json");
                return "{\"id\": \"" + value + "\", \"name\": \"" + json.getString("name") + "\"}";
            } else {
                res.status(400);
                return "{\"error\": \"too many players already: " + printNames(players) + "\"}";
            }
        }
    }

    private boolean exist(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String roll(Request req, Response res) {
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
                System.out.println("next player is " + nextPlayer);
                res.status(200);
                return movePlayer;
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
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
        int firstThrow = thowDice();
        int secondThrow = thowDice();

        int startPosition = 0, newPosition = 0;
        startPosition = currentPlayer.getPosition();
        newPosition = currentPlayer.getPosition() + firstThrow + secondThrow;
        currentPlayer.setPosition(newPosition);
        String message = String.format("%s moves from %s to %s. ", currentPlayer.getName(), cellName(startPosition), cellName(newPosition));
        if(currentPlayer.getPosition()> 63) {
            currentPlayer.setPosition(63 - (currentPlayer.getPosition() - 63));
            message += String.format("%s bounced! %s goes back to %s", currentPlayer.getName(), currentPlayer.getName(), currentPlayer.getPosition());
        }
        else if (currentPlayer.getPosition() == 6)
            currentPlayer.setPosition(currentPlayer.getPosition() + 6);

        if (currentPlayer.getPosition() == 63) {
            message += String.format("%s wins!", currentPlayer.getName());
            gameOver = true;
        }

        return "{\"roll\":" + printRoll(firstThrow, secondThrow) + ", \"position\":" + currentPlayer.getPosition() + ", \"message\": \"" + message.trim() + "\" }";
    }

    private String printRoll(int firstThrow, int secondThrow) {
        return "[" +firstThrow + ", " + secondThrow + "]";
    }

    private int thowDice() {
        return new Random().nextInt(5) + 1;
    }

    private String cellName(int startPosition) {
        return startPosition == 0? "Partenza" : String.valueOf(startPosition);
    }
}
