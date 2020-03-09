package pt.up.hs.uhc.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.util.GregorianCalendar;

/**
 * Utilities to deal with dates.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class DateUtils {

    public static long xmlGregorianCalendarToLong(XMLGregorianCalendar calendar) {
        Instant instant = calendar.toGregorianCalendar().toInstant();
        return instant.toEpochMilli();
    }

    public static XMLGregorianCalendar longToXmlGregorianCalendar(long ms) {
        try {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(ms);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    calendar);
        }
        catch (final DatatypeConfigurationException ex) {
            return null;
        }
    }
}
