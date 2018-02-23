package com.logibeat.commons.geography.boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.logibeat.commons.geography.district.GeoDistrictLevel;
import com.logibeat.commons.geography.geohash.GeoHashUtil;
import com.logibeat.commons.geography.polygon.GeoBounds;
import com.logibeat.commons.geography.polygon.GeoPolygon;
import com.logibeat.commons.geography.utils.GeoUtils;

public class GeoDistrictBoundariesHash {
	Map<String, List<GeoDistrictBoundaries>> geoDistrictBoundariesHash;

	GeoDistrictBoundariesCollection geoDistrictBoundariesCollection;

	public Map<String, List<GeoDistrictBoundaries>> getGeoDistrictBoundariesHash() {
		return geoDistrictBoundariesHash;
	}

	
	public GeoDistrictBoundariesCollection getGeoDistrictBoundariesCollection() {
		return geoDistrictBoundariesCollection;
	}


	public void setGeoDistrictBoundariesCollection(GeoDistrictBoundariesCollection geoDistrictBoundariesCollection) {
		this.geoDistrictBoundariesCollection = geoDistrictBoundariesCollection;
	}


	public String getAdcode(double x, double y) {
		String geoHash = GeoHashUtil.encode(x, y);
		List<GeoDistrictBoundaries> list = geoDistrictBoundariesHash.get(geoHash);
		if (list == null) {
			return geoDistrictBoundariesCollection.whichContains(x, y);
		}
		if (list.size() == 1) {
			return list.get(0).getAdcode();
		}
		for (GeoDistrictBoundaries geoDistrictBoundaries : list) {
			if (geoDistrictBoundaries.contains(x, y)) {
				return geoDistrictBoundaries.getAdcode();
			}
		}
		return geoDistrictBoundariesCollection.whichContains(x, y);
	}

	public Map<String, List<GeoDistrictBoundaries>> setGeoDistrictBoundariesHash(GeoDistrictBoundariesCollection geoDistrictBoundariesCollection) {
		this.geoDistrictBoundariesCollection = geoDistrictBoundariesCollection;
		List<GeoDistrictBoundaries> geoList = geoDistrictBoundariesCollection.getGeoDistrictBoundariesArray();
		geoDistrictBoundariesHash = new HashMap<>();
		for (GeoDistrictBoundaries geoDistrictBoundaries : geoList) {
			GeoBounds geoBounds = geoDistrictBoundaries.getGeoBounds();
			List<String> geohashes = GeoHashUtil.getGeohash(geoBounds);
			for (String geohash : geohashes) {
				List<GeoDistrictBoundaries> list = geoDistrictBoundariesHash.get(geohash);
				if (list == null) {
					List<GeoDistrictBoundaries> firstlist = new ArrayList<>();
					firstlist.add(geoDistrictBoundaries);
					geoDistrictBoundariesHash.put(geohash, firstlist);
				}
				if (list != null && !list.contains(geoDistrictBoundaries)) {
					list.add(geoDistrictBoundaries);
					geoDistrictBoundariesHash.put(geohash, list);
				}
			}
		}
		return geoDistrictBoundariesHash;
	}

	public static void main(String[] args) throws Exception {
		long t1 = System.currentTimeMillis();
		GeoDistrictBoundariesHash geoDistrictBoundariesHash = GeoUtils.buildGeoDistrictBoundariesHash("src/test/resources/data/level/boundaries-level-2-json.zip");
		// "src/test/resources/data/level/test.zip");
		long t2 = System.currentTimeMillis();
		test(119.104013, 30.314349,geoDistrictBoundariesHash);
		test(118.616411, 29.269244,geoDistrictBoundariesHash);
		test(118.963094, 30.340284,geoDistrictBoundariesHash);
		test(119.685388, 30.433258,geoDistrictBoundariesHash);
		test(120.153576, 30.287459,geoDistrictBoundariesHash);
		test(114.475822, 16.040655,geoDistrictBoundariesHash);
		test(75.781975, 39.270635,geoDistrictBoundariesHash);
		test(116.405285, 39.904989,geoDistrictBoundariesHash);

	}

	private static void test(double lat, double lng,GeoDistrictBoundariesHash geoDistrictBoundariesHash) throws IOException {
		long t2 = System.currentTimeMillis();
		String adcode = geoDistrictBoundariesHash.getAdcode(lat, lng);
		long t3 = System.currentTimeMillis();
		System.err.println("adcode: "+ (t3 - t2));
		String old = geoDistrictBoundariesHash.geoDistrictBoundariesCollection.whichContains(lat, lng);
		long t4 = System.currentTimeMillis();
		System.err.println("old: "+ (t4 - t3));
		System.err.println(adcode+"-----------"+old);
		Assert.assertEquals(adcode, old);
	}
	
	  @Test
	  public void hash_test() throws Exception {
		    long t1 = System.currentTimeMillis();
			GeoDistrictBoundariesHash geoDistrictBoundariesHash = GeoUtils.buildGeoDistrictBoundariesHash("src/test/resources/data/level/boundaries-level-2-json.zip");
			// "src/test/resources/data/level/test.zip");
			long t2 = System.currentTimeMillis();
			test(118.616411, 29.269244,geoDistrictBoundariesHash);
			test(119.104013, 30.314349,geoDistrictBoundariesHash);
			test(118.963094, 30.340284,geoDistrictBoundariesHash);
			test(119.685388, 30.433258,geoDistrictBoundariesHash);
			test(120.153576, 30.287459,geoDistrictBoundariesHash);
			test(114.475822, 16.040655,geoDistrictBoundariesHash);
			test(75.781975, 39.270635,geoDistrictBoundariesHash);
			test(116.405285, 39.904989,geoDistrictBoundariesHash);
	 }
}
