package pt.up.hs.uhc.handspy.legacy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.InputStream;
import java.util.List;

/**
 * HandSpy legacy format reader test.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyLegacyReaderTest {

    @Test
    public void testSampleEmpty() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("handspy/legacy/single/sample-empty.xml");

        Page page = new HandSpyLegacyReader().read(is);

        Assertions.assertEquals("000", page.getMetadata("id"));
        Assertions.assertEquals("A", page.getMetadata("noteType"));
        Assertions.assertEquals(1, page.getMetadata("pageNo"));
        Assertions.assertEquals(0D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(0D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(1, page.getStrokes().size());
    }

    @Test
    public void testSampleFilled() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("handspy/legacy/single/sample-filled.xml");

        Page page = new HandSpyLegacyReader().read(is);

        Assertions.assertEquals("000", page.getMetadata("id"));
        Assertions.assertEquals("A", page.getMetadata("noteType"));
        Assertions.assertEquals(1, page.getMetadata("pageNo"));
        Assertions.assertEquals(39D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(40D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(2, page.getStrokes().size());

        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertEquals(0L, firstStroke.getStartTime());
        Assertions.assertEquals(5000L, firstStroke.getEndTime());
        Assertions.assertEquals(10, firstStroke.getDots().size());

        List<Dot> firstStrokeDots = firstStroke.getDots();
        Assertions.assertEquals(new Dot(2D, 4D, 500L), firstStrokeDots.get(0));
        Assertions.assertEquals(new Dot(4D, 7D, 1000L), firstStrokeDots.get(1));
        Assertions.assertEquals(new Dot(10D, 13D, 1500L), firstStrokeDots.get(2));
        Assertions.assertEquals(new Dot(12D, 14D, 2000L), firstStrokeDots.get(3));
        Assertions.assertEquals(new Dot(15D, 17D, 2500L), firstStrokeDots.get(4));
        Assertions.assertEquals(new Dot(16D, 18D, 3000L), firstStrokeDots.get(5));
        Assertions.assertEquals(new Dot(20D, 22D, 3500L), firstStrokeDots.get(6));
        Assertions.assertEquals(new Dot(20D, 24D, 4000L), firstStrokeDots.get(7));
        Assertions.assertEquals(new Dot(20D, 27D, 4500L), firstStrokeDots.get(8));
        Assertions.assertEquals(new Dot(20D, 28D, 5000L), firstStrokeDots.get(9));

        Stroke secondStroke = page.getStrokes().get(1);
        Assertions.assertEquals(5000L, secondStroke.getStartTime());
        Assertions.assertEquals(10000L, secondStroke.getEndTime());
        Assertions.assertEquals(10, secondStroke.getDots().size());

        List<Dot> secondStrokeDots = secondStroke.getDots();
        Assertions.assertEquals(new Dot(30D, 40D, 5500L), secondStrokeDots.get(0));
        Assertions.assertEquals(new Dot(31D, 40D, 6000L), secondStrokeDots.get(1));
        Assertions.assertEquals(new Dot(32D, 40D, 6500L), secondStrokeDots.get(2));
        Assertions.assertEquals(new Dot(33D, 40D, 7000L), secondStrokeDots.get(3));
        Assertions.assertEquals(new Dot(34D, 40D, 7500L), secondStrokeDots.get(4));
        Assertions.assertEquals(new Dot(35D, 40D, 8000L), secondStrokeDots.get(5));
        Assertions.assertEquals(new Dot(36D, 40D, 8500L), secondStrokeDots.get(6));
        Assertions.assertEquals(new Dot(37D, 40D, 9000L), secondStrokeDots.get(7));
        Assertions.assertEquals(new Dot(38D, 40D, 9500L), secondStrokeDots.get(8));
        Assertions.assertEquals(new Dot(39D, 40D, 10000L), secondStrokeDots.get(9));
    }
}
