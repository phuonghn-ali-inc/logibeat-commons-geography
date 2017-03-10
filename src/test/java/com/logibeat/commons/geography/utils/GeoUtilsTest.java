package com.logibeat.commons.geography.utils;

import com.logibeat.commons.geography.boundary.GeoDistrictBoundariesCollection;
import com.logibeat.commons.geography.district.GeoDistrict;
import com.logibeat.commons.geography.district.GeoDistrictCollection;
import com.logibeat.commons.geography.district.GeoDistrictLevel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alex on 10/03/2017.
 */
public class GeoUtilsTest {

    @Test
    public void buildGeoDistrictCollection1() throws Exception {
        GeoDistrictCollection collection = GeoUtils.buildGeoDistrictCollection("src/main/resources/geo-district-china-dist.json");
        Assert.assertNotNull(collection);
        Assert.assertEquals("中国", collection.getShortNameByAdcode("100000"));
        Assert.assertEquals("浙江省", collection.getNameByAdcode("330000"));
        Assert.assertEquals("浙江省,嘉兴市,南湖区", collection.getMergerNameByAdcode("330402"));
        Assert.assertEquals("安徽,蚌埠,淮上", collection.getMergerShortNameByAdcode("340311"));
    }

    @Test
    public void buildGeoDistrictCollection2() throws Exception {
        GeoDistrictCollection collection = GeoUtils.buildGeoDistrictCollection();
        Assert.assertNotNull(collection);
        Assert.assertEquals("中国", collection.getShortNameByAdcode("100000"));
        Assert.assertEquals("浙江省", collection.getNameByAdcode("330000"));
        Assert.assertEquals("浙江省,嘉兴市,南湖区", collection.getMergerNameByAdcode("330402"));
        Assert.assertEquals("安徽,蚌埠,淮上", collection.getMergerShortNameByAdcode("340311"));
    }

    @Test
    public void test1() throws Exception {
        GeoDistrictCollection districtCollection = GeoUtils.buildGeoDistrictCollection();
        GeoDistrictBoundariesCollection boundariesCollection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip");
        String adcode = boundariesCollection.whichContains(118.616411, 29.269244);
        Assert.assertNotNull(adcode);
        Assert.assertEquals("杭州", districtCollection.getShortNameByAdcode(adcode));
        GeoDistrict geoDistrict = districtCollection.byAdcode(adcode);
        Assert.assertNotNull(geoDistrict);
        // {"adcode":"330100","center":"120.153576,30.287459","citycode":"0571","level":"city","levelInt":2,"mergerName":"浙江省,杭州市","mergerShortName":"浙江,杭州","name":"杭州市","parentAdcode":"330000","shortName":"杭州"},
        Assert.assertEquals("杭州市", geoDistrict.getName());
        Assert.assertEquals(GeoDistrictLevel.CITY, GeoDistrictLevel.levelValueOf(geoDistrict.getLevel()));
        Assert.assertEquals(GeoDistrictLevel.CITY.getValue(), geoDistrict.getLevelInt());
        Assert.assertEquals("浙江省,杭州市", geoDistrict.getMergerName());
        Assert.assertEquals("浙江,杭州", geoDistrict.getMergerShortName());
    }
}