package pt.up.hs.uhc.models;

import java.util.*;

/**
 * A page is a full-page sample collected.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Page {

    private Double width;
    private Double height;
    private Map<String, Object> metadata;

    private List<Stroke> strokes;

    public Page() {
        this.metadata = new HashMap<>();
        this.strokes = new ArrayList<>();
    }

    public Page(double width, double height) {
        this.width = width;
        this.height = height;
        this.metadata = new HashMap<>();
        this.strokes = new ArrayList<>();
    }

    public Page(double width, double height, Map<String, Object> metadata) {
        this.width = width;
        this.height = height;
        this.metadata = metadata;
        this.strokes = new ArrayList<>();
    }

    public Page(double width, double height, Map<String, Object> metadata, List<Stroke> strokes) {
        this.width = width;
        this.height = height;
        this.metadata = metadata;
        this.strokes = strokes;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Page width(Double pageWidth) {
        this.width = pageWidth;
        return this;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Page height(Double pageHeight) {
        this.height = pageHeight;
        return this;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Page metadata(Map<String, Object> metadata) {
        this.metadata = metadata;
        return this;
    }

    public Page addMetadata(String key, Object value) {
        this.metadata.put(key, value);
        return this;
    }

    public List<Stroke> getStrokes() {
        return strokes;
    }

    public void setStrokes(List<Stroke> strokes) {
        this.strokes = strokes;
    }

    public Page strokes(List<Stroke> strokes) {
        this.strokes = strokes;
        return this;
    }

    public Page addStroke(Stroke stroke) {
        this.strokes.add(stroke);
        return this;
    }

    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;
        Page page = (Page) o;
        return Objects.equals(width, page.width) &&
                Objects.equals(height, page.height) &&
                Objects.equals(metadata, page.metadata) &&
                Objects.equals(strokes, page.strokes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, metadata, strokes);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "width=" + width +
                ", height=" + height +
                ", metadata=" + metadata +
                ", strokes=" + strokes +
                '}';
    }
}
