package com.logibeat.commons.geography;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoUtilsTest {

    @Before
    public void setUp() throws Exception {
        RandomUtils.nextDouble(0.000001, 0.000999);
    }

    @After
    public void tearDown() throws Exception {
    }


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

    @Test
    public void containsGeoDistrictBeijing1() throws Exception {
        GeoDistrict geoDistrict = GeoUtils.buildGeoDistrict(
                "src/test/resources/data/geo-boundaries-beijing.json", GeoPolygon.AlgorithmPolicy.JDK);
        _containsGeoDistrictBeijing(geoDistrict);
    }


    @Test
    public void containsGeoDistrictBeijing2() throws Exception {
        GeoDistrict geoDistrict = GeoUtils.buildGeoDistrict(
                "src/test/resources/data/geo-boundaries-beijing.json", GeoPolygon.AlgorithmPolicy.JDK_GENERAL_PATH);
        _containsGeoDistrictBeijing(geoDistrict);
    }

    @Test
    public void containsGeoDistrictBeijing3() throws Exception {
        GeoDistrict geoDistrict = GeoUtils.buildGeoDistrict(
                "src/test/resources/data/geo-boundaries-beijing.json", GeoPolygon.AlgorithmPolicy.SROMKU);
        _containsGeoDistrictBeijing(geoDistrict);
    }

    @Test
    public void containsGeoDistrictHangzhou1() throws Exception {
        GeoDistrict geoDistrict = GeoUtils.buildGeoDistrict(
                "src/test/resources/data/geo-boundaries-hangzhou.json", GeoPolygon.AlgorithmPolicy.JDK);
        _containsGeoDistrictHangzhou(geoDistrict);
    }

    @Test
    public void containsGeoDistrictHangzhou2() throws Exception {
        GeoDistrict geoDistrict = GeoUtils.buildGeoDistrict(
                "src/test/resources/data/geo-boundaries-hangzhou.json", GeoPolygon.AlgorithmPolicy.JDK_GENERAL_PATH);
        _containsGeoDistrictHangzhou(geoDistrict);
    }

    @Test
    public void containsGeoDistrictHangzhou3() throws Exception {
        GeoDistrict geoDistrict = GeoUtils.buildGeoDistrict(
                "src/test/resources/data/geo-boundaries-hangzhou.json", GeoPolygon.AlgorithmPolicy.SROMKU);
        _containsGeoDistrictHangzhou(geoDistrict);
    }

    private void _containsGeoDistrictBeijing(GeoDistrict geoDistrict) {
        Assert.assertTrue(geoDistrict.contains(makeGeoPointRandom(116.405285, 39.904989))); // beijing center
        Assert.assertTrue(geoDistrict.contains(makeGeoPointRandom(116.394232, 40.036011))); // beijing north
        Assert.assertTrue(geoDistrict.contains(makeGeoPointRandom(116.621042, 40.903093))); // beijing north
        Assert.assertFalse(geoDistrict.contains(makeGeoPointRandom(120.153576, 30.287459)));// hangzhou center
        Assert.assertFalse(geoDistrict.contains(makeGeoPointRandom(118.616411, 29.269244)));// hangzhou south-west
    }

    private void _containsGeoDistrictHangzhou(GeoDistrict geoDistrict) {
        Assert.assertFalse(geoDistrict.contains(makeGeoPointRandom(116.405285, 39.904989))); // beijing center
        Assert.assertTrue(geoDistrict.contains(makeGeoPointRandom(120.153576, 30.287459)));// hangzhou center
        Assert.assertTrue(geoDistrict.contains(new GeoPoint(118.616411, 29.269244)));// hangzhou south-west
        Assert.assertTrue(geoDistrict.contains(new GeoPoint(119.104013, 30.314349)));// hangzhou north
        Assert.assertTrue(geoDistrict.contains(new GeoPoint(118.963094, 30.340284)));// hangzhou north
        Assert.assertFalse(geoDistrict.contains(new GeoPoint(119.685388, 30.433258)));// outer hangzhou north - in huzhou
    }

    private GeoPoint makeGeoPointRandom(double x, double y) {
        return new GeoPoint(x + RandomUtils.nextDouble(0.000001, 0.000999),
                y + RandomUtils.nextDouble(0.000001, 0.000999));
    }

    @Test
    public void containsGeoDistrictChinaCities1() throws Exception {
        GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.AlgorithmPolicy.JDK);
        Assert.assertEquals("330100", GeoUtils.containsInAdcode(geoDistricts, 118.616411, 29.269244));
        Assert.assertEquals("330100", GeoUtils.containsInAdcode(geoDistricts, 119.104013, 30.314349));
        Assert.assertEquals("330100", GeoUtils.containsInAdcode(geoDistricts, 118.963094, 30.340284));
        Assert.assertEquals("330500", GeoUtils.containsInAdcode(geoDistricts, 119.685388, 30.433258));
        Assert.assertEquals("330100", GeoUtils.containsInAdcode(geoDistricts, 120.153576, 30.287459));
        Assert.assertEquals("330300", GeoUtils.containsInAdcode(geoDistricts, 120.672111, 28.000575));
        Assert.assertEquals("331100", GeoUtils.containsInAdcode(geoDistricts, 120.089836, 28.694312));
        Assert.assertEquals("330700", GeoUtils.containsInAdcode(geoDistricts, 120.040398, 28.937366));
        Assert.assertEquals("110100", GeoUtils.containsInAdcode(geoDistricts, 116.405285, 39.904989));
    }

    @Test
    public void containsGeoDistrictChinaDisctrict1() throws Exception {
        GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.AlgorithmPolicy.JDK);
        Assert.assertEquals("330127", GeoUtils.containsInAdcode(geoDistricts, 118.616411, 29.269244));
        Assert.assertEquals("330185", GeoUtils.containsInAdcode(geoDistricts, 119.104013, 30.314349));
        Assert.assertEquals("330185", GeoUtils.containsInAdcode(geoDistricts, 118.963094, 30.340284));
        Assert.assertEquals("330523", GeoUtils.containsInAdcode(geoDistricts, 119.685388, 30.433258));
        Assert.assertEquals("330103", GeoUtils.containsInAdcode(geoDistricts, 120.153576, 30.287459));
        Assert.assertEquals("330302", GeoUtils.containsInAdcode(geoDistricts, 120.672111, 28.000575));
        Assert.assertEquals("331122", GeoUtils.containsInAdcode(geoDistricts, 120.089836, 28.694312));
        Assert.assertEquals("330784", GeoUtils.containsInAdcode(geoDistricts, 120.040398, 28.937366));
        Assert.assertEquals("110101", GeoUtils.containsInAdcode(geoDistricts, 116.405285, 39.904989));
        Assert.assertEquals("350128", GeoUtils.containsInAdcode(geoDistricts, 119.74926, 25.484304));
        Assert.assertEquals("460323", GeoUtils.containsInAdcode(geoDistricts, 114.475822, 16.040655));
        Assert.assertEquals("653121", GeoUtils.containsInAdcode(geoDistricts, 75.781975,39.270635));
    }
}