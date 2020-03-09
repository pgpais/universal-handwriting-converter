package pt.up.hs.uhc.handspy;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import pt.up.hs.uhc.PageReader;
import pt.up.hs.uhc.handspy.keys.DotKeys;
import pt.up.hs.uhc.handspy.keys.PageKeys;
import pt.up.hs.uhc.handspy.keys.StrokeKeys;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.DotType;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reader for HandSpy JSON pages.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyReader implements PageReader {

    @Override
    public Page read(InputStream is) throws Exception {

        InputStreamReader isr = new InputStreamReader(is);

        JsonObject parser = (JsonObject) Jsoner.deserialize(isr);

        Page page = new Page()
                .width(parser.getDoubleOrDefault(PageKeys.WIDTH))
                .height(parser.getDoubleOrDefault(PageKeys.HEIGHT))
                .metadata(parser.getMapOrDefault(PageKeys.METADATA));

        JsonArray strokesJson = parser
                .getCollectionOrDefault(PageKeys.STROKES);
        strokesJson.forEach(strokeJson ->
                        page.addStroke(
                                readStrokeJson((JsonObject) strokeJson)
                        ));

        return page;
    }

    private Stroke readStrokeJson(JsonObject strokeJson) {

        Stroke stroke = new Stroke()
                .startTime(strokeJson.getLongOrDefault(StrokeKeys.START_TIME))
                .endTime(strokeJson.getLongOrDefault(StrokeKeys.END_TIME))
                .metadata(strokeJson.getMapOrDefault(StrokeKeys.METADATA));

        JsonArray dotsJson = strokeJson
                .getCollectionOrDefault(StrokeKeys.DOTS);
        dotsJson.forEach(dotJson ->
                        stroke.addDot(
                                readDotJson((JsonObject) dotJson)
                        ));

        return stroke;
    }

    private Dot readDotJson(JsonObject dotJson) {

        return new Dot()
                .x(dotJson.getIntegerOrDefault(DotKeys.X))
                .y(dotJson.getIntegerOrDefault(DotKeys.Y))
                .timestamp(dotJson.getLongOrDefault(DotKeys.TIMESTAMP))
                .pressure(dotJson.getDoubleOrDefault(DotKeys.PRESSURE))
                .type(DotType.valueOf(
                        dotJson.getStringOrDefault(DotKeys.TYPE).toUpperCase()
                ))
                .metadata(dotJson.getMapOrDefault(DotKeys.METADATA));
    }
}
