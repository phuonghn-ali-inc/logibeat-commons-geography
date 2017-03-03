package com.logibeat.commons.geography.polygon;

import com.logibeat.commons.geography.GeoPoint;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by alex on 24/02/2017.
 */
public class JdkGeoPolygon extends GeoPolygon {

    /**
     * using integer instead of double
     */
    protected Polygon geomPolygon000000;

    public JdkGeoPolygon(GeoPoint[] geoPoints) {
        super(geoPoints);
        npoints = geoPoints.length;
        int[] xpoints000000 = new int[npoints];
        int[] ypoints000000 = new int[npoints];
        for (int i = 0; i < npoints; ++i) {
            xpoints000000[i] = geoPoints[i].getX000000();
            ypoints000000[i] = geoPoints[i].getY000000();
        }
        geomPolygon000000 = new Polygon(xpoints000000, ypoints000000, npoints);
    }

    public PipAlgorithm getPipAlgorithm() {
        return PipAlgorithm.JDK;
    }

    public boolean contains(GeoPoint geoPoint) {
        return geomPolygon000000.contains(geoPoint.getX000000(), geoPoint.getY000000());
    }

    @SuppressWarnings("Since15")
    public Polygon clonePolygon000000() {
        int[] xpoints_copy = Arrays.copyOf(geomPolygon000000.xpoints, npoints);
        int[] ypoints_copy = Arrays.copyOf(geomPolygon000000.ypoints, npoints);
        System.arraycopy(geomPolygon000000.xpoints, 0, xpoints_copy, 0, npoints);
        System.arraycopy(geomPolygon000000.ypoints, 0, ypoints_copy, 0, npoints);
        return new Polygon(xpoints_copy, ypoints_copy, npoints);
    }
}
