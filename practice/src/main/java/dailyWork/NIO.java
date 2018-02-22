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
//		������	
		FileInputStream is=new FileInputStream(new File("e://out.txt"));
		FileOutputStream os=new FileOutputStream(new File("e://text.txt"));
//		�����ܵ�
		FileChannel isc=is.getChannel();
		FileChannel osc=os.getChannel();
//		��������
		ByteBuffer buffer=ByteBuffer.allocate(1024);
//		copy����
		while(isc.read(buffer)!=-1) {
//			����buffer
			buffer.flip();
			osc.write(buffer);
//			����buffer
			buffer.clear();
		}
		is.close();os.close();isc.close();osc.close();
	}
}
