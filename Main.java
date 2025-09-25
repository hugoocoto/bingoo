// Licensed under Hugo's GIBNC License 1.0 (2025) – must include full license and credit "Hugo Coto" if shared.

import java.util.ArrayList;

public class Main {

    private final static ArrayList<Thread> clients = new ArrayList<>();
    private static Boolean init = false;

    public static void main(String[] args) {
        if (init) {
            System.exit(0);
        }
        init = true;
        System.out.println("This should be a bingo");
        createClient();
        createClient();
        createClient();
        startGame();
        waitEndGame();
    }

    private static void createClient() {
        clients.add(new Client());
    }

    private static void startGame() {
        for (Thread c : clients) {
            c.start();
        }
    }

    private static void waitEndGame() {
        for (Thread c : clients) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
