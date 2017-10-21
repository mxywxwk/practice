package dailyWork;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlUtil {
	public static InputStream loadUrl(String url) {
		InputStream in = null;
		try {
			URL uRl = new URL(url);
			URLConnection uc = uRl.openConnection();
			HttpURLConnection huc = (HttpURLConnection) uc;
			if(huc.getContentLength()>10000){
//				System.out.println("big_lenght:"+huc.g());
			}
			in = huc.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}

	public static synchronized boolean loadInputStream(InputStream in, String path, String name) {
		try {
			
			
			System.out.println("load start!---------"+path+name);
			java.io.File file1 = new java.io.File(path);
			if (!file1.exists()) {
				file1.mkdirs();
			}
			System.out.println(file1.getAbsolutePath());
			java.io.File file = new java.io.File(path + name);
			if(file.exists()){
//				System.out.println("File exist["+path+name+"]");
				return false;
			}
			if (!file.exists()) {
				System.out.println(path+name);
				file.createNewFile();
			}
			BufferedInputStream bis = new BufferedInputStream(in); 
			BufferedOutputStream fos  = new BufferedOutputStream(new FileOutputStream(file));  
			byte[] temp = new byte[2048];
			int len = 2048;
			 while((len=in.read(temp))!=-1) {   
	                fos.write(temp, 0, len);  
	            }  
			fos.flush();
			fos.close();
			in.close();
//			System.out.println("load success!--------"+path+name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static String loadImageName(String z) {
		return z + ".png";
	}

	public  static String loadPath(String x, String y) {
		return "/D:/map/tiles"+java.io.File.separator + x + java.io.File.separator + y + java.io.File.separator;
	}

	public static String loadUrlPath(String x,String y,String z,String onlineNum){
		String url = "http://online"+onlineNum+".map.bdimg.com/tile/?qt=tile&x="+x+"&y="+y+"&z="+z+"&styles=pl&scaler=1&udt=20160401";
//		System.out.println("load url:"+url);
		return url;
	}
	
	public static void loadImage(String x,String y,String z,String onlineNum){
		loadInputStream(loadUrl(loadUrlPath(x, y, z,onlineNum)),loadPath(z, x), loadImageName(y));
	}
	public static void main(String[] args) {
		deleteFile deleteFile=new deleteFile();
		for(int i=4;i<5;i++){
			for(int c=19;c<=19;c++){//0到20
				for(int a=101246;a<=101260;a++){
					for(int b=37796;b<=37805;b++){
						loadImage(String.valueOf(a),String.valueOf(b),String.valueOf(c),String.valueOf(i));
					}
				}
			}
			//调用删除方法，删除多余图片以及文件夹
			deleteFile.deleteInterface();
		}
		System.out.println("-------------OVER-------------");
		
	}
}
