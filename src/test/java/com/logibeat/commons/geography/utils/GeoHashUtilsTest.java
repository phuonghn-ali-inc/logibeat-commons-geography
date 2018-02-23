package com.logibeat.commons.geography.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logibeat.commons.geography.boundary.GeoDistrictBoundariesHash;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoHashUtilsTest {

    public static final int TIMES_IN_TASK = 1 * 1000;
    //    public static final int TIMES_IN_TASK = 10 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(GeoHashUtilsTest.class);
    private static List<Future> futures = new ArrayList<Future>();
    //    private final int TASK_SIZE = 2 * 8;
    private final int TASK_SIZE = 2 * 8;

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

    
  

    private void _task(final GeoDistrictBoundariesHash geoDistrictBoundariesHash) throws Exception {
        //   final String tag;
        ExecutorService pool = Executors.newFixedThreadPool(TASK_SIZE);
        for (int i = 0; i < TASK_SIZE; i++) {
            Callable callable = new Callable<List>() {
                public List call() throws Exception {
                    long t1 = System.currentTimeMillis();
                    for (int i = 0; i < TIMES_IN_TASK; i++) {
                        try {
                        	hash_test(geoDistrictBoundariesHash);
                        } catch (Exception e) {
                            logger.error("失败"+e);
                        }
                    }
                    long t2 = System.currentTimeMillis();
                    logger.info("{}ms,  time=[{}-{}]",
                            (t2 - t1), t1, t2);
                    List list = new ArrayList<Object>();
                    list.add(t2 - t1);
                    return list;
                }
            };
            futures.add(pool.submit(callable));
        }
    }


	private static void test(double lat, double lng,GeoDistrictBoundariesHash geoDistrictBoundariesHash) throws IOException {
//		long t2 = System.currentTimeMillis();
		String adcode = geoDistrictBoundariesHash.getAdcode(lat, lng);
		logger.info("adcode : {} ", adcode);
//		long t3 = System.currentTimeMillis();
//		System.err.println("adcode: "+ (t3 - t2));
//		String old = geoDistrictBoundariesHash.getGeoDistrictBoundariesCollection().whichContains(lat, lng);
//		long t4 = System.currentTimeMillis();
//		System.err.println("old: "+ (t4 - t3));
//		System.err.println(adcode+"-----------"+old);
//		Assert.assertEquals(adcode, old);
	}
	
	  
	
	  public void hash_test(GeoDistrictBoundariesHash geoDistrictBoundariesHash) throws Exception {
			test(118.616411, 29.269244,geoDistrictBoundariesHash);
			test(119.104013, 30.314349,geoDistrictBoundariesHash);
			test(118.963094, 30.340284,geoDistrictBoundariesHash);
			test(119.685388, 30.433258,geoDistrictBoundariesHash);
			test(120.153576, 30.287459,geoDistrictBoundariesHash);
			test(114.475822, 16.040655,geoDistrictBoundariesHash);
			test(75.781975, 39.270635,geoDistrictBoundariesHash);
			test(116.405285, 39.904989,geoDistrictBoundariesHash);
	 }
	  
	  @Test
	  public void hash() throws Exception{
		    long t1 = System.currentTimeMillis();
			GeoDistrictBoundariesHash geoDistrictBoundariesHash = GeoUtils.buildGeoDistrictBoundariesHash("src/test/resources/data/level/boundaries-level-2-json.zip");
			// "src/test/resources/data/level/test.zip");
			long t2 = System.currentTimeMillis();
			_task(geoDistrictBoundariesHash);
	  }
   
}