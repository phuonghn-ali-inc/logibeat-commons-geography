package com.logibeat.commons.geography.district;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by alex on 09/03/2017.
 */
public class GeoDistrict implements Cloneable {

    private String citycode;

    private String adcode;

    private String name;

    private String center;

    private String level;

    private String areacode;

    private String parentAdcode;

    private int levelInt;

    private String shortName;

    private String mergerName;

    private String mergerShortName;

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getParentAdcode() {
        return parentAdcode;
    }

    public void setParentAdcode(String parentAdcode) {
        this.parentAdcode = parentAdcode;
    }

    public int getLevelInt() {
        return levelInt;
    }

    public void setLevelInt(int levelInt) {
        this.levelInt = levelInt;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getMergerShortName() {
        return mergerShortName;
    }

    public void setMergerShortName(String mergerShortName) {
        this.mergerShortName = mergerShortName;
    }

    @Override
    public String toString() {
        return "GeoDistrict{" +
                "citycode='" + citycode + '\'' +
                ", adcode='" + adcode + '\'' +
                ", name='" + name + '\'' +
                ", center='" + center + '\'' +
                ", level='" + level + '\'' +
                ", areacode='" + areacode + '\'' +
                ", parentAdcode='" + parentAdcode + '\'' +
                ", levelInt=" + levelInt +
                ", shortName='" + shortName + '\'' +
                ", mergerName='" + mergerName + '\'' +
                ", mergerShortName='" + mergerShortName + '\'' +
                '}';
    }

    public GeoDistrict clone() {
        GeoDistrict geoDistrict = new GeoDistrict();
        geoDistrict.citycode = this.citycode;
        geoDistrict.adcode = this.adcode;
        geoDistrict.name = this.name;
        geoDistrict.center = this.center;
        geoDistrict.level = this.level;
        geoDistrict.areacode = this.areacode;
        geoDistrict.parentAdcode = this.parentAdcode;
        geoDistrict.levelInt = this.levelInt;
        geoDistrict.shortName = this.shortName;
        geoDistrict.mergerName = this.mergerName;
        geoDistrict.mergerShortName = this.mergerShortName;
        return geoDistrict;
    }
}
