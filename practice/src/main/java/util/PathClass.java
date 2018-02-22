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
 * 各种路径
 * @author mxy
 *
 */
public class PathClass {
	
	@Test
	public void test1(HttpServletRequest req,HttpSession session,ServletConfig config) throws IOException {
//		java中获取路径
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));//获取资源文件(.class)所在路径
		System.out.println(ClassLoader.getSystemResource(""));//获取资源文件(.class)所在路径
		System.out.println(PathClass.class.getClassLoader().getResource(""));//获取资源文件(.class)所在路径
		System.out.println(PathClass.class.getResource("/"));//获取资源文件(.class)所在路径
		System.out.println(PathClass.class.getResource(""));//获取当前类所在路径
		System.out.println(System.getProperty("user.dir"));//获取项目根目录的绝对路径
		
//		jsp中获取路径
		System.out.println(req.getRequestURI());//根目录所对应的绝对路径
		
//		Servle中获取路径
		System.out.println(req.getServletPath());//根目录所对应的的绝对路径
		System.out.println(session.getServletContext().getRealPath(req.getRequestURI()));//文件的绝对路径
		System.out.println(session.getServletContext().getRealPath("/"));//web应用的绝对路径
		
		System.out.println(req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath());//http://localhost:8080/practice
	}
	
	/**
	 * 所有系统属性
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
