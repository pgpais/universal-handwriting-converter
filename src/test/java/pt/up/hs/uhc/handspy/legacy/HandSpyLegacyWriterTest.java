package pt.up.hs.uhc.handspy.legacy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * HandSpy legacy format writer test.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyLegacyWriterTest {

    @Test
    public void testSampleEmpty() throws Exception {

        // expected
        InputStream is = TestUtils.openReadStreamForResource("handspy/legacy/single/sample-empty.xml");
        byte[] expected = new byte[is.available()];
        is.read(expected);

        // actual
        Page page = new Page()
                .width(300D)
                .height(400D)
                .addMetadata("id", "000")
                .addMetadata("noteType", "A")
                .addMetadata("pageNo", 1)
                .addStroke(
                        new Stroke()
                                .startTime(0L)
                                .endTime(5000L)
                                .addDot(new Dot())
                );
        // write actual page
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new HandSpyLegacyWriter().writeSingle(page, baos);

        // check
        Assertions.assertArrayEquals(expected, baos.toByteArray());
    }

    @Test
    public void testSampleFilled() throws Exception {

        // expected
        InputStream is = TestUtils.openReadStreamForResource("handspy/legacy/single/sample-filled.xml");
        byte[] expected = new byte[is.available()];
        is.read(expected);

        // actual
        Page page = new Page()
                .width(39D)
                .height(40D)
                .addMetadata("id", "000")
                .addMetadata("noteType", "A")
                .addMetadata("pageNo", 1)
                .addStroke(
                        new Stroke()
                                .startTime(0L)
                                .endTime(5000L)
                                .addDot(new Dot(2D, 4D, 500L))
                                .addDot(new Dot(4D, 7D, 1000L))
                                .addDot(new Dot(10D, 13D, 1500L))
                                .addDot(new Dot(12D, 14D, 2000L))
                                .addDot(new Dot(15D, 17D, 2500L))
                                .addDot(new Dot(16D, 18D, 3000L))
                                .addDot(new Dot(20D, 22D, 3500L))
                                .addDot(new Dot(20D, 24D, 4000L))
                                .addDot(new Dot(20D, 27D, 4500L))
                                .addDot(new Dot(20D, 28D, 5000L))
                )
                .addStroke(
                        new Stroke()
                                .startTime(5000L)
                                .endTime(10000L)
                                .addDot(new Dot(30D, 40D, 5500L))
                                .addDot(new Dot(31D, 40D, 6000L))
                                .addDot(new Dot(32D, 40D, 6500L))
                                .addDot(new Dot(33D, 40D, 7000L))
                                .addDot(new Dot(34D, 40D, 7500L))
                                .addDot(new Dot(35D, 40D, 8000L))
                                .addDot(new Dot(36D, 40D, 8500L))
                                .addDot(new Dot(37D, 40D, 9000L))
                                .addDot(new Dot(38D, 40D, 9500L))
                                .addDot(new Dot(39D, 40D, 10000L))
                );

        // write actual page
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new HandSpyLegacyWriter().writeSingle(page, baos);

        // check
        Assertions.assertArrayEquals(expected, baos.toByteArray());
    }
}
