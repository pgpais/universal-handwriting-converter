package pt.up.hs.uhc.handspy.keys;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonKey;

import java.util.HashMap;

/**
 * Keys for {@link pt.up.hs.uhc.models.Page} object.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public enum PageKeys implements JsonKey {
    WIDTH(0D),
    HEIGHT(0D),
    METADATA(new HashMap<>()),
    STROKES(new JsonArray());

    private final String name;
    private final Object value;

    PageKeys(final Object value) {
        this.value = value;
        this.name = name().toLowerCase();
    }

    PageKeys(final String name, final Object value) {
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
