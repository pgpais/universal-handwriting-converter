package pt.up.hs.uhc.handspy.keys;

import com.github.cliftonlabs.json_simple.JsonKey;
import pt.up.hs.uhc.models.DotType;

import java.util.HashMap;

/**
 * Keys for {@link pt.up.hs.uhc.models.Dot} object.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public enum DotKeys implements JsonKey {
    X(0L),
    Y(0L),
    TIMESTAMP(0L),
    PRESSURE(0D),
    TYPE(DotType.DOWN.name()),
    METADATA(new HashMap<>());

    private final String name;
    private final Object value;

    DotKeys(final Object value) {
        this.value = value;
        this.name = name().toLowerCase();
    }

    DotKeys(final String name, final Object value) {
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
