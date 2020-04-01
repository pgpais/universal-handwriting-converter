package pt.up.hs.uhc.exceptions;

/**
 * General exception thrown from Universal Handwriting Converter.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class UniversalHandwritingConverterException extends RuntimeException {

    public UniversalHandwritingConverterException() {
    }

    public UniversalHandwritingConverterException(String message) {
        super(message);
    }

    public UniversalHandwritingConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
