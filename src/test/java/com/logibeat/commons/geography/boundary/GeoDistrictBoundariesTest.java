package com.logibeat.commons.geography.boundary;

import com.logibeat.commons.geography.GeoPoint;
import com.logibeat.commons.geography.polygon.GeoPolygon;
import com.logibeat.commons.geography.utils.GeoUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alex on 03/03/2017.
 */
public class GeoDistrictBoundariesTest {

    @Test
    public void contains_Beijing1() throws Exception {
        GeoDistrictBoundaries geoDistrictBoundaries = GeoUtils.buildGeoDistrictBoundaries(
                "src/test/resources/data/geo-boundaries-beijing.json");
        _contains_Beijing(geoDistrictBoundaries);
    }


    @Test
    public void contains_Beijing2() throws Exception {
        GeoDistrictBoundaries geoDistrictBoundaries = GeoUtils.buildGeoDistrictBoundaries(
                "src/test/resources/data/geo-boundaries-beijing.json",
                GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH);
        _contains_Beijing(geoDistrictBoundaries);
    }

    @Test
    public void contains_Beijing3() throws Exception {
        GeoDistrictBoundaries geoDistrictBoundaries = GeoUtils.buildGeoDistrictBoundaries(
                "src/test/resources/data/geo-boundaries-beijing.json",
                GeoPolygon.PipAlgorithm.SROMKU);
        _contains_Beijing(geoDistrictBoundaries);
    }

    @Test
    public void contains_Hangzhou1() throws Exception {
        GeoDistrictBoundaries geoDistrictBoundaries = GeoUtils.buildGeoDistrictBoundaries(
                "src/test/resources/data/geo-boundaries-hangzhou.json");
        _contains_Hangzhou(geoDistrictBoundaries);
    }

    @Test
    public void contains_Hangzhou2() throws Exception {
        GeoDistrictBoundaries geoDistrictBoundaries = GeoUtils.buildGeoDistrictBoundaries(
                "src/test/resources/data/geo-boundaries-hangzhou.json",
                GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH);
        _contains_Hangzhou(geoDistrictBoundaries);
    }

    @Test
    public void contains_Hangzhou3() throws Exception {
        GeoDistrictBoundaries geoDistrictBoundaries = GeoUtils.buildGeoDistrictBoundaries(
                "src/test/resources/data/geo-boundaries-hangzhou.json",
                GeoPolygon.PipAlgorithm.SROMKU);
        _contains_Hangzhou(geoDistrictBoundaries);
    }

    private void _contains_Beijing(GeoDistrictBoundaries geoDistrictBoundaries) {
        Assert.assertTrue(geoDistrictBoundaries.contains(makeGeoPointRandom(116.405285, 39.904989))); // beijing center
        Assert.assertTrue(geoDistrictBoundaries.contains(makeGeoPointRandom(116.394232, 40.036011))); // beijing north
        Assert.assertTrue(geoDistrictBoundaries.contains(makeGeoPointRandom(116.621042, 40.903093))); // beijing north
        Assert.assertFalse(geoDistrictBoundaries.contains(makeGeoPointRandom(120.153576, 30.287459)));// hangzhou center
        Assert.assertFalse(geoDistrictBoundaries.contains(makeGeoPointRandom(118.616411, 29.269244)));// hangzhou south-west
    }

    private void _contains_Hangzhou(GeoDistrictBoundaries geoDistrictBoundaries) {
        Assert.assertFalse(geoDistrictBoundaries.contains(makeGeoPointRandom(116.405285, 39.904989))); // beijing center
        Assert.assertTrue(geoDistrictBoundaries.contains(makeGeoPointRandom(120.153576, 30.287459)));// hangzhou center
        Assert.assertTrue(geoDistrictBoundaries.contains(new GeoPoint(118.616411, 29.269244)));// hangzhou south-west
        Assert.assertTrue(geoDistrictBoundaries.contains(new GeoPoint(119.104013, 30.314349)));// hangzhou north
        Assert.assertTrue(geoDistrictBoundaries.contains(new GeoPoint(118.963094, 30.340284)));// hangzhou north
        Assert.assertFalse(geoDistrictBoundaries.contains(new GeoPoint(119.685388, 30.433258)));// outer hangzhou north - in huzhou
    }

    private GeoPoint makeGeoPointRandom(double x, double y) {
        return new GeoPoint(x + RandomUtils.nextDouble(0.000001, 0.000999),
                y + RandomUtils.nextDouble(0.000001, 0.000999));
    }
}