package com.logibeat.commons.geography.boundary;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logibeat.commons.geography.geohash.GeoHashUtil;
import com.logibeat.commons.geography.polygon.GeoBounds;
import com.logibeat.commons.geography.utils.GeoUtils;

public class GeoDistrictBoundariesHash {
	 Map<String,List<GeoDistrictBoundaries>> geoDistrictBoundariesHash;

	public Map<String, List<GeoDistrictBoundaries>> getGeoDistrictBoundariesHash() {
		return geoDistrictBoundariesHash;
	}

	public  String getAdcode(double x, double y) {
		String geoHash = GeoHashUtil.encode(x, y);
		List<GeoDistrictBoundaries> list = geoDistrictBoundariesHash.get(geoHash);
		if(list.size() == 1){
			return list.get(0).getAdcode();
		}
		for (GeoDistrictBoundaries geoDistrictBoundaries : list) {
			if (geoDistrictBoundaries.contains(x, y)) {
                return geoDistrictBoundaries.getAdcode();
            }
		}
		return null;	
	}

	
	public Map<String, List<GeoDistrictBoundaries>> setGeoDistrictBoundariesHash(GeoDistrictBoundariesCollection geoDistrictBoundariesCollection) {
		List<GeoDistrictBoundaries> geoList = geoDistrictBoundariesCollection.getGeoDistrictBoundariesArray();
		for (GeoDistrictBoundaries geoDistrictBoundaries : geoList) {
			GeoBounds geoBounds = geoDistrictBoundaries.getGeoBounds();
			List<String>  geohashes= GeoHashUtil.getGeohash(geoBounds);
			for (String geohash : geohashes) {
				geoDistrictBoundariesHash =  new HashMap<>();
				List<GeoDistrictBoundaries> list =geoDistrictBoundariesHash.get(geohash) ;
				if(list == null){
					List<GeoDistrictBoundaries> firstlist = new ArrayList<>() ;
					firstlist.add(geoDistrictBoundaries);
					geoDistrictBoundariesHash.put(geohash, firstlist);
				}
				if(list != null && !list.contains(geoDistrictBoundaries)){
				    list.add(geoDistrictBoundaries);
				}
			}
		}
		return geoDistrictBoundariesHash;
	}
	
	public static void main(String[] args) throws Exception {
		GeoDistrictBoundariesHash geoDistrictBoundariesHash = GeoUtils.buildGeoDistrictBoundariesHash(
	                "src/test/resources/data/level/boundaries-level-2-json.zip");
	        String adcode = geoDistrictBoundariesHash.getAdcode(118.616411, 29.269244);
	        System.err.println(adcode);
	      
	}
}
