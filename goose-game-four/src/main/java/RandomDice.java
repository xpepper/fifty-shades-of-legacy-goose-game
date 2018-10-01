import java.util.Random;

public class RandomDice implements IDice {

    @Override
    public int roll() {
        return new Random().nextInt((6)) + 1;
    }
}