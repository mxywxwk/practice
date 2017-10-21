package dailyWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class NIO {
	@Test
	public void test1() throws IOException{
		FileInputStream fis=new FileInputStream("e:/new 11.txt");
		FileChannel fileIn=fis.getChannel();
		ByteBuffer byteB=ByteBuffer.allocate(1024);
		int len=0;
		while((len=fileIn.read(byteB))!=-1){
			System.out.println(len);
			byteB.flip();
			System.out.println((char)byteB.get());
			byteB.clear();
		}
		fis.close();
	}
}
