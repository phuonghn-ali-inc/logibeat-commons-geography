package com.logibeat.commons.geography;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 17/02/2017.
 */
public class GeoUtils {
    private static final Logger logger = LoggerFactory.getLogger(GeoUtils.class);

    public static boolean contains(GeoPolygon geoPolygon, double x, double y) {
        return contains(geoPolygon, new GeoPoint(x, y));
    }


    public static boolean contains(GeoPolygon geoPolygon, GeoPoint geoPoint) {
        return geoPolygon.contains(geoPoint);
    }

    public static boolean contains(GeoDistrict geoDistrict, double x, double y) {
        return contains(geoDistrict, new GeoPoint(x, y));
    }

    public static boolean contains(GeoDistrict geoDistrict, GeoPoint geoPoint) {
        return geoDistrict.contains(geoPoint);
    }

    public static GeoDistrict contains(GeoDistrict[] geoDistricts, double x, double y) {
        return contains(geoDistricts, new GeoPoint(x, y));
    }

    public static String containsInAdcode(GeoDistrict[] geoDistricts, double x, double y) {
        return containsInAdcode(geoDistricts, new GeoPoint(x, y));
    }

    public static String containsInAdcode(GeoDistrict[] geoDistricts, GeoPoint geoPoint) {
        GeoDistrict geoDistrict = contains(geoDistricts, geoPoint);
        if (geoDistrict != null) {
            return geoDistrict.getAdcode();
        }
        return null;
    }

    public static GeoDistrict contains(GeoDistrict[] geoDistricts, GeoPoint geoPoint) {
        for (GeoDistrict geoDistrict : geoDistricts) {
            if (geoDistrict.contains(geoPoint)) {
                return geoDistrict;
            }
        }
        return null;
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

    public static GeoDistrict[] buildGeoDistricts(String zipFilename, GeoPolygon.AlgorithmPolicy algorithmPolicy) throws IOException {
        File zipFile = new File(zipFilename);
        logger.debug("zip file: {}", zipFile.getCanonicalPath());
        ZipArchiveInputStream zais = new ZipArchiveInputStream(new FileInputStream(zipFile));
        ArchiveEntry archiveEntry = null;
        List<GeoDistrict> list = new ArrayList<GeoDistrict>();
        while ((archiveEntry = zais.getNextEntry()) != null) {
            // 获取zip中的一个入口文件名/文件夹名
            String entryFileName = archiveEntry.getName();
            if (archiveEntry.isDirectory()) { // ignore 'directory'
                continue;
            } else if (!(StringUtils.endsWith(entryFileName, ".json"))) {
                logger.debug("ignore non-json file: {}", entryFileName);
                continue;
            } else if (StringUtils.startsWith(entryFileName, "__MACOSX/")
                    || StringUtils.startsWith(entryFileName, ".")
                    || StringUtils.contains(entryFileName, "/.")) {
                logger.debug("ignore hidden file: {}", entryFileName);
                continue;
            }
            String jsonString = IOUtils.toString(zais);
            JSONObject json = JSONObject.parseObject(jsonString);
            GeoDistrict geoDistrict = buildGeoDistrict(json, algorithmPolicy);
            list.add(geoDistrict);
        }
        return list.toArray(new GeoDistrict[list.size()]);
    }
}
