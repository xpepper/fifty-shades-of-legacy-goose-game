public class MockDice implements IDice {

    int die;

    public MockDice(int die) {
        this.die = die;
    }

    @Override
    public int roll() {
        return this.die;
    }
}
