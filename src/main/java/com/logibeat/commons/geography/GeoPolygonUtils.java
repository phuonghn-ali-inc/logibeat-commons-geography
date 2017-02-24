package com.logibeat.commons.geography;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoPolygonUtils {
    private static final Logger logger = LoggerFactory.getLogger(GeoPolygonUtils.class);

    public static boolean contains(GeoPolygon geoPolygon, double x, double y) {
        return contains(geoPolygon, new GeoPoint(x, y));
    }


    public static boolean contains(GeoPolygon geoPolygon, GeoPoint geoPoint) {
        return geoPolygon.contains(geoPoint);
    }


    public static GeoDistrict buildGeoDistrict(String filename) throws IOException {
        return buildGeoDistrict(filename, GeoPolygon.AlgorithmPolicy.JDK);
    }

    public static GeoDistrict buildGeoDistrict(String filename, GeoPolygon.AlgorithmPolicy algorithmPolicy) throws IOException {
        long tt1 = System.currentTimeMillis();
        GeoDistrict geoDistrict = buildGeoDistrict(new File(filename), algorithmPolicy);
        long tt2 = System.currentTimeMillis();
        logger.debug("{} ms elapsed for buildGeoDistrict.", tt2 - tt1);
        return geoDistrict;
    }

    public static GeoDistrict buildGeoDistrict(File file, GeoPolygon.AlgorithmPolicy algorithmPolicy) throws IOException {
        logger.debug("build from Boundaries Data File (JSON): {}", file.getCanonicalPath());
        String jsonString = FileUtils.readFileToString(file);
        JSONObject json = JSONObject.parseObject(jsonString);
        return buildGeoDistrict(json, algorithmPolicy);
    }

    public static GeoDistrict buildGeoDistrict(JSONObject json, GeoPolygon.AlgorithmPolicy algorithmPolicy) throws IOException {
        JSONArray boundsJson = json.getJSONArray("bounds");
        GeoPolygon[] geoPolygons = new GeoPolygon[boundsJson.size()];
        for (int i = 0; i < boundsJson.size(); i++) {
            JSONArray boundJson = boundsJson.getJSONArray(i);
            GeoPoint[] geoPoints = new GeoPoint[boundJson.size()];
            for (int j = 0; j < boundJson.size(); j++) {
                JSONObject pointJson = boundJson.getJSONObject(j);
                geoPoints[j] = new GeoPoint(pointJson.getDouble("lng"), pointJson.getDouble("lat"));
            }
            GeoPolygon geoPolygon = null;
            if (GeoPolygon.AlgorithmPolicy.JDK.equals(algorithmPolicy)) {
                geoPolygon = new JdkGeoPolygon(geoPoints);
            } else if (GeoPolygon.AlgorithmPolicy.JDK_GENERAL_PATH.equals(algorithmPolicy)) {
                geoPolygon = new JdkGeneralPathGeoPolygon(geoPoints);
            } else if (GeoPolygon.AlgorithmPolicy.SROMKU.equals(algorithmPolicy)) {
                geoPolygon = new SromkuGeoPolygon(geoPoints);
            } else {
                throw new UnsupportedOperationException("Unsupported Algorithm Policy: " + algorithmPolicy);
            }
            geoPolygons[i] = geoPolygon;
        }
        return new GeoDistrict(json.getString("adcode"), json.getString("center"), geoPolygons);
    }
}
