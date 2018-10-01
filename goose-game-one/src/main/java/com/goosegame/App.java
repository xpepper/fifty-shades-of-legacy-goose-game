package com.goosegame;

import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.rmi.server.UID;
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
            Random random = new Random();
            int dado = random.nextInt(5);
            try {
                Player player = players.stream().filter(it -> it.getUuid().toString().equals(req.params("id"))).collect(Collectors.toList()).get(0);
                if (!nextPlayer.equals(player)){
                    res.status(400);
                    return "{\"error\": \"Is not your turn " + player.getName() + "!\"}";
                }

                Integer oldPosition = player.getPosition();
                int newPos = oldPosition + dado + 1;
                res.status(200);
                player.setPosition(newPos);
                if (newPos == 63) {
                    gameOver = true;
                }
                if (newPos > 63) {
                    newPos = 63 - (newPos % 63);
                    //TODO SHOW BOUNCE IN MESSAGE
                    player.setPosition(newPos);
                }
                nextPlayer = players.get((players.indexOf(player) + 1 ) % players.size());
                System.out.println("next player is " + nextPlayer);
                return "{\"roll\":" + (dado + 1) + ", \"position\":" + newPos + "}";
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

}
