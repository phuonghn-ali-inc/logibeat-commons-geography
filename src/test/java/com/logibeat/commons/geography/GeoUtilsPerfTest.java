package com.logibeat.commons.geography;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoUtilsPerfTest {

    public static final int TIMES_IN_TASK = 10 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(GeoUtilsPerfTest.class);
    private static List<Future> futures = new ArrayList<Future>();
    private final int TASK_SIZE = 8 * 8;

    @AfterClass
    public static void tearDownClass() throws Exception {
        // pool.awaitTermination(1, TimeUnit.HOURS);
        for (Future<String> future : futures) {
//            Long t = future.get(1, TimeUnit.HOURS);
            String t = future.get();
            logger.info(t);
        }
    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @Test
    public void containsGeoDistrictChinaDisctrict1() throws Exception {
        final GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.AlgorithmPolicy.JDK);
        _task(geoDistricts, GeoPolygon.AlgorithmPolicy.JDK, 3);
    }

    //    @Test
    public void containsGeoDistrictChinaDisctrict2() throws Exception {
        final GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.AlgorithmPolicy.JDK_GENERAL_PATH);
        _task(geoDistricts, GeoPolygon.AlgorithmPolicy.JDK_GENERAL_PATH, 3);
    }

    @Test
    public void containsGeoDistrictChinaDisctrict3() throws Exception {
        final GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-3-json.zip", GeoPolygon.AlgorithmPolicy.SROMKU);
        _task(geoDistricts, GeoPolygon.AlgorithmPolicy.SROMKU, 3);
    }

    private void _task(final GeoDistrict[] geoDistricts, final GeoPolygon.AlgorithmPolicy ap, final int level) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(TASK_SIZE);
        for (int i = 0; i < TASK_SIZE; i++) {
            Callable<String> callable = new Callable<String>() {
                public String call() throws Exception {
                    long t1 = System.currentTimeMillis();
                    for (int i = 0; i < TIMES_IN_TASK; i++) {
                        _containsGeoDistrictChinaDisctricts(geoDistricts, level);
                    }
                    long t2 = System.currentTimeMillis();
                    return "" + (t2 - t1) + "ms, " + ap.toString() + ", level=" + level + ", [" + t1 + "-" + t2 + "]";
                }
            };
            futures.add(pool.submit(callable));
        }
    }

    private void _containsGeoDistrictChinaDisctricts(GeoDistrict[] geoDistricts, int level) {
        assertEqualsAdcode("330127", "330100", level, geoDistricts, 118.616411, 29.269244);
        assertEqualsAdcode("330185", "330100", level, geoDistricts, 119.104013, 30.314349);
        assertEqualsAdcode("330185", "330100", level, geoDistricts, 118.963094, 30.340284);
        assertEqualsAdcode("330523", "330500", level, geoDistricts, 119.685388, 30.433258);
        assertEqualsAdcode("330103", "330100", level, geoDistricts, 120.153576, 30.287459);
        assertEqualsAdcode("330302", "330300", level, geoDistricts, 120.672111, 28.000575);
        assertEqualsAdcode("331122", "331100", level, geoDistricts, 120.089836, 28.694312);
        assertEqualsAdcode("330784", "330700", level, geoDistricts, 120.040398, 28.937366);
        assertEqualsAdcode("110101", "110100", level, geoDistricts, 116.405285, 39.904989);
        assertEqualsAdcode("350128", "350100", level, geoDistricts, 119.74926, 25.484304);
        assertEqualsAdcode("460323", "460300", level, geoDistricts, 114.475822, 16.040655);
        assertEqualsAdcode("653121", "653100", level, geoDistricts, 75.781975, 39.270635);
    }

    private void assertEqualsAdcode(String adcode3, String adcode2, int level, GeoDistrict[] geoDistricts, double x, double y) {
//        Assert.assertEquals(calcAdcode(adcode, level), GeoUtils.containsInAdcode(geoDistricts, x, y));
        String adcode = level == 2 ? adcode2 : (level == 3 ? adcode3 : "xxxxxx");
        Assert.assertEquals(adcode, GeoUtils.containsInAdcode(geoDistricts, x, y));
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
    public void containsGeoDistrictChinaCities1() throws Exception {
        GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.AlgorithmPolicy.JDK);
        _task(geoDistricts, GeoPolygon.AlgorithmPolicy.JDK, 2);
    }


    //    @Test
    public void containsGeoDistrictChinaCities2() throws Exception {
        GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.AlgorithmPolicy.JDK_GENERAL_PATH);
        _task(geoDistricts, GeoPolygon.AlgorithmPolicy.JDK_GENERAL_PATH, 2);
    }

    @Test
    public void containsGeoDistrictChinaCities3() throws Exception {
        GeoDistrict[] geoDistricts = GeoUtils.buildGeoDistricts(
                "src/test/resources/data/level/boundaries-level-2-json.zip", GeoPolygon.AlgorithmPolicy.SROMKU);
        _task(geoDistricts, GeoPolygon.AlgorithmPolicy.SROMKU, 2);
    }

}