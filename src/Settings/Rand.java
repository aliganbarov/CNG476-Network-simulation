package Settings;
/*
    Given probability returns true or false
 */

import java.util.Random;

public class Rand {
    private static Random rand = new Random();

    public static boolean simulate(int probability) {
        int r = rand.nextInt(probability);
        return r == 0;
    }

    public static int rv(int range) {
        return rand.nextInt(range);
    }
}
