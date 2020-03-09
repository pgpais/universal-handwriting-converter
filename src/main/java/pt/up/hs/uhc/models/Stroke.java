package pt.up.hs.uhc.models;

import java.util.*;

/**
 * A collection of {@link Dot}s.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Stroke {

    private Long startTime;
    private Long endTime;
    private List<Dot> dots;
    private Map<String, Object> metadata;

    public Stroke() {
        this.dots = new ArrayList<>();
        this.metadata = new HashMap<>();
    }

    public Stroke(Long startTime, Long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dots = new ArrayList<>();
        this.metadata = new HashMap<>();
    }

    public Stroke(Long startTime, Long endTime, Map<String, Object> metadata, List<Dot> dots) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.metadata = metadata;
        this.dots = dots;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Stroke startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Stroke endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public List<Dot> getDots() {
        return dots;
    }

    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }

    public Stroke dots(List<Dot> dots) {
        this.dots = dots;
        return this;
    }

    public Stroke addDot(Dot dot) {
        this.dots.add(dot);
        return this;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Stroke metadata(Map<String, Object> metadata) {
        this.metadata = metadata;
        return this;
    }

    public Stroke addMetadata(String key, Object value) {
        this.metadata.put(key, value);
        return this;
    }

    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stroke)) return false;
        Stroke stroke = (Stroke) o;
        return Objects.equals(startTime, stroke.startTime) &&
                Objects.equals(endTime, stroke.endTime) &&
                Objects.equals(dots, stroke.dots) &&
                Objects.equals(metadata, stroke.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, dots, metadata);
    }

    @Override
    public String toString() {
        return "Stroke{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", metadata=" + metadata +
                ", dots=" + dots +
                '}';
    }
}
