package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyLawyer implements InvocationHandler{
	private Object subject;
	public DynamicProxyLawyer(Object subject) {
		super();
		this.subject = subject;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		before();
		method.invoke(subject,args);
		after();
		return null;
	}
	private void after() {
		System.out.println("after");
	}
	private void before() {
		System.out.println("before");
	}
	
}
