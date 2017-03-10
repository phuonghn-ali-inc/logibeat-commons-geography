package com.logibeat.commons.geography.boundary;

import com.logibeat.commons.geography.GeoPoint;
import com.logibeat.commons.geography.polygon.GeoPolygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * bounadaries of a district consist of one or more <code>GeoPolygon</code>
 * Created by alex on 24/02/2017.
 */
public class GeoDistrictBoundaries  implements Serializable {
    private Logger logger = LoggerFactory.getLogger(GeoDistrictBoundaries.class);
    private String adcode;
    private String center;
    private GeoPolygon[] geoPolygons;

    public GeoDistrictBoundaries(String adcode, String center, GeoPolygon[] geoPolygons) {
        this.adcode = adcode;
        this.center = center;
        this.geoPolygons = geoPolygons;
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
        long t1 = System.currentTimeMillis();
        boolean b = _contains(geoPoint);
        long t2 = System.currentTimeMillis();
        logger.debug("district[{}] contains ({},{}): {}, {}ms elapsed.",
                adcode,
                geoPoint.getX(),
                geoPoint.getY(),
                b,
                (t2 - t1));
        return b;
    }

    private boolean _contains(GeoPoint geoPoint) {
        for (int i = 0; i < geoPolygons.length; i++) {
            if (geoPolygons[i].contains(geoPoint)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "GeoDistrictBoundaries{" +
                "adcode='" + adcode + '\'' +
                ", center='" + center + '\'' +
                ", geoPolygons.length=" + geoPolygons.length +
                '}';
    }
}
