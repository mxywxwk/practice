package util;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;


/**
 * 
 *<P>Description:地图坐标API</P> 
 * @version 1.0
 * @author mxy
 */
public class MapUtil {
	private static final Double PI=Math.PI;  
    private static final Double PK=180/PI;  
    private static final Double X_PI=PI*3000.0/180.0;  
	/**
	 * 计算两点间距离(bd-09)
	 * @param pointX1
	 * @param pointY1
	 * @param pointX2
	 * @param pointY2
	 * @return
	 */
    public static double getDistance(String lat_a, String lng_a, String lat_b, String lng_b) { 
    	double y1=Double.valueOf(lat_a);
		double x1=Double.valueOf(lng_a);
		double y2=Double.valueOf(lat_b);
		double x2=Double.valueOf(lng_b);
        double t1 = Math.cos(y1 / PK) * Math.cos(x1 / PK) * Math.cos(y2 / PK) * Math.cos(x2 / PK);  
        double t2 = Math.cos(y1 / PK) * Math.sin(x1 / PK) * Math.cos(y2 / PK) * Math.sin(x2 / PK);  
        double t3 = Math.sin(y1 / PK) * Math.sin(y2 / PK);  
        double tt = Math.acos(t1 + t2 + t3);  
        return 6366000 * tt;  
    } 
    /**
     * GCJ-02(高德) ----> BD-09(百度) 
     * @param lat  GPS_Y 纬度
     * @param lng  GPS_X 经度
     * @return
     */
    public static String[] GCJ02ToBD09(String lat, String lng) {  
        double x = Double.valueOf(lng), y = Double.valueOf(lat);  
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);  
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);  
        double tempLng = z * Math.cos(theta) + 0.0065;  
        double tempLat = z * Math.sin(theta) + 0.006;  
        String[] gcj = {String.format("%.6f",tempLat),String.format("%.6f",tempLng)};  
        return gcj;  
    }  
    /**
     * BD-09(百度) ---> GCJ-02(高德)
     * @param lat  GPS_Y
     * @param lng  GPS_X
     * @return
     */
    public static String[] BD09ToGCJ02(String lat, String lng) {  
        double x = Double.valueOf(lng) - 0.0065, y = Double.valueOf(lat) - 0.006;  
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);  
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);  
        double tempLng = z * Math.cos(theta);  
        double tempLat = z * Math.sin(theta);  
        String[] bd = {String.format("%.6f",tempLat),String.format("%.6f",tempLng)};  
        return bd;  
    }  
    
    
    /**
     * 输入经纬度,获取地址,返回字符串
     * @param lng
     * @param lat
     * @return
     */
    public static String getAddr(String lng, String lat){ 
    	//参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)  
        String urlString = "http://api.map.baidu.com/geocoder/v2/?ak=wPPxsfCtNFm4PqEV68jbDdjP7U4twstj&callback=renderReverse&location="+ lat + "," + lng + "&output=json&pois=1"; 
        String res = "";   
        try {   
          URL url = new URL(urlString);  
          java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();  
          conn.setDoOutput(true);  
          conn.setRequestMethod("POST");  
          java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));  
          String line;  
          while ((line = in.readLine()) != null) {  
            res += line+"\n";  
         }  
          in.close();  
        } catch (Exception e) {  
          System.out.println("error in wapaction,and e is " + e.getMessage());  
        }  
        System.out.println(res);
        return res;  
      } 
}
