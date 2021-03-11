package pt.up.hs.uhc.models;

import pt.up.hs.uhc.handspy.keys.PageMetadataKeys;

import java.io.Serializable;
import java.util.*;

/**
 * A page is a full-page sample collected.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Page implements Serializable, Cloneable {

    private Double width;
    private Double height;
    private Double marginLeft = 0D;
    private Double marginTop = 0D;
    private Double marginRight = 0D;
    private Double marginBottom = 0D;
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

    public Page(Double width, Double height, Double marginLeft, Double marginTop, Double marginRight, Double marginBottom) {
        this.width = width;
        this.height = height;
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        this.metadata = new HashMap<>();
        this.strokes = new ArrayList<>();
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

    public Double getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(Double marginLeft) {
        this.marginLeft = marginLeft;
    }

    public Page marginLeft(Double marginLeft) {
        this.marginLeft = marginLeft;
        return this;
    }

    public Double getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(Double marginTop) {
        this.marginTop = marginTop;
    }

    public Page marginTop(Double marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public Double getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(Double marginRight) {
        this.marginRight = marginRight;
    }

    public Page marginRight(Double marginRight) {
        this.marginRight = marginRight;
        return this;
    }

    public Double getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(Double marginBottom) {
        this.marginBottom = marginBottom;
    }

    public Page marginBottom(Double marginBottom) {
        this.marginBottom = marginBottom;
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

    public Page addMetadataId(String id) {
        this.metadata.put(PageMetadataKeys.ID.getKey(), id);
        return this;
    }

    public Page addMetadataVersion(String version) {
        this.metadata.put(PageMetadataKeys.VERSION.getKey(), version);
        return this;
    }

    public Page addMetadataPaperType(String paperType) {
        this.metadata.put(PageMetadataKeys.PAPER_TYPE.getKey(), paperType);
        return this;
    }

    public Page addMetadataPenId(String penId) {
        this.metadata.put(PageMetadataKeys.PEN_ID.getKey(), penId);
        return this;
    }

    public Page addMetadataPenSerialNo(String penSerialNo) {
        this.metadata.put(PageMetadataKeys.PEN_SERIAL_NUMBER.getKey(), penSerialNo);
        return this;
    }

    public Page addMetadataPenManufacturer(String penManufacturer) {
        this.metadata.put(PageMetadataKeys.PEN_MANUFACTURER.getKey(), penManufacturer);
        return this;
    }

    public Page addMetadataPenModel(String penModel) {
        this.metadata.put(PageMetadataKeys.PEN_MODEL.getKey(), penModel);
        return this;
    }

    public Page addMetadataPageNo(Integer pageNo) {
        this.metadata.put(PageMetadataKeys.PAGE_NUMBER.getKey(), pageNo);
        return this;
    }

    public Page addMetadataCaptureError(CaptureError captureError) {
        this.metadata.put(PageMetadataKeys.CAPTURE_ERROR.getKey(), captureError);
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

    @Override
    public Page clone() throws CloneNotSupportedException {
        return (Page) super.clone();
    }
}
