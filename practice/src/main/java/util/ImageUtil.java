package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * ͼƬ����
 * @version 1.0
 * @author mxy
 * @date 2017��8��31��
 */
public class ImageUtil {
	/**
	 * ͼƬת����base64�ַ���  
	 * @return
	 */
    public static String GetImageStr(String imgFile){
//    	��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
        InputStream in = null;  
        byte[] data = null;  
//      ��ȡͼƬ�ֽ�����  
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
//      ���ֽ�����Base64����  
        BASE64Encoder encoder = new BASE64Encoder();  
//      ����Base64��������ֽ������ַ���  
        return encoder.encode(data);
    }  
	/**
	 *  base64�ַ���ת����ͼƬ  
	 */
    public static boolean GenerateImage(String imgStr,String imgPath){
    	OutputStream out = null;
//    	���ֽ������ַ�������Base64���벢����ͼƬ  
        if (imgStr == null) //ͼ������Ϊ��  
            return false;  
		BASE64Decoder decoder = new BASE64Decoder();  
        try{  
            //Base64����  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i){  
//            	�����쳣����
                if(b[i]<0){  
                    b[i]+=256;  
                }  
            }  
//          ����jpegͼƬ  
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
