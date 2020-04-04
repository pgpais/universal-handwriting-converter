package pt.up.hs.uhc.utils;

/**
 * Utilities to deal with numbers.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class NumberUtils {

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
