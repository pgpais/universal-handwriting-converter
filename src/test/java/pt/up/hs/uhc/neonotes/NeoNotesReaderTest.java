package pt.up.hs.uhc.neonotes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.handspy.keys.PageMetadataKeys;
import pt.up.hs.uhc.models.CaptureError;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Test Neo Notes reader.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class NeoNotesReaderTest {

    @Test
    public void testCompletePage() throws IOException {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/single/page_full.data");

        Page page = new NeoNotesReader().readSingle(is);
        Assertions.assertEquals("neo", page.getMetadata("id"));
        Assertions.assertEquals(609, page.getMetadata("noteType"));
        Assertions.assertEquals(1576500142905L, page.getMetadata("createdTime"));
        Assertions.assertEquals(1576500328443L, page.getMetadata("modifiedTime"));
        Assertions.assertEquals(79, page.getMetadata("pageNo"));
        Assertions.assertEquals(5, page.getMetadata("version"));
        Assertions.assertEquals(236.000D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(323.000D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(643, page.getStrokes().size());

        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertEquals(1576499042448L, firstStroke.getStartTime());
        Assertions.assertEquals(1576499043432L, firstStroke.getEndTime());
        Assertions.assertEquals(97, firstStroke.getDots().size());
        Assertions.assertEquals(1576499042448L, firstStroke.getDots().get(0).getTimestamp());
        Assertions.assertEquals(28.8984257D, firstStroke.getDots().get(0).getX(), TestUtils.EPSILON);
        Assertions.assertEquals(72.8031712D, firstStroke.getDots().get(0).getY(), TestUtils.EPSILON);

        Stroke lastStroke = page.getStrokes().get(page.getStrokes().size() - 1);
        Assertions.assertEquals(1576500049236L, lastStroke.getStartTime());
        Assertions.assertEquals(1576500049319L, lastStroke.getEndTime());
        Assertions.assertEquals(6, lastStroke.getDots().size());
        Assertions.assertEquals(1576500049236L, lastStroke.getDots().get(0).getTimestamp());
        Assertions.assertEquals(86.1737297D, lastStroke.getDots().get(0).getX(), TestUtils.EPSILON);
        Assertions.assertEquals(280.9002883D, lastStroke.getDots().get(0).getY(), TestUtils.EPSILON);

        Assertions.assertNull(page.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));
    }

    @Test
    public void testEmptyPage() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/single/page_empty.data");

        Page page = new NeoNotesReader().readSingle(is);
        Assertions.assertEquals("neo", page.getMetadata("id"));
        Assertions.assertEquals(609, page.getMetadata("noteType"));
        Assertions.assertEquals(1576500142563L, page.getMetadata("createdTime"));
        Assertions.assertEquals(1576500142563L, page.getMetadata("modifiedTime"));
        Assertions.assertEquals(15, page.getMetadata("pageNo"));
        Assertions.assertEquals(5, page.getMetadata("version"));
        Assertions.assertEquals(236.000D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(323.000D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(1, page.getStrokes().size());

        Stroke stroke = page.getStrokes().get(0);
        Assertions.assertEquals(1576499414218L, stroke.getStartTime());
        Assertions.assertEquals(1576499414226L, stroke.getEndTime());
        Assertions.assertEquals(3, stroke.getDots().size());

        Assertions.assertNull(page.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));
    }

    @Test
    public void testReadArchive() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/archive/archive.neonotes.zip");

        List<Page> pages = new NeoNotesReader().readArchive(new ZipInputStream(new BufferedInputStream(is)));

        Assertions.assertEquals(2, pages.size());

        Page firstPage = pages.get(0);
        Assertions.assertEquals("neo", firstPage.getMetadata("id"));
        Assertions.assertEquals(609, firstPage.getMetadata("noteType"));
        Assertions.assertEquals(1576500142563L, firstPage.getMetadata("createdTime"));
        Assertions.assertEquals(1576500142563L, firstPage.getMetadata("modifiedTime"));
        Assertions.assertEquals(15, firstPage.getMetadata("pageNo"));
        Assertions.assertEquals(5, firstPage.getMetadata("version"));
        Assertions.assertEquals(236.000D, firstPage.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(323.000D, firstPage.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(1, firstPage.getStrokes().size());

        Stroke stroke = firstPage.getStrokes().get(0);
        Assertions.assertEquals(1576499414218L, stroke.getStartTime());
        Assertions.assertEquals(1576499414226L, stroke.getEndTime());
        Assertions.assertEquals(3, stroke.getDots().size());

        Assertions.assertNull(firstPage.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));

        Page secondPage = pages.get(1);
        Assertions.assertEquals("neo", secondPage.getMetadata("id"));
        Assertions.assertEquals(609, secondPage.getMetadata("noteType"));
        Assertions.assertEquals(1576500142905L, secondPage.getMetadata("createdTime"));
        Assertions.assertEquals(1576500328443L, secondPage.getMetadata("modifiedTime"));
        Assertions.assertEquals(79, secondPage.getMetadata("pageNo"));
        Assertions.assertEquals(5, secondPage.getMetadata("version"));
        Assertions.assertEquals(236.000D, secondPage.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(323.000D, secondPage.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(643, secondPage.getStrokes().size());

        Stroke firstStroke = secondPage.getStrokes().get(0);
        Assertions.assertEquals(1576499042448L, firstStroke.getStartTime());
        Assertions.assertEquals(1576499043432L, firstStroke.getEndTime());
        Assertions.assertEquals(97, firstStroke.getDots().size());
        Assertions.assertEquals(1576499042448L, firstStroke.getDots().get(0).getTimestamp());
        Assertions.assertEquals(28.8984257D, firstStroke.getDots().get(0).getX(), TestUtils.EPSILON);
        Assertions.assertEquals(72.8031712D, firstStroke.getDots().get(0).getY(), TestUtils.EPSILON);

        Stroke lastStroke = secondPage.getStrokes().get(secondPage.getStrokes().size() - 1);
        Assertions.assertEquals(1576500049236L, lastStroke.getStartTime());
        Assertions.assertEquals(1576500049319L, lastStroke.getEndTime());
        Assertions.assertEquals(6, lastStroke.getDots().size());
        Assertions.assertEquals(1576500049236L, lastStroke.getDots().get(0).getTimestamp());
        Assertions.assertEquals(86.1737297D, lastStroke.getDots().get(0).getX(), TestUtils.EPSILON);
        Assertions.assertEquals(280.9002883D, lastStroke.getDots().get(0).getY(), TestUtils.EPSILON);

        Assertions.assertNull(secondPage.getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey()));
    }

    @Test
    public void testReadFatArchive() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/archive/fat-archive.neonotes.zip");

        List<Page> pages = new NeoNotesReader().readArchive(new ZipInputStream(new BufferedInputStream(is)));

        Assertions.assertEquals(70, pages.size());
    }

    @Test
    public void testReadFatArchive2() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/archive/fat-archive-2.neonotes.zip");

        List<Page> pages = new NeoNotesReader().readArchive(new ZipInputStream(new BufferedInputStream(is)));

        Assertions.assertEquals(61, pages.size());
    }

    @Test
    public void testReadSoftLines() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/archive/soft-lines.neonotes.zip");

        List<Page> pages = new NeoNotesReader().readArchive(new ZipInputStream(new BufferedInputStream(is)));

        Assertions.assertEquals(4, pages.size());
    }

    @Test
    public void testReadMariona1() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/archive/mariona-1.neonotes.zip");

        List<Page> pages = new NeoNotesReader()
                .readArchive(new ZipInputStream(new BufferedInputStream(is)));

        Assertions.assertEquals(12, pages.size());

        Assertions.assertEquals(
                CaptureError.OUT_OF_BOUNDS,
                pages.get(0).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.OUT_OF_BOUNDS,
                pages.get(0).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.MARGIN_NOT_RESPECTED,
                pages.get(2).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.MARGIN_NOT_RESPECTED,
                pages.get(3).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.STROKE_OVERLAP,
                pages.get(4).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.OUT_OF_BOUNDS,
                pages.get(5).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.STROKE_OVERLAP,
                pages.get(6).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.MARGIN_NOT_RESPECTED,
                pages.get(7).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.MARGIN_NOT_RESPECTED,
                pages.get(8).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.STROKE_OVERLAP,
                pages.get(9).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertNull(
                pages.get(10).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );

        Assertions.assertEquals(
                CaptureError.OUT_OF_BOUNDS,
                pages.get(11).getMetadata().get(PageMetadataKeys.CAPTURE_ERROR.getKey())
        );
    }
}
