package pt.up.hs.uhc.inkml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.InputStream;
import java.util.List;

/**
 * Test InkML reader.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class InkMLReaderTest {

    @Test
    public void testSampleEmptyPage() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("inkml/single/sample-empty.xml");

        Page page = new InkMLReader().read(is);

        Assertions.assertEquals(300D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(400D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(1, page.getStrokes().size());

        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertEquals(0L, firstStroke.getStartTime());
        Assertions.assertEquals(5000L, firstStroke.getEndTime());
        Assertions.assertEquals(0, firstStroke.getDots().size());
    }

    @Test
    public void testSampleFilledPage() throws Exception {

        InputStream is = TestUtils.openReadStreamForResource("inkml/single/sample-filled.xml");

        Page page = new InkMLReader().read(is);

        Assertions.assertEquals(300D, page.getWidth(), TestUtils.EPSILON);
        Assertions.assertEquals(400D, page.getHeight(), TestUtils.EPSILON);

        Assertions.assertEquals(2, page.getStrokes().size());

        Stroke firstStroke = page.getStrokes().get(0);
        Assertions.assertEquals(0L, firstStroke.getStartTime());
        Assertions.assertEquals(5000L, firstStroke.getEndTime());
        Assertions.assertEquals(10, firstStroke.getDots().size());

        List<Dot> firstStrokeDots = firstStroke.getDots();
        Assertions.assertEquals(new Dot(2, 4, 500L), firstStrokeDots.get(0));
        Assertions.assertEquals(new Dot(4, 7, 1000L), firstStrokeDots.get(1));
        Assertions.assertEquals(new Dot(10, 13, 1500L), firstStrokeDots.get(2));
        Assertions.assertEquals(new Dot(12, 14, 2000L), firstStrokeDots.get(3));
        Assertions.assertEquals(new Dot(15, 17, 2500L), firstStrokeDots.get(4));
        Assertions.assertEquals(new Dot(16, 18, 3000L), firstStrokeDots.get(5));
        Assertions.assertEquals(new Dot(20, 22, 3500L), firstStrokeDots.get(6));
        Assertions.assertEquals(new Dot(20, 24, 4000L), firstStrokeDots.get(7));
        Assertions.assertEquals(new Dot(20, 27, 4500L), firstStrokeDots.get(8));
        Assertions.assertEquals(new Dot(20, 28, 5000L), firstStrokeDots.get(9));

        Stroke secondStroke = page.getStrokes().get(1);
        Assertions.assertEquals(5000L, secondStroke.getStartTime());
        Assertions.assertEquals(10000L, secondStroke.getEndTime());
        Assertions.assertEquals(10, secondStroke.getDots().size());

        List<Dot> secondStrokeDots = secondStroke.getDots();
        Assertions.assertEquals(new Dot(30, 40, 5500L), secondStrokeDots.get(0));
        Assertions.assertEquals(new Dot(31, 40, 6000L), secondStrokeDots.get(1));
        Assertions.assertEquals(new Dot(32, 40, 6500L), secondStrokeDots.get(2));
        Assertions.assertEquals(new Dot(33, 40, 7000L), secondStrokeDots.get(3));
        Assertions.assertEquals(new Dot(34, 40, 7500L), secondStrokeDots.get(4));
        Assertions.assertEquals(new Dot(35, 40, 8000L), secondStrokeDots.get(5));
        Assertions.assertEquals(new Dot(36, 40, 8500L), secondStrokeDots.get(6));
        Assertions.assertEquals(new Dot(37, 40, 9000L), secondStrokeDots.get(7));
        Assertions.assertEquals(new Dot(38, 40, 9500L), secondStrokeDots.get(8));
        Assertions.assertEquals(new Dot(39, 40, 10000L), secondStrokeDots.get(9));
    }
}
