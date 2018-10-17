package com.goosegame.domain;

public class IllegalTurnException extends Exception {
    private Player player;

    public IllegalTurnException(Player player) {
        this.player = player;
    }

    public Player player() {
        return player;
    }
}
