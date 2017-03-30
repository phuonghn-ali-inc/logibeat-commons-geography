package com.logibeat.commons.geography.polygon;

import com.logibeat.commons.geography.GeoPoint;
import com.logibeat.commons.geography.GeoShape;

import java.awt.*;

/**
 * <p>
 * Geography Polygon<br/>
 * </p>
 * Created by alex on 17/02/2017.
 *
 * @since 2.0
 */
public abstract class GeoPolygon implements GeoShape {

    protected int npoints;

    protected GeoBounds geoBounds;

    public GeoPolygon(GeoPoint[] geoPoints) {
        npoints = geoPoints.length;
        geoBounds = new GeoBounds();
        geoBounds.setMaxX(geoPoints[0].getX());
        geoBounds.setMinX(geoPoints[0].getX());
        geoBounds.setMaxX(geoPoints[0].getY());
        geoBounds.setMinY(geoPoints[0].getY());
        for (GeoPoint geoPoint :
                geoPoints) {
            geoBounds.maxX = Math.max(geoBounds.maxX, geoPoint.getX());
            geoBounds.minX = Math.min(geoBounds.minX, geoPoint.getX());
            geoBounds.maxY = Math.max(geoBounds.maxY, geoPoint.getY());
            geoBounds.minY = Math.min(geoBounds.minY, geoPoint.getY());
        }
    }

    public int getNpoints() {
        return npoints;
    }

    public abstract PipAlgorithm getPipAlgorithm();

    public abstract boolean contains(double x, double y);

    public GeoBounds getGeoBounds() {
        return geoBounds;
    }

    /**
     * 多边形(几何学上的,每个点的<code>x</code>,<code>y</code>都是乘以1000000后的整数)
     *
     * @return
     */
    public abstract Polygon clonePolygon000000();

    public enum PipAlgorithm {
        JDK("JDK"),
        JDK_GENERAL_PATH("JDK_GENERAL_PATH"),
        SROMKU("SROMKU");

        private final String algorithm;

        PipAlgorithm(String policy) {
            this.algorithm = policy;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        @Override
        public String toString() {
            return "{" +
                    "algorithm='" + algorithm + '\'' +
                    '}';
        }
    }
}
