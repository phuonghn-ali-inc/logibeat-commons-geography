package com.logibeat.commons.geography;

import java.awt.*;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoRectangle implements GeoShape {

    private Rectangle geomRectangle000000;

    public boolean contains(double x, double y) {
        return false;
    }

    public Rectangle cloneGeomRectangle000000() {
        return new Rectangle(geomRectangle000000.x,
                geomRectangle000000.y,
                geomRectangle000000.width,
                geomRectangle000000.height);
    }
}
