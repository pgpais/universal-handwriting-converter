package pt.up.hs.uhc.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A dot is the smallest element collected from handwriting.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Dot implements Serializable, Cloneable {

    private Double x;
    private Double y;
    private Long timestamp;
    private DotType type = DotType.DOWN;

    private Double pressure;

    private Map<String, Object> metadata;

    public Dot() {
        this.metadata = new HashMap<>();
    }

    public Dot(Double x, Double y, Long timestamp) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.metadata = new HashMap<>();
    }

    public Dot(Double x, Double y, Long timestamp, Double pressure) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.pressure = pressure;
        this.metadata = new HashMap<>();
    }

    public Dot(Double x, Double y, Long timestamp, DotType type, Double pressure) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.type = type;
        this.pressure = pressure;
        this.metadata = new HashMap<>();
    }

    public Dot(Double x, Double y, Long timestamp, DotType type, Double pressure, Map<String, Object> metadata) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.type = type;
        this.pressure = pressure;
        this.metadata = metadata;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Dot x(Double x) {
        this.x = x;
        return this;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Dot y(Double y) {
        this.y = y;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Dot timestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public DotType getType() {
        return type;
    }

    public void setType(DotType type) {
        this.type = type;
    }

    public Dot type(DotType type) {
        this.type = type;
        return this;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Dot pressure(Double pressure) {
        this.pressure = pressure;
        return this;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Dot metadata(Map<String, Object> metadata) {
        this.metadata = metadata;
        return this;
    }

    public Dot addMetadata(String key, Object value) {
        this.metadata.put(key, value);
        return this;
    }

    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dot)) return false;
        Dot dot = (Dot) o;
        return Objects.equals(x, dot.x) &&
                Objects.equals(y, dot.y) &&
                Objects.equals(timestamp, dot.timestamp) &&
                type == dot.type &&
                Objects.equals(pressure, dot.pressure) &&
                Objects.equals(metadata, dot.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, timestamp, type, pressure, metadata);
    }

    @Override
    public String toString() {
        return "Dot{" +
                "x=" + x +
                ", y=" + y +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", pressure=" + pressure +
                ", metadata=" + metadata +
                '}';
    }

    @Override
    public Dot clone() throws CloneNotSupportedException {
        return (Dot) super.clone();
    }
}
