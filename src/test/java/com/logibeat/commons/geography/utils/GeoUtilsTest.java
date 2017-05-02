package com.logibeat.commons.geography.utils;

import com.logibeat.commons.geography.boundary.GeoDistrictBoundariesCollection;
import com.logibeat.commons.geography.district.GeoDistrict;
import com.logibeat.commons.geography.district.GeoDistrictCollection;
import com.logibeat.commons.geography.district.GeoDistrictLevel;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alex on 10/03/2017.
 */
public class GeoUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(GeoUtilsTest.class);

    public static List subtract(final List list1, final List list2) {
        final ArrayList result = new ArrayList(list1);
        final Iterator iterator = list2.iterator();

        while (iterator.hasNext()) {
            result.remove(iterator.next());
        }

        return result;
    }

    @Test
    public void testBuildGeoDistrictCollection1() throws Exception {
        GeoDistrictCollection collection = GeoUtils.buildGeoDistrictCollection();
        Assert.assertNotNull(collection);
        Assert.assertEquals("中国", collection.getShortNameByAdcode("100000"));
        Assert.assertEquals("浙江省", collection.getNameByAdcode("330000"));
        Assert.assertEquals("浙江省,嘉兴市,南湖区", collection.getMergerNameByAdcode("330402"));
        Assert.assertEquals("安徽,蚌埠,淮上", collection.getMergerShortNameByAdcode("340311"));
    }

    @Test
    public void testDataIntegrationOfCity() throws Exception {

        GeoDistrictCollection districtCollection = GeoUtils.buildGeoDistrictCollection();
        GeoDistrictBoundariesCollection boundariesCollection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip");
        List<String> adcodeList1 = districtCollection.allAdcode(GeoDistrictLevel.CITY);
        List<String> adcodeList2 = boundariesCollection.allAdcode();
        _testDataIntegration(adcodeList1, adcodeList2);
    }

    @Test
    public void testDataIntegrationOfDistrict() throws Exception {

        GeoDistrictCollection districtCollection = GeoUtils.buildGeoDistrictCollection();
        GeoDistrictBoundariesCollection boundariesCollection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip");
        List<String> adcodeList1 = districtCollection.allAdcode(GeoDistrictLevel.DISTRICT);
        List<String> adcodeList2 = boundariesCollection.allAdcode();
        _testDataIntegration(adcodeList1, adcodeList2);
    }

    private void _testDataIntegration(final List<String> adcodeList1, List<String> adcodeList2) throws Exception {
        List<String> diff1 = subtract(adcodeList1, adcodeList2);
        List<String> diff2 = subtract(adcodeList2, adcodeList1);
        if (!diff1.isEmpty() || !diff2.isEmpty()) {
            logger.error("diff data is not empty:\n" +
                            "--> GeoDistrictCollection subtract GeoDistrictBoundariesCollection - size({}): {}\n" +
                            "--> GeoDistrictBoundariesCollection subtract eoDistrictCollection - size({}): {}\n" +
                            "::: GeoDistrictCollection - size({}): {}\n" +
                            "::: GeoDistrictBoundariesCollection - size({}): {}",
                    diff1.size(),
                    diff1,
                    diff2.size(),
                    diff2,
                    adcodeList1.size(),
                    adcodeList1,
                    adcodeList2.size(),
                    adcodeList2);
        }
        Assert.assertTrue(diff1.isEmpty());
        Assert.assertTrue(diff2.isEmpty());
    }

    @Test
    public void testContains1() throws Exception {
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

    @Test
    public void testContains2() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip");
        GeoDistrictLevel level = GeoDistrictLevel.CITY;
        _testContains(collection, level);
    }

    @Test
    public void testContains3() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip");
        GeoDistrictLevel level = GeoDistrictLevel.DISTRICT;
        _testContains(collection, level);
    }

    private void _testContains(GeoDistrictBoundariesCollection collection, GeoDistrictLevel level) {
        assertEqualsAdcode(118.616411, 29.269244, collection, level, "330100", "330127");
        assertEqualsAdcode(119.104013, 30.314349, collection, level, "330100", "330185");
        assertEqualsAdcode(118.963094, 30.340284, collection, level, "330100", "330185");
        assertEqualsAdcode(119.685388, 30.433258, collection, level, "330500", "330523");
        assertEqualsAdcode(120.153576, 30.287459, collection, level, "330100", "330103");
        assertEqualsAdcode(120.672111, 28.000575, collection, level, "330300", "330302");
        assertEqualsAdcode(120.089836, 28.694312, collection, level, "331100", "331122");
        assertEqualsAdcode(120.040398, 28.937366, collection, level, "330700", "330784");
        assertEqualsAdcode(116.405285, 39.904989, collection, level, "110100", "110101");
        assertEqualsAdcode(119.74926, 25.484304, collection, level, "350100", "350128");
        assertEqualsAdcode(114.475822, 16.040655, collection, level, "460300", "460323");
        assertEqualsAdcode(75.781975, 39.270635, collection, level, "653100", "653121");
        assertEqualsAdcode(114.714975, 30.418113, collection, level, "420700", "420703");
        assertEqualsAdcode(117.414897, 34.522166, collection, level, "320300", "320305");
        assertEqualsAdcode(123.24562, 41.807205, collection, level, "210100", "210114");
        assertEqualsAdcode(113.620451,22.859132, collection, level, "441900", "441900"); // no level 3
        assertEqualsAdcode(109.197765, 21.656216, collection, level, "450500", "450521");
    }

    private void assertEqualsAdcode(double x, double y, GeoDistrictBoundariesCollection collection, GeoDistrictLevel level, String adcode2, String adcode3) {
        String adcode = GeoDistrictLevel.CITY.equals(level)
                ? adcode2
                : (GeoDistrictLevel.DISTRICT.equals(level) ? adcode3 : "xxxxxx");
        Assert.assertEquals(adcode, collection.whichContains(x, y));
    }
}