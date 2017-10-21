package dailyWork;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * ��ָ��Ŀ¼�µ�Java�ļ��ľ���·��,�洢��һ���ı��ļ���
 * @version 1.0
 * @author mxy
 * @date 2017��9��30��
 */
public class IO {
	
	@Test
	public void test1(){
		File in=new File("D:\\eclipse\\2\\nfc");
		List<File> files=new ArrayList<File>();
		files=fileToList(in,files);
		File out=new File("e:/io/javaList.txt");
		if(!out.getParentFile().exists())
			out.getParentFile().mkdirs();
		writeToFile(files,out.toString());
		System.out.println(out.toString());
	}
	/**
	 * ���ļ�·��д�뵽һ���ļ���
	 * @param files
	 * @param out
	 * @throws IOException 
	 */
	public void writeToFile(List<File> files, String out){
		System.out.println("----------start write file path-----------------");
		try{
			final BufferedWriter bw=new BufferedWriter(new FileWriter(out));
			files.stream()
				.forEach(file->{
					String path=file.getAbsolutePath();
					System.out.println(path);
					try {
						bw.write(path);
						bw.newLine();
						bw.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			if(bw!=null)
				bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("-----------end write file path--------------------");
	}
	/**
	 * �����ļ�Ŀ¼,��".java"�ļ��鵵
	 * @param in
	 * @param files
	 */
	private List<File> fileToList(File in, List<File> files) {
		System.out.println("-----------start get file list------------");
		Arrays.asList(in.listFiles())
			.stream()
			.forEach(file->{
				if(file.isDirectory())
					fileToList(file,files);
				else
					if(file.getName().endsWith(".java"))
						files.add(file);
			});
		System.out.println("-----------end get file list--------------");
		return files;
	}
}
