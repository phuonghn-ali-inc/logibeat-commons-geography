package com.logibeat.commons.geography.boundary;

import com.logibeat.commons.geography.GeoPoint;
import com.logibeat.commons.geography.GeoShape;
import com.logibeat.commons.geography.polygon.GeoBounds;
import com.logibeat.commons.geography.polygon.GeoPolygon;
import com.logibeat.commons.geography.utils.GeoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * bounadaries of a district consist of one or more <code>GeoPolygon</code>
 * Created by alex on 24/02/2017.
 */
public class GeoDistrictBoundaries implements GeoShape, Serializable {
    private Logger logger = LoggerFactory.getLogger(GeoDistrictBoundaries.class);
    private String adcode;
    private String center;
    private GeoPolygon[] geoPolygons;
    private GeoBounds geoBounds;

    public GeoDistrictBoundaries(String adcode, String center, GeoPolygon[] geoPolygons) {
        this.adcode = adcode;
        this.center = center;
        this.geoPolygons = geoPolygons;
        geoBounds = GeoUtils.unionGeoBouds(geoPolygons);
    }

    public String getAdcode() {
        return adcode;
    }

    public String getCenter() {
        return center;
    }

    public GeoPolygon[] getGeoPolygons() {
        return geoPolygons;
    }

    public boolean contains(GeoPoint geoPoint) {
        return contains(geoPoint.getX(), geoPoint.getY());
    }

    @Override
    public String toString() {
        return "GeoDistrictBoundaries{" +
                "adcode='" + adcode + '\'' +
                ", center='" + center + '\'' +
                ", geoPolygons.length=" + geoPolygons.length +
                '}';
    }

    @Override
    public boolean contains(double x, double y) {
        long t1 = System.currentTimeMillis();
        boolean b = false;
        for (int i = 0; i < geoPolygons.length; i++) {
            if (geoPolygons[i].contains(x, y)) {
                b = true;
                break;
            }
        }
        long t2 = System.currentTimeMillis();
        logger.debug("district[{}] contains ({},{}): {}, {}ms elapsed.",
                adcode, x, y, b, (t2 - t1));
        return b;
    }

    @Override
    public GeoBounds getGeoBounds() {
        return geoBounds;
    }
}
