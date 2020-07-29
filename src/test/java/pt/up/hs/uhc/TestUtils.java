package pt.up.hs.uhc;

import org.junit.jupiter.api.Assertions;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

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

    public static void checkCorners(Page page) {

        // we have 4 strokes (one in each corner)
        Assertions.assertEquals(4, page.getStrokes().size());

        double halfW = page.getWidth() / 2;
        double halfH = page.getHeight() / 2;

        // first corner
        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertTrue(firstStroke.getDots().parallelStream()
                .allMatch(dot -> dot.getX() < halfW && dot.getX() > 0 && dot.getY() < halfH && dot.getY() > 0));

        // second corner
        Stroke secondStroke = page.getStrokes().get(1);
        Assertions.assertTrue(secondStroke.getDots().parallelStream()
                .allMatch(dot -> dot.getX() > halfW && dot.getX() < page.getWidth() && dot.getY() < halfH && dot.getY() > 0));

        // third corner
        Stroke thirdStroke = page.getStrokes().get(2);
        Assertions.assertTrue(thirdStroke.getDots().parallelStream()
                .allMatch(dot -> dot.getX() < halfW && dot.getX() > 0 && dot.getY() > halfH && dot.getY() < page.getHeight()));

        // fourth corner
        Stroke fourthStroke = page.getStrokes().get(3);
        Assertions.assertTrue(fourthStroke.getDots().parallelStream()
                .allMatch(dot -> dot.getX() > halfW && dot.getX() < page.getWidth() && dot.getY() > halfH && dot.getY() < page.getHeight()));
    }
}
