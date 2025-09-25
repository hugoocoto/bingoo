// Licensed under Hugo's GIBNC License 1.0 (2025) – must include full license and credit "Hugo Coto" if shared.

import java.util.Random;

public class Client extends Thread {

    private Card card;

    public Client() {
        super();
    }

    @Override
    public void run() {
        card = new Card();
        Random r = new Random();
        card.print();
        for (int i = 0; i < 1000; i++) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
            }

            card.check(r.nextInt(75));
            card.print();

            if (card.bingo()) System.exit(0);
        }
    }

}
