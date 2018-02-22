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
 * 将指定目录下的Java文件的绝对路径,存储到一个文本文件中
 * @version 1.0
 * @author mxy
 * @date 2017年9月30日
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
	 * 将文件路径写入到一个文件中
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
	 * 遍历文件目录,将".java"文件归档
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
	 * 字节流写/读
	 * @throws IOException 
	 */
	@Test
	public void test2() throws IOException{
		String outStr="hi，我的天啊";
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
	 * 字符流写/读
	 * @throws IOException 
	 */
	@Test
	public void test3() throws IOException {
		String str=new String("我的天啊");
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
	 * 随机写/读文件
	 * @throws IOException 
	 */
	@Test
	public void test4() throws IOException {
		File file=new File("e:/r.txt");
		RandomAccessFile raf=new RandomAccessFile(file, "rw");
		raf.seek(10);
		raf.write("我的天啊".getBytes("utf-8"));
		raf.seek(13);
		byte[] bytes=new byte[3];
		raf.read(bytes);
		System.out.println(new String(bytes,"utf-8"));
		System.out.println(System.identityHashCode(bytes));
		raf.close();
		System.out.println();
	}
}














