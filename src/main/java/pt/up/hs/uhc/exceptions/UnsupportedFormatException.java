package pt.up.hs.uhc.exceptions;

/**
 * Exception thrown when the conversion of an unsupported format is
 * requested.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class UnsupportedFormatException extends RuntimeException {

    public UnsupportedFormatException() {
        super("The requested format is not supported yet.");
    }
}
