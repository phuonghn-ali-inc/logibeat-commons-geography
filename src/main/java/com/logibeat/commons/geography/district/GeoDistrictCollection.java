package com.logibeat.commons.geography.district;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alex on 09/03/2017.
 */
public class GeoDistrictCollection {

    List<GeoDistrict> geoDistrictArray;

    Map<String, GeoDistrict> geoDistrictMap;
    
    Map<String, GeoDistrict> parseAdcodeMap;
    
    private static final Integer LEVEL = 2; 

    private Logger logger = LoggerFactory.getLogger(GeoDistrictCollection.class);

    public GeoDistrictCollection(List<GeoDistrict> geoDistrictArray) {
        this.geoDistrictArray = geoDistrictArray;
    }

    public GeoDistrict byAdcode(String adcode) {
        return _byAdcode(adcode, true);
    }

    private GeoDistrict _byAdcode(String adcode, boolean clone) {
        loadMap();
        GeoDistrict geoDistrict = geoDistrictMap.get(adcode);
        GeoDistrict result = (clone && geoDistrict != null)
                ? ObjectUtils.clone(geoDistrict)
                : geoDistrict;
        if (result == null) {
            logger.debug("GeoDistrict is null.");
        }
        return result;
    }

    private synchronized void loadMap() {
        if (geoDistrictMap != null) {
            return;
        }
        geoDistrictMap = new HashMap<>();
        geoDistrictArray.forEach(geoDistrict -> geoDistrictMap.put(geoDistrict.getAdcode(), geoDistrict));
    }
    
    private synchronized void loadNameMap() {
        if (parseAdcodeMap != null) {
            return;
        }
        parseAdcodeMap = new HashMap<>();
        geoDistrictArray.forEach(geoDistrict -> {
        	parseAdcodeMap.put(geoDistrict.getMergerName(), geoDistrict);
        	if(LEVEL==geoDistrict.getLevelInt()){
        		parseAdcodeMap.put(geoDistrict.getName(), geoDistrict);
        	}
        	
        });
    }
    
    public String parseAdcodeFromMergerName(String address){
    	loadNameMap();
    	GeoDistrict geoDistrict = parseAdcodeMap.get(address);
    	return (geoDistrict == null) ? null : geoDistrict.getAdcode();
    }
    
    public String getNameByAdcode(String adcode) {
        GeoDistrict geoDistrict = _byAdcode(adcode, false);
        return (geoDistrict == null) ? null : geoDistrict.getName();
    }

    public String getShortNameByAdcode(String adcode) {
        GeoDistrict geoDistrict = _byAdcode(adcode, false);
        return (geoDistrict == null) ? null : geoDistrict.getShortName();
    }

    public String getMergerNameByAdcode(String adcode) {
        GeoDistrict geoDistrict = _byAdcode(adcode, false);
        return (geoDistrict == null) ? null : geoDistrict.getMergerName();
    }

    public String getMergerShortNameByAdcode(String adcode) {
        GeoDistrict geoDistrict = _byAdcode(adcode, false);
        return (geoDistrict == null) ? null : geoDistrict.getMergerShortName();
    }

    public List<String> allAdcode() {
        return allAdcode(null);
    }

    public List<String> allAdcode(GeoDistrictLevel level) {
        List<String> list = geoDistrictArray.stream().filter(geoDistrict -> level == null || geoDistrict.getLevelInt() == level.getValue()).map(GeoDistrict::getAdcode).collect(Collectors.toList());
        return Collections.unmodifiableList(list);
    }
}
