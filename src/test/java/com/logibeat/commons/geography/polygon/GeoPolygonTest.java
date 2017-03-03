package com.logibeat.commons.geography.polygon;

import com.logibeat.commons.geography.GeoPoint;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alex on 03/03/2017.
 */
public class GeoPolygonTest {

    @Test
    public void contains1() throws Exception {
        GeoPolygon polygon = new JdkGeoPolygon(new GeoPoint[]{
                new GeoPoint(0.0, 0.0),
                new GeoPoint(4.0, 0.0),
                new GeoPoint(4.0, 4.0),
                new GeoPoint(0.0, 4.0),
        });
        Assert.assertFalse(polygon.contains(113, 113));
        Assert.assertFalse(polygon.contains(-1, -3));
        Assert.assertFalse(polygon.contains(-1, 5));
        Assert.assertFalse(polygon.contains(4, 4.0001));
        Assert.assertFalse(polygon.contains(114, 114));
        Assert.assertFalse(polygon.contains(113, 113));
        Assert.assertTrue(polygon.contains(0, 2));
        Assert.assertTrue(polygon.contains(2, 2));
        Assert.assertTrue(polygon.contains(2, 0));
        Assert.assertTrue(polygon.contains(0, 0));
        Assert.assertTrue(polygon.contains(3.999999, 3.999999));
        Assert.assertTrue(polygon.contains(0.000001, 3.999999));
        Assert.assertTrue(polygon.contains(3.999999, 0.000001));
        Assert.assertFalse(polygon.contains(4.000001, 0.000001));
        Assert.assertFalse(polygon.contains(0.000001, 4.000001));
        Assert.assertFalse(polygon.contains(-0.000001, 3.999999));
        // FIXME all vertex and points on line surpposed to be true
        // 需要将所有"顶点"和"边上的点"也纳入范围
        {
            Assert.assertFalse(polygon.contains(4, 4));
            Assert.assertFalse(polygon.contains(4, 2));
        }
    }

    @Test
    public void contains2() throws Exception {
        GeoPolygon polygon = new JdkGeneralPathGeoPolygon(new GeoPoint[]{
                new GeoPoint(0.0, 0.0),
                new GeoPoint(4.0, 0.0),
                new GeoPoint(4.0, 4.0),
                new GeoPoint(0.0, 4.0),
        });
        Assert.assertFalse(polygon.contains(113, 113));
        Assert.assertFalse(polygon.contains(-1, -3));
        Assert.assertFalse(polygon.contains(-1, 5));
        Assert.assertFalse(polygon.contains(4, 4.0001));
        Assert.assertFalse(polygon.contains(114, 114));
        Assert.assertFalse(polygon.contains(113, 113));
        Assert.assertTrue(polygon.contains(0, 2));
        Assert.assertTrue(polygon.contains(2, 2));
        Assert.assertTrue(polygon.contains(2, 0));
        Assert.assertTrue(polygon.contains(0, 0));
        Assert.assertTrue(polygon.contains(3.999999, 3.999999));
        Assert.assertTrue(polygon.contains(0.000001, 3.999999));
        Assert.assertTrue(polygon.contains(3.999999, 0.000001));
        Assert.assertFalse(polygon.contains(4.000001, 0.000001));
        Assert.assertFalse(polygon.contains(0.000001, 4.000001));
        Assert.assertFalse(polygon.contains(-0.000001, 3.999999));
        // FIXME all vertex and points on line surpposed to be true
        // 需要将所有"顶点"和"边上的点"也纳入范围
        {
            Assert.assertFalse(polygon.contains(4, 4));
            Assert.assertFalse(polygon.contains(4, 2));
        }
    }

    @Test
    public void contains3() throws Exception {
        GeoPolygon polygon = new SromkuGeoPolygon(new GeoPoint[]{
                new GeoPoint(0.0, 0.0),
                new GeoPoint(4.0, 0.0),
                new GeoPoint(4.0, 4.0),
                new GeoPoint(0.0, 4.0),
        });
        Assert.assertFalse(polygon.contains(113, 113));
        Assert.assertFalse(polygon.contains(-1, -3));
        Assert.assertFalse(polygon.contains(-1, 5));
        Assert.assertFalse(polygon.contains(4, 4.0001));
        Assert.assertFalse(polygon.contains(114, 114));
        Assert.assertFalse(polygon.contains(113, 113));
        Assert.assertTrue(polygon.contains(0, 2));
        Assert.assertTrue(polygon.contains(2, 2));
        Assert.assertTrue(polygon.contains(2, 0));
        Assert.assertTrue(polygon.contains(0, 0));
        Assert.assertTrue(polygon.contains(3.999999, 3.999999));
        Assert.assertTrue(polygon.contains(0.000001, 3.999999));
        Assert.assertTrue(polygon.contains(3.999999, 0.000001));
        Assert.assertFalse(polygon.contains(4.000001, 0.000001));
        Assert.assertFalse(polygon.contains(0.000001, 4.000001));
        Assert.assertFalse(polygon.contains(-0.000001, 3.999999));
        // FIXME all vertex surpposed to be true
        {
            Assert.assertTrue(polygon.contains(4, 4));
            Assert.assertFalse(polygon.contains(4, 2));
        }
    }

}