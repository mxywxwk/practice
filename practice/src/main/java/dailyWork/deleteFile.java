package dailyWork;

import java.io.File;

public class deleteFile {
	public void deleteInterface(){
		String path="D:/java_workspace/MapDownload/WebContent/tiles";
		
		for(int i=0;i<3;i++){
			delete(path);
		}
		
		System.out.println("�ɹ�ɾ������ͼƬ");
	}
	public void delete(String path) {
		
		  File file=new File(path);
		  File[] tempList = file.listFiles();
		  for (int i = 0; i < tempList.length; i++) {
		   if (tempList[i].isFile()) {
		   // System.out.println(tempList[i].length()+"��"+tempList[i].getPath());
		    int a=(int)tempList[i].length();
		    if(/*a==197 || */a==798 /*|| a==210 *//*|| a==224 || a==222*/){//ͨ��url��ȡ�������ݺ����ɵ�ͼƬ��СΪ798
		    	tempList[i].delete();
		    }
		   }
		   if (tempList[i].isDirectory()) {
			   //����ļ���û���Ӽ�����ɾ��
			   String[] files = tempList[i].list();
	            if (files.length > 0) {
	            	String filePath=tempList[i].getPath();
	    		    delete(filePath);
	            }else {
	            	tempList[i].delete();
				}
		   }
		  }
	}
	
	public static void main(String[] args) {
		String path="D:/java_workspace/MapDownload/WebContent/tiles";
		
		for(int i=0;i<3;i++){
		//	delete(path);//ִ��main����ʱ�ſ���һ�д���
		}
		
		System.out.println("�ɹ�ɾ������ͼƬ");
	}

}
