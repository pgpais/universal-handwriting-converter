package pt.up.hs.uhc.utils;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.Map;

/**
 * Utilities to deal with JSON.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class JsonUtils {

    public static JsonObject fromMap(Map<String, ?> simpleMap) {
        JsonObject jsonObject = new JsonObject();
        simpleMap.entrySet().parallelStream()
                .forEach(e -> jsonObject.put(e.getKey(), e.getValue()));
        return jsonObject;
    }
}
