package proxy;

public class StaticProxyLawyer implements Subject{
	private Subject subject;
	public StaticProxyLawyer(Subject subject) {
		super();
		this.subject = subject;
	}

	@Override
	public void lawsuit() {
		before();
		subject.lawsuit();
		after();
	}

	private void after() {
		System.out.println("after");
	}

	private void before() {
		System.out.println("before");
	}

}
