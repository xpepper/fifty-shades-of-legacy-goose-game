package com.goosegame.domain;

public class Roll {
    private final int firstThrow;
    private final int secondThrow;

    public Roll(int firstThrow, int secondThrow) {
        this.firstThrow = firstThrow;
        this.secondThrow = secondThrow;
    }

    public int result() {
        return firstThrow + secondThrow;
    }

    @Override
    public String toString() {
        return "[" + firstThrow + ", " + secondThrow + "]";
    }
}
