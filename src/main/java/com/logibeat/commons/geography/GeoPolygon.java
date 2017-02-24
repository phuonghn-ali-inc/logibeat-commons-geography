package com.logibeat.commons.geography;

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

    public GeoPolygon(GeoPoint[] geoPoints) {
        npoints = geoPoints.length;
    }


    public int getNpoints() {
        return npoints;
    }

    public abstract AlgorithmPolicy getAlgorithmPolicy();

    public boolean contains(double x, double y) {
        return contains(new GeoPoint(x, y));
    }

    public abstract boolean contains(GeoPoint geoPoint);

    /**
     * 多边形(几何学上的,每个点的<code>x</code>,<code>y</code>都是乘以1000000后的整数)
     *
     * @return
     */
    public abstract Polygon clonePolygon000000();

    public enum AlgorithmPolicy {
        JDK("JDK"),
        JDK_GENERAL_PATH("JDK_GENERAL_PATH"),
        SROMKU("SROMKU");

        private final String policy;

        AlgorithmPolicy(String policy) {
            this.policy = policy;
        }

        public String getPolicy() {
            return policy;
        }
    }
}
