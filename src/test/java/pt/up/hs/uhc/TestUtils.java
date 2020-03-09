package pt.up.hs.uhc;

import java.io.InputStream;

/**
 * Utilities for tests.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class TestUtils {
    public static final double EPSILON = 1e-5;

    public static InputStream openReadStreamForResource(String path) {
        return Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(path);
    }
}
