package com.logibeat.commons.geography.polygon;

import com.logibeat.commons.geography.GeoShape;

/**
 * Created by alex on 30/03/2017.
 */
public class GeoBounds implements GeoShape {

    double maxX = Double.NEGATIVE_INFINITY;

    double minX = Double.NEGATIVE_INFINITY;

    double maxY = Double.NEGATIVE_INFINITY;

    double minY = Double.NEGATIVE_INFINITY;

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    @Override
    public boolean contains(double x, double y) {
        if (x < minX || x > maxX || y < minY || y > maxY) {
            return false;
        }
        return true;
    }

    @Override
    public GeoBounds getGeoBounds() {
        return this;
    }
}
