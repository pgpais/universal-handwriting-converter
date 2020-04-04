package pt.up.hs.uhc.svg;

import pt.up.hs.uhc.base.PageWriter;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Writer for SVG files.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class SvgWriter implements PageWriter {

    @Override
    public void write(Page page, OutputStream os) throws Exception {

        StringBuilder sb = new StringBuilder()
                .append("<svg height=\"")
                .append(page.getHeight())
                .append("\" width=\"")
                .append(page.getWidth())
                .append("\" stroke=\"#000\">")
                .append(groupFromPage(page))
                .append("</svg>");

        os.write(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    private String groupFromPage(Page page) {
        StringBuilder sb = new StringBuilder();
        sb.append("<g>");
        page.getStrokes().parallelStream()
                .forEachOrdered(stroke -> sb.append(pathFromStroke(stroke)));
        sb.append("</g>");
        return sb.toString();
    }

    private String pathFromStroke(Stroke stroke) {

        StringBuilder sb = new StringBuilder();
        sb.append("<path d=\"");
        sb.append('M');
        AtomicReference<Double> sumPressure = new AtomicReference<>((double) 0);
        stroke.getDots().parallelStream()
                .forEachOrdered(dot -> {
                    sb
                            .append(dot.getX())
                            .append(' ')
                            .append(dot.getY())
                            .append(',');
                    sumPressure.updateAndGet(v -> v + dot.getPressure());
                });
        sb
                .deleteCharAt(sb.length() - 1)
                .append('\"')
                .append(' ')
                .append("stroke-width=\"")
                .append(sumPressure.get() <= 0 ? 0.5 : sumPressure.get() / stroke.getDots().size())
                .append("\"")
                .append(' ')
                .append("shape-rendering=\"geometricPrecision\"")
                .append(' ')
                .append("stroke-linejoin=\"round\"")
                .append(' ')
                .append("stroke-linecap=\"round\"")
                .append(' ')
                .append("fill=\"none\"")
                .append("/>");

        return sb.toString();
    }
}
