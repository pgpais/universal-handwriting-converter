package pt.up.hs.uhc.handspy.legacy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.handspy.HandSpyWriter;
import pt.up.hs.uhc.inkml.InkMLWriter;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
        new HandSpyLegacyWriter().write(page, baos);

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
                                .addDot(new Dot(2, 4, 500L))
                                .addDot(new Dot(4, 7, 1000L))
                                .addDot(new Dot(10, 13, 1500L))
                                .addDot(new Dot(12, 14, 2000L))
                                .addDot(new Dot(15, 17, 2500L))
                                .addDot(new Dot(16, 18, 3000L))
                                .addDot(new Dot(20, 22, 3500L))
                                .addDot(new Dot(20, 24, 4000L))
                                .addDot(new Dot(20, 27, 4500L))
                                .addDot(new Dot(20, 28, 5000L))
                )
                .addStroke(
                        new Stroke()
                                .startTime(5000L)
                                .endTime(10000L)
                                .addDot(new Dot(30, 40, 5500L))
                                .addDot(new Dot(31, 40, 6000L))
                                .addDot(new Dot(32, 40, 6500L))
                                .addDot(new Dot(33, 40, 7000L))
                                .addDot(new Dot(34, 40, 7500L))
                                .addDot(new Dot(35, 40, 8000L))
                                .addDot(new Dot(36, 40, 8500L))
                                .addDot(new Dot(37, 40, 9000L))
                                .addDot(new Dot(38, 40, 9500L))
                                .addDot(new Dot(39, 40, 10000L))
                );

        // write actual page
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new HandSpyLegacyWriter().write(page, baos);

        // check
        Assertions.assertArrayEquals(expected, baos.toByteArray());
    }
}
