package pt.up.hs.uhc;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

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

    public static File openFileForResource(String path) {
        return new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource(path)).getFile());
    }
}
