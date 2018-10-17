package com.goosegame.domain;

public class MoveResult {
    private final Roll roll;
    private String message;
    private int position;

    public MoveResult(Roll roll, String message, int position) {
        this.roll = roll;
        this.message = message;
        this.position = position;
    }

    public Roll getRoll() {
        return roll;
    }

    public String getMessage() {
        return message.trim();
    }

    public Integer getNewPosition() {
        return position;
    }
}
