package pt.up.hs.uhc.handspy.keys;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonKey;

import java.util.HashMap;

/**
 * Keys for {@link pt.up.hs.uhc.models.Stroke} object.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public enum StrokeKeys implements JsonKey {
    START_TIME("startTime", 0L),
    END_TIME("endTime", 0L),
    METADATA(new HashMap<>()),
    DOTS(new JsonArray());

    private final String name;
    private final Object value;

    StrokeKeys(final Object value) {
        this.value = value;
        this.name = name().toLowerCase();
    }

    StrokeKeys(final String name, final Object value) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
