package com.logibeat.commons.geography.district;

/**
 * Created by alex on 09/03/2017.
 */
public enum GeoDistrictLevel {
    COUNTRY("country", "国家", 0),
    PROVINCE("province", "省", 1),
    CITY("city", "市", 2),
    DISTRICT("district", "区", 3),
    STREET("street", "街道", 98),
    BIZ_AREA("biz_area", "商圈", 99);

    private final String name;
    private final String desc;
    private final int value;

    GeoDistrictLevel(String level, String desc, int value) {
        this.name = level;
        this.desc = desc;
        this.value = value;
    }

    public static GeoDistrictLevel levelValueOf(int value) {
        return _levelValueOf(null, value, true);
    }

    public static GeoDistrictLevel levelValueOf(String name) {
        return _levelValueOf(name, -1, false);
    }

    private static GeoDistrictLevel _levelValueOf(String name, int value, Boolean usingValue) {
        for (GeoDistrictLevel level :
                GeoDistrictLevel.values()) {
            if (usingValue && level.getValue() == value) {
                return level;
            }
            if (!usingValue && level.getName().equalsIgnoreCase(name)) {
                return level;
            }
        }
        throw new IllegalArgumentException(
                "No enum constant " + GeoDistrictLevel.class.getCanonicalName() + "." +
                        (usingValue ? value : name));
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GeoDistrictLevel{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", value=" + value +
                '}';
    }

}
