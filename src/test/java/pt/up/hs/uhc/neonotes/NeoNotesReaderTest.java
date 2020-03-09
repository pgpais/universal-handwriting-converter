package pt.up.hs.uhc.neonotes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.IOException;
import java.io.InputStream;

/**
 * Test Neo Notes reader.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class NeoNotesReaderTest {

    @Test
    public void testCompletePage() throws IOException {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/single/page_full.data");

        Page page = new NeoNotesReader().read(is);
        Assertions.assertEquals("neo", page.getMetadata("id"));
        Assertions.assertEquals(609, page.getMetadata("noteType"));
        Assertions.assertEquals(1576500142905L, page.getMetadata("createdTime"));
        Assertions.assertEquals(1576500328443L, page.getMetadata("modifiedTime"));
        Assertions.assertEquals(79, page.getMetadata("pageNo"));
        Assertions.assertEquals(5, page.getMetadata("version"));
        Assertions.assertEquals(88.6779785D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(114.7084808D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(643, page.getStrokes().size());

        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertEquals(1576499042448L, firstStroke.getStartTime());
        Assertions.assertEquals(1576499043432L, firstStroke.getEndTime());
        Assertions.assertEquals(97, firstStroke.getDots().size());
        Assertions.assertEquals(1576499042448L, firstStroke.getDots().get(0).getTimestamp());
        Assertions.assertEquals(12, firstStroke.getDots().get(0).getX());
        Assertions.assertEquals(30, firstStroke.getDots().get(0).getY());

        Stroke lastStroke = page.getStrokes().get(page.getStrokes().size() - 1);
        Assertions.assertEquals(1576500049236L, lastStroke.getStartTime());
        Assertions.assertEquals(1576500049319L, lastStroke.getEndTime());
        Assertions.assertEquals(6, lastStroke.getDots().size());
        Assertions.assertEquals(1576500049236L, lastStroke.getDots().get(0).getTimestamp());
        Assertions.assertEquals(36, lastStroke.getDots().get(0).getX());
        Assertions.assertEquals(118, lastStroke.getDots().get(0).getY());
    }

    @Test
    public void testEmptyPage() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("neonotes/single/page_empty.data");

        Page page = new NeoNotesReader().read(is);
        Assertions.assertEquals("neo", page.getMetadata("id"));
        Assertions.assertEquals(609, page.getMetadata("noteType"));
        Assertions.assertEquals(1576500142563L, page.getMetadata("createdTime"));
        Assertions.assertEquals(1576500142563L, page.getMetadata("modifiedTime"));
        Assertions.assertEquals(15, page.getMetadata("pageNo"));
        Assertions.assertEquals(5, page.getMetadata("version"));
        Assertions.assertEquals(88.6779785D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(114.7084808D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(1, page.getStrokes().size());

        Stroke stroke = page.getStrokes().get(0);
        Assertions.assertEquals(1576499414218L, stroke.getStartTime());
        Assertions.assertEquals(1576499414226L, stroke.getEndTime());
        Assertions.assertEquals(3, stroke.getDots().size());
    }
}
