package com.goosegame;

import java.util.LinkedList;

public class GooseGame {
    public static final int MAXIMUM_NUMBER_OF_PLAYERS = 4;
    private LinkedList<Player> players;

    public GooseGame(LinkedList<Player> players) {
        this.players = players;
    }


    public void addPlayer(Player player) throws GameFullException, NickNameAlreadyTakenException {
        if (players.size() == MAXIMUM_NUMBER_OF_PLAYERS) {
            throw new GameFullException();
        }
        if (existPlayerWithNickname(player.getNickname())) {
            throw new NickNameAlreadyTakenException();
        }
        players.add(player);
    }

    public Player firstPlayer() {
        return players.getFirst();
    }

    public LinkedList<Player> players() {
        return players;
    }

    private boolean existPlayerWithNickname(String nickname) {
        return players.stream().anyMatch(player -> player.hasNickname(nickname));
    }
}
