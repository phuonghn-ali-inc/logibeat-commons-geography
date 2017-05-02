package com.logibeat.commons.geography.boundary;

import com.logibeat.commons.geography.GeoPoint;
import com.logibeat.commons.geography.district.GeoDistrict;
import com.logibeat.commons.geography.district.GeoDistrictCollection;
import com.logibeat.commons.geography.polygon.GeoBounds;
import com.logibeat.commons.geography.utils.GeoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * bounadaries of a district consist of one or more <code>GeoPolygon</code>
 * Created by alex on 24/02/2017.
 */
public class GeoDistrictBoundariesCollection {

    List<GeoDistrictBoundaries> geoDistrictBoundariesArray;
    Map<String, GeoDistrictBoundaries> adcodeBoundariesMap;
    List<Map<String, GeoBounds>> parentAdcodeBoundsMapArray;
    Map<String, List<String>> parentAdcodeChildrenMap;
    private Logger logger = LoggerFactory.getLogger(GeoDistrictBoundariesCollection.class);

    private boolean optimizedWay = false;

    public List<String> allAdcode() {
        List<String> list = new ArrayList<>();
        geoDistrictBoundariesArray.forEach(geoDistrictBoundaries -> list.add(geoDistrictBoundaries.getAdcode()));
        return Collections.unmodifiableList(list);
    }

    /**
     * @param x
     * @param y
     * @return adcode
     */
    public String whichContains(double x, double y) {
        String result;
        if (!optimizedWay) {
            result = _whichContains1(x, y);
        } else {
            result = _whichContains2(x, y);
        }
        logger.debug("adcode: {}, optimized: {}", result, optimizedWay);
        return result;
    }

    private String _whichContains1(double x, double y) {
        for (GeoDistrictBoundaries geoDistrictBoundaries : geoDistrictBoundariesArray) {
            if (geoDistrictBoundaries.contains(x, y)) {
                return geoDistrictBoundaries.getAdcode();
            }
        }
        return null;
    }

    private String _whichContains2(double x, double y) {
        List<String> maybeArray = new ArrayList<>();
        maybeArray.addAll(parentAdcodeBoundsMapArray.get(0).keySet());
        for (Map<String, GeoBounds> parentAdcodeBoundsMap :
                parentAdcodeBoundsMapArray) {
            List<String> childrenOfCurrent = new ArrayList<>();
            for (String adcode :
                    maybeArray) {
                if (parentAdcodeBoundsMap.get(adcode).contains(x, y)) {
                    childrenOfCurrent.addAll(parentAdcodeChildrenMap.get(adcode));
                }
            }
            maybeArray = childrenOfCurrent;
        }
        for (String adcode : maybeArray) {
            if (adcodeBoundariesMap.get(adcode).contains(x, y)) {
                return adcode;
            }
        }
        return null;
    }

    /**
     * @param geoPoint
     * @return adcode
     */
    public String whichContains(GeoPoint geoPoint) {
        return whichContains(geoPoint.getX(), geoPoint.getY());
    }

    public void setGeoDistrictBoundariesArray(List<GeoDistrictBoundaries> geoDistrictBoundariesArray) {
        this.geoDistrictBoundariesArray = geoDistrictBoundariesArray;
    }

    public synchronized boolean optimize(GeoDistrictCollection tree) {
        try {
            _optimize(tree);
            // set flag
            optimizedWay = true;
        } catch (Exception e) {
            logger.error("optimizing failure.", e);
        }
        return optimizedWay;
    }

    public void _optimize(GeoDistrictCollection tree) {
        adcodeBoundariesMap = new HashMap<>();
        geoDistrictBoundariesArray.forEach(geoDistrictBoundaries -> {
            String adcode = geoDistrictBoundaries.getAdcode();
            adcodeBoundariesMap.put(adcode, geoDistrictBoundaries);
        });

        parentAdcodeBoundsMapArray = new ArrayList<>();
        parentAdcodeChildrenMap = new HashMap<>();
        while (true) {
            Map<String, GeoBounds> adcodeBoundsMap;
            if (parentAdcodeBoundsMapArray.isEmpty()) {
                adcodeBoundsMap = new HashMap<>();
                geoDistrictBoundariesArray.forEach(geoDistrictBoundaries -> {
                    adcodeBoundsMap.put(geoDistrictBoundaries.getAdcode(), geoDistrictBoundaries.getGeoBounds());
                });
            } else {
                adcodeBoundsMap = parentAdcodeBoundsMapArray.get(0);
            }
            Map<String, GeoBounds> parentAdcodeBoundsMap = new HashMap<>();
            calcParentMap(tree, adcodeBoundsMap, parentAdcodeChildrenMap, parentAdcodeBoundsMap);
            if (parentAdcodeBoundsMap.isEmpty()) { // no parent
                break;
            }
            parentAdcodeBoundsMapArray.add(0, parentAdcodeBoundsMap);
            if (parentAdcodeBoundsMap.size() <= 1) { // the parent of one element will not more than one
                break;
            }
        }
        if (parentAdcodeBoundsMapArray.isEmpty()) {
            throw new IllegalArgumentException("not valid tree.");
        }
    }

    private void calcParentMap(GeoDistrictCollection tree, Map<String, GeoBounds> adcodeBoundsMap, Map<String, List<String>> parentAdcodeChildrenMap, Map<String, GeoBounds> parentAdcodeBoundsMap) {
        List<String> adcodeArray = new ArrayList<>();
        adcodeArray.addAll(adcodeBoundsMap.keySet());
        int level = Integer.MIN_VALUE;
        int parentLevel = Integer.MIN_VALUE;
        for (String adcode :
                adcodeArray) {
            GeoDistrict geoDistrict = tree.byAdcode(adcode);
            if (level == Integer.MIN_VALUE) {
                level = geoDistrict.getLevelInt();
            }
            String parentAdcode = geoDistrict.getParentAdcode();
            GeoDistrict parentGeoDistrict = tree.byAdcode(parentAdcode);
            if (parentLevel == Integer.MIN_VALUE) {
                parentLevel = parentGeoDistrict.getLevelInt();
            }
            if (level != geoDistrict.getLevelInt() || parentLevel != parentGeoDistrict.getLevelInt()) {
                throw new IllegalArgumentException(String.format("not valid tree - original: [level=%d, parentLevel=%d], current:[level=%d, parentLevel=%d, adcode=%s, parentAdcode=%s]",
                        level, parentLevel, geoDistrict.getLevelInt(), parentGeoDistrict.getLevelInt(), adcode, parentAdcode)
                );
            }
            if (StringUtils.isEmpty(parentAdcode) || StringUtils.equals(adcode, parentAdcode)) {
                continue;
            }
            // update parent's bounds
            GeoBounds geoBounds = adcodeBoundsMap.get(adcode);
            GeoBounds parentGeoBounds = GeoUtils.unionGeoBouds(parentAdcodeBoundsMap.get(parentAdcode), geoBounds);
            parentAdcodeBoundsMap.put(parentAdcode, parentGeoBounds);
            // update parent's children
            List<String> children;
            if (!parentAdcodeChildrenMap.containsKey(parentAdcode)) {
                children = new ArrayList<>();
            } else {
                children = parentAdcodeChildrenMap.get(parentAdcode);
            }
            children.add(adcode);
            parentAdcodeChildrenMap.put(parentAdcode, children);
        }
    }
}
