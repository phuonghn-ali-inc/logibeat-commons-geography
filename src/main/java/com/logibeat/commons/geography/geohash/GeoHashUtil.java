package com.logibeat.commons.geography.geohash;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

import com.logibeat.commons.geography.polygon.GeoBounds;

public class GeoHashUtil {

	private static int numbits = 3 * 5;
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
	static {
		int i = 0;
		for (char c : digits)
			lookup.put(c, i++);
	}

	public static double[] decode(String geohash) {
		StringBuilder buffer = new StringBuilder();
		for (char c : geohash.toCharArray()) {
			int i = lookup.get(c) + 32;
			buffer.append(Integer.toString(i, 2).substring(1));
		}

		BitSet lonset = new BitSet();
		BitSet latset = new BitSet();

		// even bits
		int j = 0;
		for (int i = 0; i < numbits * 2; i += 2) {
			boolean isSet = false;
			if (i < buffer.length())
				isSet = buffer.charAt(i) == '1';
			lonset.set(j++, isSet);
		}

		// odd bits
		j = 0;
		for (int i = 1; i < numbits * 2; i += 2) {
			boolean isSet = false;
			if (i < buffer.length())
				isSet = buffer.charAt(i) == '1';
			latset.set(j++, isSet);
		}

		double lon = decode(lonset, -180, 180);
		double lat = decode(latset, -90, 90);
		return new double[] { lat, lon };
	}

	private static double decode(BitSet bs, double floor, double ceiling) {
		double mid = 0;
		for (int i = 0; i < bs.length(); i++) {
			mid = (floor + ceiling) / 2;
			if (bs.get(i))
				floor = mid;
			else
				ceiling = mid;
		}
		return mid;
	}

	public static String encode(double lat, double lon) {
		BitSet latbits = getBits(lat, -90, 90);
		BitSet lonbits = getBits(lon, -180, 180);
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < numbits; i++) {
			buffer.append((lonbits.get(i)) ? '1' : '0');
			buffer.append((latbits.get(i)) ? '1' : '0');
		}

