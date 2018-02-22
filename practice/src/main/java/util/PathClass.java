package util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.junit.Test;

/**
 * ����·��
 * @author mxy
 *
 */
public class PathClass {
	
	@Test
	public void test1(HttpServletRequest req,HttpSession session,ServletConfig config) throws IOException {
//		java�л�ȡ·��
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));//��ȡ��Դ�ļ�(.class)����·��
		System.out.println(ClassLoader.getSystemResource(""));//��ȡ��Դ�ļ�(.class)����·��
		System.out.println(PathClass.class.getClassLoader().getResource(""));//��ȡ��Դ�ļ�(.class)����·��
		System.out.println(PathClass.class.getResource("/"));//��ȡ��Դ�ļ�(.class)����·��
		System.out.println(PathClass.class.getResource(""));//��ȡ��ǰ������·��
		System.out.println(System.getProperty("user.dir"));//��ȡ��Ŀ��Ŀ¼�ľ���·��
		
//		jsp�л�ȡ·��
		System.out.println(req.getRequestURI());//��Ŀ¼����Ӧ�ľ���·��
		
//		Servle�л�ȡ·��
		System.out.println(req.getServletPath());//��Ŀ¼����Ӧ�ĵľ���·��
		System.out.println(session.getServletContext().getRealPath(req.getRequestURI()));//�ļ��ľ���·��
		System.out.println(session.getServletContext().getRealPath("/"));//webӦ�õľ���·��
		
		System.out.println(req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath());//http://localhost:8080/practice
	}
	
	/**
	 * ����ϵͳ����
	 */
	@Test
	public void test2() {
		Properties properties=System.getProperties();
		Iterator<Entry<Object, Object>> it=properties.entrySet().iterator();
		while(it.hasNext()){
		    Entry<Object, Object> entry=(Entry<Object, Object>)it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key +":"+value);
		}
	}
}
