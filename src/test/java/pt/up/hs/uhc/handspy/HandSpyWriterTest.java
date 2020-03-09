package pt.up.hs.uhc.handspy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Test HandSpy writer.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyWriterTest {

    @Test
    public void testSampleEmpty() throws Exception {

        // expected
        InputStream is = TestUtils.openReadStreamForResource("handspy/single/sample-empty.json");
        Page expectedPage = new HandSpyReader().read(is);

        // actual
        Page page = new Page()
                .width(300D)
                .height(400D)
                .addMetadata("noteType", 609)
                .addStroke(
                        new Stroke()
                                .startTime(0L)
                                .endTime(5000L)
                );

        // write actual page
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new HandSpyWriter().write(page, baos);
        // read what was written
        Page actualPage = new HandSpyReader()
                .read(new ByteArrayInputStream(baos.toByteArray()));

        // check
        Assertions.assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testSampleFilled() throws Exception {
        // expected
        InputStream is = TestUtils.openReadStreamForResource("handspy/single/sample-filled.json");
        Page expectedPage = new HandSpyReader().read(is);

        // actual
        Page page = new Page()
                .width(300D)
                .height(400D)
                .addMetadata("noteType", 609)
                .addStroke(
                        new Stroke()
                                .startTime(0L)
                                .endTime(5000L)
                                .addDot(new Dot(2, 4, 500L, 0.45677D))
                                .addDot(new Dot(4, 7, 1000L, 0.65766D))
                                .addDot(new Dot(10, 13, 1500L, 0.48777D))
                                .addDot(new Dot(12, 14, 2000L, 0.51113D))
                                .addDot(new Dot(15, 17, 2500L, 0.77355D))
                                .addDot(new Dot(16, 18, 3000L, 0.45677D))
                                .addDot(new Dot(20, 22, 3500L, 0.65330D))
                                .addDot(new Dot(20, 24, 4000L, 0.75424D))
                                .addDot(new Dot(20, 27, 4500L, 0.31233D))
                                .addDot(new Dot(20, 28, 5000L, 0.23489D))
                )
                .addStroke(
                        new Stroke()
                                .startTime(5000L)
                                .endTime(10000L)
                                .addDot(new Dot(30, 40, 5500L, 0.48777D))
                                .addDot(new Dot(31, 40, 6000L, 0.65330D))
                                .addDot(new Dot(32, 40, 6500L, 0.23489D))
                                .addDot(new Dot(33, 40, 7000L, 0.51113D))
                                .addDot(new Dot(34, 40, 7500L, 0.77355D))
                                .addDot(new Dot(35, 40, 8000L, 0.45677D))
                                .addDot(new Dot(36, 40, 8500L, 0.75424D))
                                .addDot(new Dot(37, 40, 9000L, 0.31233D))
                                .addDot(new Dot(38, 40, 9500L, 0.65766D))
                                .addDot(new Dot(39, 40, 10000L, 0.45677D))
                );
        // write actual page
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new HandSpyWriter().write(page, baos);
        // read what was written
        Page actualPage = new HandSpyReader()
                .read(new ByteArrayInputStream(baos.toByteArray()));

        // check
        Assertions.assertEquals(expectedPage, actualPage);
    }
}
