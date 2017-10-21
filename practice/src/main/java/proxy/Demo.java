package proxy;

import java.lang.reflect.Proxy;

public class Demo {
	public static void main(String[] args) {
		System.out.println("static proxy");
		Subject subject=new RealSubject();
		StaticProxyLawyer proxy=new StaticProxyLawyer(subject);
		proxy.lawsuit();
		System.out.println("end static proxy");
		System.out.println("dynamic proxy");
//		Subject1 subject11=(Subject1) Proxy.newProxyInstance(subject.getClass().getClassLoader(), new Class[]{Subject1.class}, new DynamicProxyLawyer(subject));
	}
}
