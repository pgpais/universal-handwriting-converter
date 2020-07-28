package pt.up.hs.uhc.handspy.keys;

import com.github.cliftonlabs.json_simple.JsonKey;

/**
 * Keys for {@link pt.up.hs.uhc.models.Page} metadata object.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public enum PageMetadataKeys implements JsonKey {
    ID(null),
    VERSION(null),
    PAPER_TYPE("paperType", null),
    PEN_ID("penId", null),
    PEN_SERIAL_NUMBER("penSerialNo", null),
    PEN_MANUFACTURER("penManufacturer", null),
    PEN_MODEL("penModel", null),
    PAGE_NUMBER("pageNo", null);

    private final String name;
    private final Object value;

    PageMetadataKeys(final Object value) {
        this.value = value;
        this.name = name().toLowerCase();
    }

    PageMetadataKeys(final String name, final Object value) {
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
