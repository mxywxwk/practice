package dailyWork;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class InterviewQuestion {
	/**
	 * Integer创建对象
	 * 	[-128,127]引用常量池中的；
	 *  其他的创建新的；
	 */
	@Test
	public void test1(){
		Integer a=-128,b=-128,c=128,d=128;
		System.out.println(a==b);
		System.out.println(c==d);
	}
	/**
	 *  栈：引用
	 *  堆：对象
	 *  方法区：常量池
	 */
	@Test
	public void test2(){
//			      栈中|  堆中          | 常量池(方法区中)
		String str=new String("string");
//		intern()
		String s1=new StringBuilder("go").append("od").toString();
		String s2=new StringBuilder("ja").append("va").toString();
		System.out.println(s1.intern()==s1);
		System.out.println(s2.intern()==s2);
	}
	
	/**
	 * Math.round()
	 */
	@Test
	public void test3(){
		System.out.println(Math.round(11.5));
		System.out.println(Math.round(-11.5));
		System.out.println(2<<3);
	}
	/**
	 * String "+"
	 */
	@Test
	public void test4(){
		String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming";
        String s6 = s3 + s4;
        System.out.println(s1 == s2);
        System.out.println(s1 == s5);
        System.out.println(s1 == s6);
        System.out.println(s1 == s6.intern());
        System.out.println(s2 == s2.intern());
	}
	/**
	 * 调用GC
	 */
	@Test
	public void test5(){
		System.gc();
		Runtime.getRuntime().gc();
	}
	/**
	 * 字符串反转
	 */
	@Test
	public void test6(){
		String str="abcdef";
		System.out.println(str);
		System.out.println(reverse(str));
	}
	public static String reverse(String str){
		if(str==null||str.length()<=1)
			return str;
		return reverse(str.substring(1))+str.charAt(0);
	}
	/**
	 * 字符转码
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void test7() throws UnsupportedEncodingException{
		String str="我的天啊！";
		System.out.println(new String(str.getBytes("utf-8"),"UTF-8"));
	}
	
	class Annoyance extends Exception {}
	class Sneeze extends Annoyance {}
	/**
	 * 异常捕获
	 */
	@Test
	public void test8(){
		try {
            try {
                throw new Sneeze();
            }catch ( Exception a ) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        }catch ( Exception s ) {
            System.out.println("Caught Sneeze");
            return ;
        }finally {
            System.out.println("Hello World!");
        }
	}
	
	/**
	 * 
	 */
	@Test
	public void test9(){
		
	}
}
