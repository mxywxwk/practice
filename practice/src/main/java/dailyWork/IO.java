package dailyWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
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
		File in=new File("D:\\workspace");
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
		Arrays.asList(in.listFiles())
			.stream()
			.forEach(file->{
				if(file.isDirectory())
					fileToList(file,files);
				else
					if(file.getName().endsWith(".java"))
						files.add(file);
			});
		return files;
	}
	
	/**
	 * �ֽ���д/��
	 * @throws IOException 
	 */
	@Test
	public void test2() throws IOException{
		String outStr="hi���ҵ��찡";
		byte[] outs=outStr.getBytes("utf-8");
		OutputStream os=new FileOutputStream("e:/out.txt");
		os.write(outs);
		os.close();
		
		File file=new File("e://out.txt");
		byte[] 	iss=new byte[(int) file.length()];
		InputStream is=new FileInputStream(file);
		is.read(iss);
		System.out.println(new String(iss,"utf-8"));
		is.close();
	}
	
	/**
	 * �ַ���д/��
	 * @throws IOException 
	 */
	@Test
	public void test3() throws IOException {
		String str=new String("�ҵ��찡");
		Writer w=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("e:/w.txt"),"utf-8"),2*1024);
		w.write(str);
		w.close();
		
		Reader r=new InputStreamReader(new FileInputStream("e:/w.txt"),"utf-8");
		Reader br=new BufferedReader(new InputStreamReader(new FileInputStream("e:/w.txt"),"utf-8"),4*1024);
		StringBuffer sb=new StringBuffer();
		char[] chars=new char[1];
		while(r.read(chars)!=-1){
			sb.append(new String(chars));
		}
		r.close();
		br.close();
		System.out.println(sb.toString());
	}
	
	/**
	 * ���д/���ļ�
	 * @throws IOException 
	 */
	@Test
	public void test4() throws IOException {
		File file=new File("e:/r.txt");
		RandomAccessFile raf=new RandomAccessFile(file, "rw");
		raf.seek(10);
		raf.write("�ҵ��찡".getBytes("utf-8"));
		raf.seek(13);
		byte[] bytes=new byte[3];
		raf.read(bytes);
		System.out.println(new String(bytes,"utf-8"));
		System.out.println(System.identityHashCode(bytes));
		raf.close();
		System.out.println();
	}
}














