package com.logibeat.commons.geography;

import com.logibeat.commons.geography.polygon.GeoBounds;

/**
 * Created by alex on 17/02/2017.
 *
 * @since 2.0
 */
public interface GeoShape {

    boolean contains(double x, double y);

    GeoBounds getGeoBounds();

}
