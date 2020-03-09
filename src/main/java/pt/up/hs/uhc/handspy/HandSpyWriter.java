package pt.up.hs.uhc.handspy;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import pt.up.hs.uhc.PageWriter;
import pt.up.hs.uhc.handspy.keys.DotKeys;
import pt.up.hs.uhc.handspy.keys.PageKeys;
import pt.up.hs.uhc.handspy.keys.StrokeKeys;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;
import pt.up.hs.uhc.utils.JsonUtils;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Writer of HandSpy JSON pages.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyWriter implements PageWriter {

    @Override
    public void write(Page page, OutputStream os) throws Exception {

        JsonObject pageJson = new JsonObject();

        // add page info
        pageJson.put(PageKeys.WIDTH, page.getWidth());
        pageJson.put(PageKeys.HEIGHT, page.getHeight());
        pageJson.put(PageKeys.METADATA, JsonUtils.fromMap(page.getMetadata()));

        // add strokes' info
        JsonArray strokesJson = new JsonArray();
        page.getStrokes()
                .forEach(stroke -> strokesJson.add(buildStrokeJson(stroke)));
        pageJson.put(PageKeys.STROKES, strokesJson);

        // write JSON to stream
        Writer writer = new OutputStreamWriter(os);
        Jsoner.serialize(pageJson, writer);
        writer.close();
    }

    /**
     * Build JSON Object from stroke.
     *
     * @param stroke {@link Stroke} the stroke.
     * @return {@link JsonObject} json object of the stroke.
     */
    private JsonObject buildStrokeJson(Stroke stroke) {

        JsonObject strokeJson = new JsonObject();

        // add stroke info
        strokeJson.put(StrokeKeys.START_TIME, stroke.getStartTime());
        strokeJson.put(StrokeKeys.END_TIME, stroke.getEndTime());
        strokeJson.put(StrokeKeys.METADATA, JsonUtils.fromMap(stroke.getMetadata()));

        // add dots' info
        JsonArray strokesJson = new JsonArray();
        stroke.getDots()
                .forEach(dot -> strokesJson.add(buildDotJson(dot)));
        strokeJson.put(StrokeKeys.DOTS, strokesJson);

        return strokeJson;
    }

    /**
     * Build JSON Object from dot.
     *
     * @param dot {@link Dot} the dot.
     * @return {@link JsonObject} json object of the dot.
     */
    private JsonObject buildDotJson(Dot dot) {

        JsonObject jsonObject = new JsonObject();

        jsonObject.put(DotKeys.X, dot.getX());
        jsonObject.put(DotKeys.Y, dot.getY());
        jsonObject.put(DotKeys.TIMESTAMP, dot.getTimestamp());
        jsonObject.put(DotKeys.TYPE, dot.getType().name());

        jsonObject.put(DotKeys.PRESSURE, dot.getPressure());

        jsonObject.put(DotKeys.METADATA, JsonUtils.fromMap(dot.getMetadata()));

        return jsonObject;
    }
}
