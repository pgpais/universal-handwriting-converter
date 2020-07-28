package pt.up.hs.uhc.exceptions;

/**
 * Exception thrown when the conversion of an unsupported format is
 * requested.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class UnknownFormatException extends UniversalHandwritingConverterException {

    public UnknownFormatException() {
        super("Could detect format. Try to specify type.");
    }

    public UnknownFormatException(String message) {
        super(message);
    }
}
