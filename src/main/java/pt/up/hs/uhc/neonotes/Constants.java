package pt.up.hs.uhc.neonotes;

import static pt.up.hs.uhc.base.Constants.INCHES_TO_MM_FACTOR;

/**
 * Constants used in NeoNotes conversion.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Constants {
    public static final double NCODE_COORDINATES_TO_INCHES_FACTOR = 56D / 600D;
    public static final double NCODE_COORDINATES_TO_MM_FACTOR =
            NCODE_COORDINATES_TO_INCHES_FACTOR * INCHES_TO_MM_FACTOR;
}
