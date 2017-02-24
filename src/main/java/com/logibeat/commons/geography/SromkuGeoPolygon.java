package com.logibeat.commons.geography;

import com.sromku.polygon.Point;

import java.awt.*;

/**
 * Created by alex on 24/02/2017.
 */
public class SromkuGeoPolygon extends GeoPolygon {

    /**
     * x坐标
     */
    protected double[] xpoints;
    /**
     * y坐标, 维度
     */
    protected double[] ypoints;

    com.sromku.polygon.Polygon sromkuPolygon;


    public SromkuGeoPolygon(GeoPoint[] geoPoints) {
        super(geoPoints);


        xpoints = new double[npoints];
        ypoints = new double[npoints];
        for (int i = 0; i < npoints; ++i) {
            xpoints[i] = geoPoints[i].getX();
            ypoints[i] = geoPoints[i].getY();
        }
        com.sromku.polygon.Polygon.Builder builder = new com.sromku.polygon.Polygon.Builder();
        for (int i = 0; i < npoints; ++i) {
            builder.addVertex(new Point(xpoints[i], ypoints[i]));
        }
        sromkuPolygon = builder.build();
    }

    public AlgorithmPolicy getAlgorithmPolicy() {
        return AlgorithmPolicy.SROMKU;
    }

    public boolean contains(GeoPoint geoPoint) {
        return sromkuPolygon.contains(new Point(geoPoint.getX(), geoPoint.getY()));
    }

    public Polygon clonePolygon000000() {
        int[] xpoints_copy = new int[npoints];
        int[] ypoints_copy = new int[npoints];
        for (int i = 0; i < npoints; i++) {
            xpoints_copy[i] = (int) (xpoints[i] * GeoPoint.TIMES);
            ypoints_copy[i] = (int) (ypoints[i] * GeoPoint.TIMES);
        }
        return new Polygon(xpoints_copy, ypoints_copy, npoints);
    }
}
