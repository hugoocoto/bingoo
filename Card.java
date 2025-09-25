// Licensed under Hugo's GIBNC License 1.0 (2025) – must include full license and credit "Hugo Coto" if shared.

import java.util.ArrayList;
import java.util.Random;

public class Card {

    private static final int rows = 3;
    private static final int cols = 5;
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    private static final String STRIKE = "\033[9m";
    private static final Object lock = new Object();

    class NState implements Comparable<NState> {

        public Integer num;
        public Boolean state;

        @Override
        public int compareTo(NState st) {
            return num.compareTo(st.num);
        }
    }

    ArrayList<NState> card;

    public Card() {
        card = new ArrayList<>();
        create();
    }

    private void create() {
        Random r = new Random();
        int i = 0, j;
        for (; i < cols; i++) {
            for (j = 0; j < rows; j++) {
                NState c = new NState();
                do {
                    c.num = r.nextInt(i * 15, (i + 1) * 15) + 1;
                    c.state = false;
                } while (card.stream().anyMatch(n -> n.num.equals(c.num)));
                card.add(c);
            }
        }
        card.sort(null);
    }

    public void print() {
        int i = 0, j;

        synchronized (lock) {
            System.out.println("--- BINGO ----");
            for (; i < rows; i++) {
                for (j = 0; j < cols; j++) {
                    System.out.print("%s%02d%s ".formatted(
                            card.get(i + j * rows).state ? STRIKE : BOLD,
                            card.get(i + j * rows).num,
                            RESET
                    ));
                }
                System.out.println("");
            }
            System.out.println("--------------");
        }
    }

    public void check(Integer num) {
        for (NState o : card) {
            if (o.num.equals(num)) {
                o.state = true;
                break;
            }
        }
    }

    public Boolean bingo() {
        for (NState o : card) {
            if (!o.state) {
                return false;
            }
        }
        return true;
    }

}
