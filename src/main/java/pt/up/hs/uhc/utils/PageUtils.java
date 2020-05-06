package pt.up.hs.uhc.utils;

import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Rect;
import pt.up.hs.uhc.models.Stroke;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Utilities to deal with pages.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class PageUtils {

    public static Rect getBoundingRect(Page page) {
        Rect rect = new Rect(Double.MAX_VALUE, Double.MAX_VALUE, 0, 0);
        getDotStream(page)
                .forEach(dot -> {
                    rect
                            .x1(Math.min(rect.getX1(), dot.getX()))
                            .x2(Math.max(rect.getX2(), dot.getX()))
                            .y1(Math.min(rect.getY1(), dot.getY()))
                            .y2(Math.max(rect.getY2(), dot.getY()));
                });
        return rect;
    }

    public static void translate(Page page, double dx, double dy) {
        getDotStream(page)
                .forEach(dot -> {
                    dot.setX(dot.getX() + dx);
                    dot.setY(dot.getY() + dy);
                });
    }

    public static void rotate(Page page, double cx, double cy, double a) {
        getDotStream(page)
                .forEach(dot -> {
                    dot.setX(cx + Math.cos(a) * (dot.getX() - cx) - Math.sin(a) * (dot.getY() - cy));
                    dot.setY(cy + Math.sin(a) * (dot.getX() - cx) + Math.cos(a) * (dot.getY() - cy));
                });
    }

    public static void mirror(Page page, double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y2;
        double a = (dx * dx - dy * dy) / (dx * dx + dy*dy);
        double b = 2 * dx * dy / (dx*dx + dy*dy);
        getDotStream(page)
                .forEach(dot -> {
                    dot.setX(a * (dot.getX() - x1) + b * (dot.getY() - y1) + x1);
                    dot.setY(b * (dot.getX() - x1) - a * (dot.getY() - y1) + y1);
                });
    }

    public static void normalize(Page page, int decimalPlaces) {

        page
                .width(NumberUtils.roundAvoid(page.getWidth(), decimalPlaces))
                .height(NumberUtils.roundAvoid(page.getHeight(), decimalPlaces));

        getDotStream(page)
                .forEach(dot -> {
                    dot
                            .x(NumberUtils.roundAvoid(dot.getX(), decimalPlaces))
                            .y(NumberUtils.roundAvoid(dot.getY(), decimalPlaces))
                            .pressure(NumberUtils.roundAvoid(dot.getPressure(), decimalPlaces));
                });
    }

    public static void normalizeTime(Page page) {
        if (page.getStrokes().isEmpty()) {
            return;
        }

        long startTime = page.getStrokes().get(0).getStartTime();

        page.getStrokes().parallelStream()
                .forEach(stroke -> {
                    stroke
                            .startTime(stroke.getStartTime() - startTime)
                            .endTime(stroke.getEndTime() - startTime);
                    stroke.getDots().parallelStream()
                            .forEach(dot -> {
                                dot.timestamp(dot.getTimestamp() - startTime);
                            });
                });
    }

    private static Stream<Dot> getDotStream(Page page) {
        return page.getStrokes()
                .parallelStream()
                .map(Stroke::getDots)
                .flatMap(Collection::parallelStream);
    }
}
