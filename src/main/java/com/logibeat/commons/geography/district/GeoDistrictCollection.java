package com.logibeat.commons.geography.district;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
