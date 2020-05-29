package pt.up.hs.uhc.utils;

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
}
