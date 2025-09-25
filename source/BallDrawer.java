// Licensed under Hugo's GIBNC License 1.0 (2025) – must include full license and credit "Hugo Coto" if shared.
package source;

import java.util.ArrayList;
import java.util.Random;

public class BallDrawer {

    ArrayList<Integer> balls;

    public BallDrawer(int max) {
        balls = new ArrayList<>();
        int i = 0;
        for (; i < max; i++) {
            balls.add(i);
        }
    }

    public int draw() {
        if (balls == null || balls.isEmpty()) {
            return -1;
        }

        return balls.remove((new Random()).nextInt(balls.size()));
    }
}
