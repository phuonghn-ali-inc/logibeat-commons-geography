package com.logibeat.commons.geography.boundary;

import com.logibeat.commons.geography.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * bounadaries of a district consist of one or more <code>GeoPolygon</code>
 * Created by alex on 24/02/2017.
 */
public class GeoDistrictBoundariesCollection {

    List<GeoDistrictBoundaries> geoDistrictBoundariesArray;
    private Logger logger = LoggerFactory.getLogger(GeoDistrictBoundariesCollection.class);

    /**
     * @param x
     * @param y
     * @return adcode
     */
    public String whichContains(double x, double y) {
        return whichContains(new GeoPoint(x, y));
    }

    /**
     * @param geoPoint
     * @return adcode
     */
    public String whichContains(GeoPoint geoPoint) {
        String adcode = null;
        for (GeoDistrictBoundaries geoDistrictBoundaries : geoDistrictBoundariesArray) {
            if (geoDistrictBoundaries.contains(geoPoint)) {
                adcode = geoDistrictBoundaries.getAdcode();
                break;
            }
        }
        logger.debug("adcode: {}", adcode);
        return adcode;
    }

    /**
     * @param adcode
     * @return
     */
    public GeoDistrictBoundaries getDistrictBoundaries(String adcode) {
        for (GeoDistrictBoundaries geoDistrictBoundaries : geoDistrictBoundariesArray) {
            if (adcode.equals(geoDistrictBoundaries.getAdcode())) {
                return geoDistrictBoundaries;
            }
        }
        return null;
    }

    public void setGeoDistrictBoundariesArray(List<GeoDistrictBoundaries> geoDistrictBoundariesArray) {
        this.geoDistrictBoundariesArray = geoDistrictBoundariesArray;
    }
}
