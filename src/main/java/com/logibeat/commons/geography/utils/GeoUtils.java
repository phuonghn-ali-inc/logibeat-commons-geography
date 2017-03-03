package com.logibeat.commons.geography.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.logibeat.commons.geography.GeoPoint;
import com.logibeat.commons.geography.boundary.GeoDistrictBoundaries;
import com.logibeat.commons.geography.boundary.GeoDistrictBoundariesCollection;
import com.logibeat.commons.geography.polygon.GeoPolygon;
import com.logibeat.commons.geography.polygon.JdkGeneralPathGeoPolygon;
import com.logibeat.commons.geography.polygon.JdkGeoPolygon;
import com.logibeat.commons.geography.polygon.SromkuGeoPolygon;
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


    public static GeoDistrictBoundaries buildGeoDistrictBoundaries(String filename) throws IOException {
        return buildGeoDistrictBoundaries(filename, GeoPolygon.PipAlgorithm.JDK);
    }

    public static GeoDistrictBoundaries buildGeoDistrictBoundaries(String filename, GeoPolygon.PipAlgorithm pipAlgorithm) throws IOException {
        File file = new File(filename);
        String jsonString = FileUtils.readFileToString(file);
        JSONObject json = JSONObject.parseObject(jsonString);
        logger.debug("build from json file: {}, [{}]", filename, file.getAbsolutePath());
        return buildGeoDistrictBoundaries(json, pipAlgorithm);
    }

    public static GeoDistrictBoundaries buildGeoDistrictBoundaries(JSONObject json, GeoPolygon.PipAlgorithm pipAlgorithm) throws IOException {
        JSONArray boundsJson = json.getJSONArray("bounds");
        GeoPolygon[] geoPolygons = new GeoPolygon[boundsJson.size()];
        for (int i = 0; i < boundsJson.size(); i++) {
            JSONArray boundJson = boundsJson.getJSONArray(i);
            GeoPoint[] geoPoints = new GeoPoint[boundJson.size()];
            for (int j = 0; j < boundJson.size(); j++) {
                JSONObject pointJson = boundJson.getJSONObject(j);
                geoPoints[j] = new GeoPoint(pointJson.getDouble("lng"), pointJson.getDouble("lat"));
            }
            GeoPolygon geoPolygon;
            if (GeoPolygon.PipAlgorithm.JDK.equals(pipAlgorithm)) {
                geoPolygon = new JdkGeoPolygon(geoPoints);
            } else if (GeoPolygon.PipAlgorithm.JDK_GENERAL_PATH.equals(pipAlgorithm)) {
                geoPolygon = new JdkGeneralPathGeoPolygon(geoPoints);
            } else if (GeoPolygon.PipAlgorithm.SROMKU.equals(pipAlgorithm)) {
                geoPolygon = new SromkuGeoPolygon(geoPoints);
            } else {
                throw new UnsupportedOperationException("Unsupported Algorithm: " + pipAlgorithm);
            }
            geoPolygons[i] = geoPolygon;
        }
        GeoDistrictBoundaries geoDistrictBoundaries = new GeoDistrictBoundaries(json.getString("adcode"), json.getString("center"), geoPolygons);
        logger.debug("result: {}", geoDistrictBoundaries);
        return geoDistrictBoundaries;
    }

    public static GeoDistrictBoundariesCollection buildGeoDistrictBoundariesCollection(String zipFilename) throws IOException {
        return buildGeoDistrictBoundariesCollection(zipFilename, GeoPolygon.PipAlgorithm.JDK);
    }

    public static GeoDistrictBoundariesCollection buildGeoDistrictBoundariesCollection(String zipFilename, GeoPolygon.PipAlgorithm pipAlgorithm) throws IOException {
        File zipFile = new File(zipFilename);
        logger.debug("build from zip file: {}", zipFile.getCanonicalPath());
        ZipArchiveInputStream zais = new ZipArchiveInputStream(new FileInputStream(zipFile));
        ArchiveEntry archiveEntry = null;
        List<GeoDistrictBoundaries> list = new ArrayList<GeoDistrictBoundaries>();
        while ((archiveEntry = zais.getNextEntry()) != null) {
            // 获取zip中的一个入口文件名/文件夹名
            String entryFileName = archiveEntry.getName();
            if (archiveEntry.isDirectory()) { // ignore 'directory'
                continue;
            } else if (!(StringUtils.endsWith(entryFileName, ".json"))) {
                logger.debug("ignore non-json file in zip: {}", entryFileName);
                continue;
            } else if (StringUtils.startsWith(entryFileName, "__MACOSX/")
                    || StringUtils.startsWith(entryFileName, ".")
                    || StringUtils.contains(entryFileName, "/.")) {
                logger.debug("ignore hidden file in zip: {}", entryFileName);
                continue;
            }
            String jsonString = IOUtils.toString(zais);
            JSONObject json = JSONObject.parseObject(jsonString);
            GeoDistrictBoundaries geoDistrictBoundaries = buildGeoDistrictBoundaries(json, pipAlgorithm);
            list.add(geoDistrictBoundaries);
        }
        GeoDistrictBoundariesCollection geoDistrictBoundariesCollection = new GeoDistrictBoundariesCollection();
        geoDistrictBoundariesCollection.setGeoDistrictBoundariesArray(list);
        return geoDistrictBoundariesCollection;
    }
}
