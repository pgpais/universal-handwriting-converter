package pt.up.hs.uhc.exceptions;

/**
 * Exception thrown when the conversion of an unknown unit is
 * requested.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class UnknownUnitException extends UniversalHandwritingConverterException {

    public UnknownUnitException(String unit) {
        super(String.format("Could not handle unit %s.", unit));
    }
}
