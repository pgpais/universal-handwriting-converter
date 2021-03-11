package pt.up.hs.uhc.utils;

import static pt.up.hs.uhc.base.Constants.EPSILON;

/**
 * Utilities to deal with numbers.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class NumberUtils {

    public static Double roundAvoid(Double value, int places) {
        if (value == null) {
            return null;
        }
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static int compare(double d1, double d2) {
        if (d1 - EPSILON <= d2 && d1 + EPSILON >= d2) {
            return 0;
        }
        if (d1 < d2) {
            return -1;
        }
        return 1;
    }
}
