package com.logibeat.commons.geography;

import java.awt.geom.Point2D;

/**
 * <pre>
 * Geography Point
 * <code>x</code>: -180 ~ 180
 * <code>y</code>: -90 ~ 90
 * in <code>%.6f</code> format
 * </pre>
 * Created by alex on 17/02/2017.
 *
 * @since 2.0
 */
public class GeoPoint {

    public static final int TIMES = 1000 * 1000;
    private Point2D.Double point;

    /**
     * @param x
     * @param y
     */
    public GeoPoint(double x, double y) {
        point = new Point2D.Double(x, y);
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public int getX000000() {
        return (int) (getX() * TIMES);
    }

    public int getY000000() {
        return (int) (getY() * TIMES);
    }

}
