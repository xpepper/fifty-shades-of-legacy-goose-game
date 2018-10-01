package org.goose.game;

import java.util.Random;

public class RandomDice implements IDice {
    private int dice1 = 0;
    private int dice2 = 0;

    public String roll() {
        Random random = new Random();

        dice1 = random.nextInt(5) + 1;
        dice2 = random.nextInt(5) + 1;

        return dice1 + ", " + dice2;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }
}
