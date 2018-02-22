package com.logibeat.commons.geography.boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logibeat.commons.geography.geohash.GeoHashUtil;
import com.logibeat.commons.geography.polygon.GeoBounds;
import com.logibeat.commons.geography.utils.GeoUtils;

public class GeoDistrictBoundariesHash {
	Map<String, List<GeoDistrictBoundaries>> geoDistrictBoundariesHash;

	GeoDistrictBoundariesCollection geoDistrictBoundariesCollection;

	public Map<String, List<GeoDistrictBoundaries>> getGeoDistrictBoundariesHash() {
		return geoDistrictBoundariesHash;
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
		return null;
	}

	public Map<String, List<GeoDistrictBoundaries>> setGeoDistrictBoundariesHash(
			GeoDistrictBoundariesCollection geoDistrictBoundariesCollection) {
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
		GeoDistrictBoundariesHash geoDistrictBoundariesHash = GeoUtils
				.buildGeoDistrictBoundariesHash("src/test/resources/data/level/boundaries-level-2-json.zip");
		// "src/test/resources/data/level/test.zip");
		long t2 = System.currentTimeMillis();
		String adcode = geoDistrictBoundariesHash.getAdcode(118.616411, 29.269244);
		long t3 = System.currentTimeMillis();
		System.err.println(t2 - t1);
		System.err.println(t3 - t2);
		System.err.println(adcode);

	}
}
