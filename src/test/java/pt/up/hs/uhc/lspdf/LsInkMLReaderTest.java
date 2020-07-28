package pt.up.hs.uhc.lspdf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * Test Livescribe InkML reader.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class LsInkMLReaderTest {

    @Test
    public void testCornersPage() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/corners-page.inkml");

        List<Page> pages = new LsInkMLReader().read(is);
        Assertions.assertEquals(1, pages.size());

        Page page = pages.get(0);
        Assertions.assertEquals(187.99248D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page.getHeight(), TestUtils.EPSILON);

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

    @Test
    public void test2Pages() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/2-pages.inkml");

        List<Page> pages = new LsInkMLReader().read(is);
        Assertions.assertEquals(2, pages.size());

        Page page1 = pages.get(0);
        Assertions.assertEquals(187.99248D, page1.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page1.getHeight(), TestUtils.EPSILON);

        // page only written above middle
        final double halfHPage1 = page1.getHeight() / 2;
        Assertions.assertTrue(
                page1.getStrokes().parallelStream()
                        .map(Stroke::getDots)
                        .flatMap(Collection::parallelStream)
                        .allMatch(dot -> dot.getY() < halfHPage1 && dot.getY() > 0)
        );

        Page page2 = pages.get(1);
        Assertions.assertEquals(187.99248D, page2.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page2.getHeight(), TestUtils.EPSILON);

        // page only written above middle
        final double halfHPage2 = page2.getHeight() / 2;
        Assertions.assertTrue(
                page2.getStrokes().parallelStream()
                        .map(Stroke::getDots)
                        .flatMap(Collection::parallelStream)
                        .allMatch(dot -> dot.getY() < halfHPage2 && dot.getY() > 0)
        );
    }

    @Test
    public void test2PagesNoAudio() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/2-pages-no-audio.inkml");

        List<Page> pages = new LsInkMLReader().read(is);
        Assertions.assertEquals(2, pages.size());

        Page page1 = pages.get(0);
        Assertions.assertEquals(187.99248D, page1.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page1.getHeight(), TestUtils.EPSILON);

        // page only written above middle
        final double halfHPage1 = page1.getHeight() / 2;
        Assertions.assertTrue(
                page1.getStrokes().parallelStream()
                        .map(Stroke::getDots)
                        .flatMap(Collection::parallelStream)
                        .allMatch(dot -> dot.getY() < halfHPage1 && dot.getY() > 0)
        );

        Page page2 = pages.get(1);
        Assertions.assertEquals(187.99248D, page2.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page2.getHeight(), TestUtils.EPSILON);

        // page only written above middle
        final double halfHPage2 = page2.getHeight() / 2;
        Assertions.assertTrue(
                page2.getStrokes().parallelStream()
                        .map(Stroke::getDots)
                        .flatMap(Collection::parallelStream)
                        .allMatch(dot -> dot.getY() < halfHPage2 && dot.getY() > 0)
        );
    }

    @Test
    public void test3Dots() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/3-dots.inkml");

        List<Page> pages = new LsInkMLReader().read(is);
        Assertions.assertEquals(1, pages.size());

        Page page = pages.get(0);
        Assertions.assertEquals(187.99248D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page.getHeight(), TestUtils.EPSILON);

        // we have 3 strokes (one in each corner except the bottom left corner)
        Assertions.assertEquals(3, page.getStrokes().size());

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

        // fourth corner
        Stroke fourthStroke = page.getStrokes().get(2);
        Assertions.assertTrue(fourthStroke.getDots().parallelStream()
                .allMatch(dot -> dot.getX() > halfW && dot.getX() < page.getWidth() && dot.getY() > halfH && dot.getY() < page.getHeight()));
    }
}
