package com.logibeat.commons.geography.boundary;

import com.logibeat.commons.geography.boundary.GeoDistrictBoundariesCollection;
import com.logibeat.commons.geography.polygon.GeoPolygon;
import com.logibeat.commons.geography.utils.GeoUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoDistrictBoundariesCollectionTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void containsGeoDistrictChinaCities1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip");
        Assert.assertEquals("330100", collection.whichContains(118.616411, 29.269244));
        Assert.assertEquals("330100", collection.whichContains(119.104013, 30.314349));
        Assert.assertEquals("330100", collection.whichContains(118.963094, 30.340284));
        Assert.assertEquals("330500", collection.whichContains(119.685388, 30.433258));
        Assert.assertEquals("330100", collection.whichContains(120.153576, 30.287459));
        Assert.assertEquals("330300", collection.whichContains(120.672111, 28.000575));
        Assert.assertEquals("331100", collection.whichContains(120.089836, 28.694312));
        Assert.assertEquals("330700", collection.whichContains(120.040398, 28.937366));
        Assert.assertEquals("110100", collection.whichContains(116.405285, 39.904989));
    }

    @Test
    public void containsGeoDistrictChinaDisctrict1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.PipAlgorithm.SROMKU);
        Assert.assertEquals("330127", collection.whichContains(118.616411, 29.269244));
        Assert.assertEquals("330185", collection.whichContains(119.104013, 30.314349));
        Assert.assertEquals("330185", collection.whichContains(118.963094, 30.340284));
        Assert.assertEquals("330523", collection.whichContains(119.685388, 30.433258));
        Assert.assertEquals("330103", collection.whichContains(120.153576, 30.287459));
        Assert.assertEquals("330302", collection.whichContains(120.672111, 28.000575));
        Assert.assertEquals("331122", collection.whichContains(120.089836, 28.694312));
        Assert.assertEquals("330784", collection.whichContains(120.040398, 28.937366));
        Assert.assertEquals("110101", collection.whichContains(116.405285, 39.904989));
        Assert.assertEquals("350128", collection.whichContains(119.74926, 25.484304));
        Assert.assertEquals("460323", collection.whichContains(114.475822, 16.040655));
        Assert.assertEquals("653121", collection.whichContains(75.781975, 39.270635));
    }
}