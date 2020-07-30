package pt.up.hs.uhc.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utilities to deal with streams.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class StreamUtils {

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 1024;
        byte[] buf = new byte[bufLen];
        int readLen;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while ((readLen = inputStream.read(buf, 0, bufLen)) != -1) {
            outputStream.write(buf, 0, readLen);
        }

        return outputStream.toByteArray();
    }
}
