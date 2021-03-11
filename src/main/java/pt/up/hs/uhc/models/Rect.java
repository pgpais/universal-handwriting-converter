package pt.up.hs.uhc.models;

import pt.up.hs.uhc.utils.NumberUtils;

import static pt.up.hs.uhc.base.Constants.EPSILON;

/**
 *
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class Rect {
    private double x1, x2, y1, y2;

    public Rect() {
    }

    public Rect(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX1() {
        return x1;
    }

    public Rect x1(double x1) {
        this.x1 = x1;
        return this;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public Rect x2(double x2) {
        this.x2 = x2;
        return this;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public Rect y1(double y1) {
        this.y1 = y1;
        return this;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public Rect y2(double y2) {
        this.y2 = y2;
        return this;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getWidth() {
        return x2 - x1;
    }

    public double getHeight() {
        return y2 - y1;
    }

    public boolean contains(Rect other) {
        return
                NumberUtils.compare(x1, other.x1) <= 0 &&
                NumberUtils.compare(x2, other.x2) >= 0 &&
                NumberUtils.compare(y1, other.y1) <= 0 &&
                NumberUtils.compare(y2, other.y2) >= 0;
    }
}
