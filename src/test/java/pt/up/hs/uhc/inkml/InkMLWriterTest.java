package pt.up.hs.uhc.inkml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.up.hs.uhc.TestUtils;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Test InkML writer.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class InkMLWriterTest {

    @Test
    public void testSampleEmptyPage() throws Exception {

        // expected
        InputStream is = TestUtils.openReadStreamForResource("inkml/single/sample-empty.inkml");
        byte[] expected = new byte[is.available()];
        is.read(expected);

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
        new InkMLWriter().write(page, baos);

        // check
        Assertions.assertArrayEquals(expected, baos.toByteArray());
    }

    @Test
    public void testSampleFilledPage() throws Exception {

        // expected
        InputStream is = TestUtils.openReadStreamForResource("inkml/single/sample-filled.inkml");
        byte[] expected = new byte[is.available()];
        is.read(expected);

        // actual
        Page page = new Page()
                .width(300D)
                .height(400D)
                .addMetadata("noteType", 609)
                .addStroke(
                        new Stroke()
                                .startTime(0L)
                                .endTime(5000L)
                                .addDot(new Dot(2D, 4D, 500L, 0.45677D))
                                .addDot(new Dot(4D, 7D, 1000L, 0.65766D))
                                .addDot(new Dot(10D, 13D, 1500L, 0.48777D))
                                .addDot(new Dot(12D, 14D, 2000L, 0.51113D))
                                .addDot(new Dot(15D, 17D, 2500L, 0.77355D))
                                .addDot(new Dot(16D, 18D, 3000L, 0.45677D))
                                .addDot(new Dot(20D, 22D, 3500L, 0.65330D))
                                .addDot(new Dot(20D, 24D, 4000L, 0.75424D))
                                .addDot(new Dot(20D, 27D, 4500L, 0.31233D))
                                .addDot(new Dot(20D, 28D, 5000L, 0.23489D))
                )
                .addStroke(
                        new Stroke()
                                .startTime(5000L)
                                .endTime(10000L)
                                .addDot(new Dot(30D, 40D, 5500L, 0.48777D))
                                .addDot(new Dot(31D, 40D, 6000L, 0.65330D))
                                .addDot(new Dot(32D, 40D, 6500L, 0.23489D))
                                .addDot(new Dot(33D, 40D, 7000L, 0.51113D))
                                .addDot(new Dot(34D, 40D, 7500L, 0.77355D))
                                .addDot(new Dot(35D, 40D, 8000L, 0.45677D))
                                .addDot(new Dot(36D, 40D, 8500L, 0.75424D))
                                .addDot(new Dot(37D, 40D, 9000L, 0.31233D))
                                .addDot(new Dot(38D, 40D, 9500L, 0.65766D))
                                .addDot(new Dot(39D, 40D, 10000L, 0.45677D))
                );

        // write actual page
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new InkMLWriter().write(page, baos);

        // check
        Assertions.assertArrayEquals(expected, baos.toByteArray());
    }
}
