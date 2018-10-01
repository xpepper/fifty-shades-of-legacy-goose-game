public class Main {
    public static void main(String[] args) throws Exception {
        new ApplicationServer(8080, new GooseGameApplication(new GooseGame(new RandomDice(), new RandomDice()))).start();
    }
}
