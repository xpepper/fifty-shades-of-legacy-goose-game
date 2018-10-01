package org.goose.game;

public class Main {
    public static void main(String[] args) throws Exception {
        new ApplicationServer(8080, new GooseGameWrapperServlet()).start();
    }
}
