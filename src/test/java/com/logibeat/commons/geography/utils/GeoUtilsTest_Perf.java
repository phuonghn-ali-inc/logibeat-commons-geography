package com.logibeat.commons.geography.utils;

import com.logibeat.commons.geography.GeoPoint;
import com.logibeat.commons.geography.boundary.GeoDistrictBoundariesCollection;
import com.logibeat.commons.geography.district.GeoDistrictLevel;
import com.logibeat.commons.geography.polygon.GeoPolygon;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoUtilsTest_Perf {

    public static final int TIMES_IN_TASK = 1 * 1;
    //    public static final int TIMES_IN_TASK = 10 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(GeoUtilsTest_Perf.class);
    private static List<Future> futures = new ArrayList<Future>();
    //    private final int TASK_SIZE = 2 * 8;
    private final int TASK_SIZE = 1 * 1;

    @AfterClass
    public static void tearDownClass() throws Exception {
        // pool.awaitTermination(1, TimeUnit.HOURS);
        Map<String, Long> map = new HashMap<String, Long>();
        for (Future<List<Object>> future : futures) {
            List<Object> result = future.get();
            String key = (String) result.get(0);
            Long value = (Long) result.get(1);
            Long newValue = value;
            Long oldValue = map.get(key);
            if (oldValue != null) {
                newValue += oldValue;
            }
            map.put(key, newValue);
        }
        for (Map.Entry entry :
                map.entrySet()) {
            logger.info("{}: {}ms", entry.getKey(), entry.getValue());
        }
    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @Test
    public void containsLevel3_jdk_1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip");
        _task(collection, GeoPolygon.PipAlgorithm.JDK, GeoDistrictLevel.DISTRICT);
    }

    @Test
    public void containsLevel3_jdk_2() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.PipAlgorithm.JDK);
        _task(collection, GeoPolygon.PipAlgorithm.JDK, GeoDistrictLevel.DISTRICT);
    }

    //    @Test
    public void containsLevel3_gpath_1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH);
        _task(collection, GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH, GeoDistrictLevel.DISTRICT);
    }

    //    @Test
    public void containsLevel3_gpath_2() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH);
        _task(collection, GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH, GeoDistrictLevel.DISTRICT);
    }

    private void _task(final GeoDistrictBoundariesCollection collection, final GeoPolygon.PipAlgorithm ap, final GeoDistrictLevel level) throws Exception {
        //   final String tag;
        ExecutorService pool = Executors.newFixedThreadPool(TASK_SIZE);
        for (int i = 0; i < TASK_SIZE; i++) {
            Callable callable = new Callable<List>() {
                public List call() throws Exception {
                    long t1 = System.currentTimeMillis();
                    for (int i = 0; i < TIMES_IN_TASK; i++) {
                        try {
                            _containsGeoDistrictChinaDisctricts(collection, level);
                        } catch (Exception e) {
                            logger.error("{}, level={}",
                                    ap.toString(), level, e);
                        }
                    }
                    long t2 = System.currentTimeMillis();
                    logger.info("{}ms, {}, level={}, time=[{}-{}]",
                            (t2 - t1), ap.toString(), level, t1, t2);
                    List list = new ArrayList<Object>();
                    list.add("{level=" + level + "} - " + ap);
                    list.add(t2 - t1);
                    return list;
                }
            };
            futures.add(pool.submit(callable));
        }
    }

    @Test
    public void containsLevel3_sromku_1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.PipAlgorithm.SROMKU);
        _task(collection, GeoPolygon.PipAlgorithm.SROMKU, GeoDistrictLevel.DISTRICT);
    }

    @Test
    public void containsLevel3_sromku_2() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.PipAlgorithm.SROMKU);
        _task(collection, GeoPolygon.PipAlgorithm.SROMKU, GeoDistrictLevel.DISTRICT);
    }

    private void _containsGeoDistrictChinaDisctricts(final GeoDistrictBoundariesCollection collection, final GeoDistrictLevel level) {
        assertEqualsAdcode("330127", "330100", level, collection, 118.616411, 29.269244);
        assertEqualsAdcode("330185", "330100", level, collection, 119.104013, 30.314349);
        assertEqualsAdcode("330185", "330100", level, collection, 118.963094, 30.340284);
        assertEqualsAdcode("330523", "330500", level, collection, 119.685388, 30.433258);
        assertEqualsAdcode("330103", "330100", level, collection, 120.153576, 30.287459);
        assertEqualsAdcode("330302", "330300", level, collection, 120.672111, 28.000575);
        assertEqualsAdcode("331122", "331100", level, collection, 120.089836, 28.694312);
        assertEqualsAdcode("330784", "330700", level, collection, 120.040398, 28.937366);
        assertEqualsAdcode("110101", "110100", level, collection, 116.405285, 39.904989);
        assertEqualsAdcode("350128", "350100", level, collection, 119.74926, 25.484304);
        assertEqualsAdcode("460323", "460300", level, collection, 114.475822, 16.040655);
        assertEqualsAdcode("653121", "653100", level, collection, 75.781975, 39.270635);
    }

    private void assertEqualsAdcode(String adcode3, String adcode2, GeoDistrictLevel level, GeoDistrictBoundariesCollection collection, double x, double y) {
//        Assert.assertEquals(calcAdcode(adcode, level), collection.whichContains( x, y));
        String adcode = GeoDistrictLevel.CITY.equals(level)
                ? adcode2
                : (GeoDistrictLevel.DISTRICT.equals(level) ? adcode3 : "xxxxxx");
        Assert.assertEquals(adcode, collection.whichContains(x, y));
    }

    private String calcAdcode(String adcode, int lelel) {
        if (lelel == 3) {
            return adcode;
        } else if (lelel == 2) {
            return StringUtils.left(adcode, 4) + "00";
        } else if (lelel == 1) {
            return StringUtils.left(adcode, 2) + "0000";
        } else {
            return "100000";
        }
    }

    private GeoPoint makeGeoPointRandom(double x, double y) {
        return new GeoPoint(x + RandomUtils.nextDouble(0.000001, 0.000999),
                y + RandomUtils.nextDouble(0.000001, 0.000999));
    }

    @Test
    public void containsLevel2_jdk_1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip");
        _task(collection, GeoPolygon.PipAlgorithm.JDK, GeoDistrictLevel.CITY);
    }

    @Test
    public void containsLevel2_jdk_2() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.PipAlgorithm.JDK);
        _task(collection, GeoPolygon.PipAlgorithm.JDK, GeoDistrictLevel.CITY);
    }

    //    @Test
    public void containsLevel2_gpath_1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH);
        _task(collection, GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH, GeoDistrictLevel.CITY);
    }

    //    @Test
    public void containsLevel2_gpath_2() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH);
        _task(collection, GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH, GeoDistrictLevel.CITY);
    }

    @Test
    public void containsLevel2_sromku_1() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.PipAlgorithm.SROMKU);
        _task(collection, GeoPolygon.PipAlgorithm.SROMKU, GeoDistrictLevel.CITY);
    }

    @Test
    public void containsLevel2_sromku_2() throws Exception {
        GeoDistrictBoundariesCollection collection = GeoUtils.buildGeoDistrictBoundariesCollection(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.PipAlgorithm.SROMKU);
        _task(collection, GeoPolygon.PipAlgorithm.SROMKU, GeoDistrictLevel.CITY);
    }

}