package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片工具
 * @version 1.0
 * @author mxy
 * @date 2017年8月31日
 */
public class ImageUtil {
	/**
	 * 图片转化成base64字符串  
	 * @return
	 */
    public static String GetImageStr(String imgFile){
//    	将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        InputStream in = null;  
        byte[] data = null;  
//      读取图片字节数组  
        try{  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
        }catch (IOException e){  
            e.printStackTrace();  
        }finally{
        	try{
        		if(in!=null)
        			in.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
//      对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
//      返回Base64编码过的字节数组字符串  
        return encoder.encode(data);
    }  
	/**
	 *  base64字符串转化成图片  
	 */
    public static boolean GenerateImage(String imgStr,String imgPath){
    	OutputStream out = null;
//    	对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
		BASE64Decoder decoder = new BASE64Decoder();  
        try{  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i){  
//            	调整异常数据
                if(b[i]<0){  
                    b[i]+=256;  
                }  
            }  
//          生成jpeg图片  
            out = new FileOutputStream(imgPath);      
            out.write(b);  
            out.flush();  
            return true;  
        }catch(Exception e){  
            return false;  
        }finally{
        	try {
        		if(out!=null)
        			out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }  
}
