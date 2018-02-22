package util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class HttpUtil {
	public static void main(String[] args) {
		getHttp("http://www.gamersky.com",null);
	}
	public static JSON getHttp(String path,Map<String,Object> params) {
		try {
			URL url=new URL(path);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