		Long bufferLong = Long.parseLong(buffer.toString(), 2);
		return base32(bufferLong);
	}

	private static BitSet getBits(double lat, double floor, double ceiling) {
		BitSet buffer = new BitSet(numbits);
		for (int i = 0; i < numbits; i++) {
			double mid = (floor + ceiling) / 2;
			if (lat >= mid) {
				buffer.set(i);
				floor = mid;
			} else {
				ceiling = mid;
			}
		}
		return buffer;
	}

	public static String base32(long i) {
		char[] buf = new char[65];
		int charPos = 64;
		boolean negative = (i < 0);
		if (!negative)
			i = -i;
		while (i <= -32) {
			buf[charPos--] = digits[(int) (-(i % 32))];
			i /= 32;
		}
		buf[charPos] = digits[(int) (-i)];

		if (negative)
			buf[--charPos] = '-';
		return new String(buf, charPos, (65 - charPos));
	}

	private static String getBase32BinaryString(int i) {
		if (i < 0 || i > 31) {
			return null;
		}
		String str = Integer.toBinaryString(i + 32);
		return str.substring(1);
	}

	/**
	 * @param geoHash
	 * @return
	 * @Description: 将geoHash转化为二进制字符串
	 */
	private static String getGeoHashBinaryString(String geoHash) {
		if (geoHash == null || "".equals(geoHash)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < geoHash.length(); i++) {
			char c = geoHash.charAt(i);
			if (lookup.containsKey(c)) {
				String cStr = getBase32BinaryString(lookup.get(c));
				if (cStr != null) {
					sb.append(cStr);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param geoHash
	 * @return
	 * @Description: 返回geoHash 对应的坐标二进制
	 */
	public static String[] getLocationBinary(String geoHash) {
		String geoHashBinaryStr = getGeoHashBinaryString(geoHash);
		if (geoHashBinaryStr == null) {
			return null;
		}
		StringBuffer lat = new StringBuffer();
		StringBuffer lng = new StringBuffer();
		for (int i = 0; i < geoHashBinaryStr.length(); i++) {
			if (i % 2 != 0) {
				lat.append(geoHashBinaryStr.charAt(i));
			} else {
				lng.append(geoHashBinaryStr.charAt(i));
			}
		}
		return new String[] { lat.toString(), lng.toString() };
	}

	/**
	 * 
	 * @param latBinary
	 * @param lonBinary
	 * @return
	 * @Description: 返回geoHash(二进制的经纬度)
	 */
	public static String encodeBinary(String latBinary, String lonBinary) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < numbits; i++) {
			buffer.append(lonBinary.charAt(i));
			buffer.append(latBinary.charAt(i));
		}
		Long bufferLong = Long.parseLong(buffer.toString(), 2);
		return base32(bufferLong);
	}

	/**
	 *  二进制加法
	 * @param a
	 * @param b
	 * @return
	 */
	public static String addBinary(String org,String add){
        StringBuilder sb=new StringBuilder();
        int x=0; 
        int y=0;
        int pre=0;//进位
        int sum=0;//存储进位和另两个位的和
        
        while(org.length()!=add.length()){//将两个二进制的数位数补齐,在短的前面添0
            if(org.length()>add.length()){
            	add="0"+add;
            }else{
            	org="0"+org;
            }
        }
            for(int i=org.length()-1;i>=0;i--){
                x=org.charAt(i)-'0';
                y=add.charAt(i)-'0';
                sum=x+y+pre;//从低位做加法
                if(sum>=2){
                    pre=1;//进位
                    sb.append(sum-2);
                }else{
                	pre=0;
                    sb.append(sum);
                }
            }
            if(pre==1){ 
                sb.append("1");
            }
        return sb.reverse().toString();//翻转返回
    }

	//TODO 这个方法不行
	public static List<String> getGeohash(GeoBounds geoBounds,String adcode) {
		List<String> geohashes = new ArrayList<>();;
		String minGeohash = encode(geoBounds.getMinX(), geoBounds.getMinY());
		String maxGeohash = encode(geoBounds.getMaxX(), geoBounds.getMaxY());
		if(minGeohash.equals(maxGeohash)){
			 geohashes.add(maxGeohash);
			return  geohashes;
		}
		String[] minBinary = getLocationBinary(minGeohash);
		String[] maxBinary = getLocationBinary(maxGeohash);
		String minlat = minBinary[0];
		String minlng =minBinary[1];
		String maxlat = maxBinary[0];
		String maxlng =maxBinary[1];
		int i = 0;
		System.err.println("adcode:"+adcode+"minGeohash:"+minGeohash+"--------------"+"maxGeohash:"+maxGeohash);
		System.err.println("minlat:"+minlat+"--------------"+"maxlat:"+maxlat);
		System.err.println("minlng:"+minlng+"--------------"+"maxlng:"+maxlng);
		if(minlat.equals(maxlat)){
	        do {
	        	String encodeBinary = encodeBinary(minlat,minlng);
	        	System.err.println("上面"+i+"---------------"+"hash:"+ encodeBinary);
	        	if( !geohashes.contains(encodeBinary)){
	        	  geohashes.add(encodeBinary); 
	        	}
	        	
	        	minlng = addBinary(minlng,"1");
	        	i++;
	           }while(!maxlng.equals(minlng)); 
	        
	        String encodeBinary = encodeBinary(maxlat,minlng);
	    	if( !geohashes.contains(encodeBinary)){
	    	  geohashes.add(encodeBinary); 
	    	} 
		}else{
			String orgMinlnt = minlng;
			do {
				String encodeBinary = encodeBinary(minlat,minlng);
	        	if( !geohashes.contains(encodeBinary)){
	        	  geohashes.add(encodeBinary); 
	        	}
	        	i++;
	        	System.err.println("下面"+i+"--------------"+"hash:"+ encodeBinary);
	        	if(maxlng.equals(minlng)){
	        		minlat = addBinary(minlat,"1");
	        		minlng = orgMinlnt;
	        	}else{
	        		minlng = addBinary(minlng,"1");
	        	}
	           }while(!maxlat.equals(minlat)); 
		}
		return geohashes;
	}
	
	
	public static void main(String[] args) throws Exception {
		String string = GeoHashUtil.encode(39.92324, 116.3906);
		System.out.println(string.toString());
		double[] doubleSet = GeoHashUtil.decode(string);
		System.out.println("经度：" + doubleSet[0]);
		System.out.println("纬度：" + doubleSet[1]);
		String[] binary = getLocationBinary(string);
		String encode = new GeoHashUtil().encodeBinary(binary[0], binary[1]);
		System.out.println("经度Binary：" + binary[0]);
		System.out.println("纬度Binary：" + binary[1]);
		System.out.println("encodeBinary：" + encode);
		System.out.println("Binary add ：" + addBinary(binary[0],"1"));
	}

	

}