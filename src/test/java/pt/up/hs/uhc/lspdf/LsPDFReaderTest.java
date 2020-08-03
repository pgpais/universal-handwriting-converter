package pt.up.hs.uhc.lspdf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.handspy.keys.PageMetadataKeys;
import pt.up.hs.uhc.models.CaptureError;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public class LsPDFReaderTest {

    @Test
    public void testCornersPage() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/corners-page.pdf");

        List<Page> pages = new LsPDFReader().read(is);
        Assertions.assertEquals(1, pages.size());

        Page page = pages.get(0);
        Assertions.assertEquals(187.99248D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page.getHeight(), TestUtils.EPSILON);

        TestUtils.checkCorners(page);

        Assertions.assertNull(page.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));
    }

    @Test
    public void test2Pages() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/2-pages.pdf");

        List<Page> pages = new LsPDFReader().read(is);
        Assertions.assertEquals(2, pages.size());

        Page page1 = pages.get(0);
        Assertions.assertEquals(187.99248D, page1.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(237.80311D, page1.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(CaptureError.STROKE_OVERLAP, page1.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));

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

        Assertions.assertEquals(CaptureError.STROKE_OVERLAP, page2.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));
    }

    @Test
    public void test2PagesNoAudio() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/2-pages-no-audio.pdf");

        List<Page> pages = new LsPDFReader().read(is);
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

        Assertions.assertEquals(CaptureError.STROKE_OVERLAP, page1.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));

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

        Assertions.assertEquals(CaptureError.STROKE_OVERLAP, page2.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));
    }

    @Test
    public void test3Dots() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("lspdf/3-dots.pdf");

        List<Page> pages = new LsPDFReader().read(is);
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

        Assertions.assertNull(page.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));
    }
}
