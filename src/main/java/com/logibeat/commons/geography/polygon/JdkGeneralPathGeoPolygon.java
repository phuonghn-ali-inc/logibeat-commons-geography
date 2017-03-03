package com.logibeat.commons.geography.polygon;

import com.logibeat.commons.geography.GeoPoint;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * Created by alex on 25/02/2017.
 */
public class JdkGeneralPathGeoPolygon extends GeoPolygon {

    protected GeneralPath generalPath;

    protected Point2D.Double[] point2DArray;

    protected Point2D.Double first;

    public JdkGeneralPathGeoPolygon(GeoPoint[] geoPoints) {
        super(geoPoints);
        point2DArray = new Point2D.Double[geoPoints.length];
        first = convert(geoPoints[0]);
        generalPath = new java.awt.geom.GeneralPath();
        generalPath.moveTo(first.getX(), first.getY());
        for (int i = 1; i < geoPoints.length; i++) {
            point2DArray[i] = convert(geoPoints[i]);
            generalPath.lineTo(point2DArray[i].getX(), point2DArray[i].getY());
        }
        generalPath.lineTo(first.getX(), first.getY());
        generalPath.closePath();
        generalPath.getBounds();
    }

    private Point2D.Double convert(GeoPoint geoPoint) {
        return new Point2D.Double(geoPoint.getX000000(), geoPoint.getY000000());
    }

    public PipAlgorithm getPipAlgorithm() {
        return PipAlgorithm.JDK_GENERAL_PATH;
    }

    public boolean contains(GeoPoint geoPoint) {
        if (!generalPath.getBounds().contains(geoPoint.getX000000(), geoPoint.getY000000())) {
            return false;
        }
        return generalPath.contains(geoPoint.getX000000(), geoPoint.getY000000());
    }

    public Polygon clonePolygon000000() {
        int[] xpoints_copy = new int[npoints];
        int[] ypoints_copy = new int[npoints];
        for (int i = 0; i < npoints; i++) {
            xpoints_copy[i] = (int) (point2DArray[i].getX() * GeoPoint.TIMES);
            ypoints_copy[i] = (int) (point2DArray[i].getX() * GeoPoint.TIMES);
        }
        return new Polygon(xpoints_copy, ypoints_copy, npoints);
    }
}
