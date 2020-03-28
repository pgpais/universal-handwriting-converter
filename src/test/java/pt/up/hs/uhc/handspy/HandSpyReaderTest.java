package pt.up.hs.uhc.handspy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Test HandSpy reader.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyReaderTest {

    @Test
    public void testSampleEmpty() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("handspy/single/sample-empty.json");

        Page page = new HandSpyReader().read(is);

        Assertions.assertEquals(609, ((BigDecimal) page.getMetadata("noteType")).intValue());
        Assertions.assertEquals(300D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(400D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(1, page.getStrokes().size());

        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertEquals(0L, firstStroke.getStartTime());
        Assertions.assertEquals(5000L, firstStroke.getEndTime());
        Assertions.assertEquals(0, firstStroke.getDots().size());
    }

    @Test
    public void testSampleFilled() throws Exception {
        InputStream is = TestUtils.openReadStreamForResource("handspy/single/sample-filled.json");

        Page page = new HandSpyReader().read(is);

        Assertions.assertEquals(609, ((BigDecimal) page.getMetadata("noteType")).intValue());
        Assertions.assertEquals(300D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(400D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(2, page.getStrokes().size());

        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertEquals(0L, firstStroke.getStartTime());
        Assertions.assertEquals(5000L, firstStroke.getEndTime());
        Assertions.assertEquals(10, firstStroke.getDots().size());

        List<Dot> firstStrokeDots = firstStroke.getDots();
        Assertions.assertEquals(new Dot(2D, 4D, 500L, 0.45677D), firstStrokeDots.get(0));
        Assertions.assertEquals(new Dot(4D, 7D, 1000L, 0.65766D), firstStrokeDots.get(1));
        Assertions.assertEquals(new Dot(10D, 13D, 1500L, 0.48777D), firstStrokeDots.get(2));
        Assertions.assertEquals(new Dot(12D, 14D, 2000L, 0.51113D), firstStrokeDots.get(3));
        Assertions.assertEquals(new Dot(15D, 17D, 2500L, 0.77355D), firstStrokeDots.get(4));
        Assertions.assertEquals(new Dot(16D, 18D, 3000L, 0.45677D), firstStrokeDots.get(5));
        Assertions.assertEquals(new Dot(20D, 22D, 3500L, 0.65330D), firstStrokeDots.get(6));
        Assertions.assertEquals(new Dot(20D, 24D, 4000L, 0.75424D), firstStrokeDots.get(7));
        Assertions.assertEquals(new Dot(20D, 27D, 4500L, 0.31233D), firstStrokeDots.get(8));
        Assertions.assertEquals(new Dot(20D, 28D, 5000L, 0.23489D), firstStrokeDots.get(9));

        Stroke secondStroke = page.getStrokes().get(1);
        Assertions.assertEquals(5000L, secondStroke.getStartTime());
        Assertions.assertEquals(10000L, secondStroke.getEndTime());
        Assertions.assertEquals(10, secondStroke.getDots().size());

        List<Dot> secondStrokeDots = secondStroke.getDots();
        Assertions.assertEquals(new Dot(30D, 40D, 5500L, 0.48777D), secondStrokeDots.get(0));
        Assertions.assertEquals(new Dot(31D, 40D, 6000L, 0.65330D), secondStrokeDots.get(1));
        Assertions.assertEquals(new Dot(32D, 40D, 6500L, 0.23489D), secondStrokeDots.get(2));
        Assertions.assertEquals(new Dot(33D, 40D, 7000L, 0.51113D), secondStrokeDots.get(3));
        Assertions.assertEquals(new Dot(34D, 40D, 7500L, 0.77355D), secondStrokeDots.get(4));
        Assertions.assertEquals(new Dot(35D, 40D, 8000L, 0.45677D), secondStrokeDots.get(5));
        Assertions.assertEquals(new Dot(36D, 40D, 8500L, 0.75424D), secondStrokeDots.get(6));
        Assertions.assertEquals(new Dot(37D, 40D, 9000L, 0.31233D), secondStrokeDots.get(7));
        Assertions.assertEquals(new Dot(38D, 40D, 9500L, 0.65766D), secondStrokeDots.get(8));
        Assertions.assertEquals(new Dot(39D, 40D, 10000L, 0.45677D), secondStrokeDots.get(9));
    }
}
