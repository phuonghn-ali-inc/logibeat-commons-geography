package com.logibeat.commons.geography.district;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by alex on 09/03/2017.
 */
public class GeoDistrictCollection {

    List<GeoDistrict> geoDistrictArray;

    Map<String, GeoDistrict> geoDistrictMap;

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
        return (clone && geoDistrict != null)
                // ? (GeoDistrict) (BeanUtils.cloneBean(geoDistrict)) // java.lang.NoClassDefFoundError: org/apache/commons/logging/LogFactory
                ? ObjectUtils.clone(geoDistrict)
                : geoDistrict;
    }

    private synchronized void loadMap() {
        if (geoDistrictMap != null) {
            return;
        }
        geoDistrictMap = new HashMap<>();
        geoDistrictArray.forEach(geoDistrict -> geoDistrictMap.put(geoDistrict.getAdcode(), geoDistrict));
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

    public Map<String, String> tree() {
        Map<String, String> map = new HashMap<>();
        geoDistrictArray.forEach(geoDistrict -> map.put(geoDistrict.getAdcode(), geoDistrict.getParentAdcode()));
        return Collections.unmodifiableMap(map);
    }

    public List<String> all() {
        List<String> list = new ArrayList<>();
        geoDistrictArray.forEach(geoDistrict -> list.add(geoDistrict.getAdcode()));
        return Collections.unmodifiableList(list);
    }
}
