package dailyWork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.junit.Test;

/**
 * Lambda���ʽ
 * @version 1.0
 * @author mxy
 * @date 2017��9��15��
 */
public class Lambda {
	@Test
	public void test1() throws InterruptedException{
		new Thread(new Runnable(){
			@Override
			public void run(){
				for(int i=0;i<1000;i++){
					System.out.println(i);
				}
			}
		}).start();
		Thread.sleep(1000);
	}
	/**
	 * �����ڲ���
	 * @throws InterruptedException
	 */
	@Test
	public void test2() throws InterruptedException{
		Thread temp=new Thread(
			()->{
				for(int i=0;i<1000;i++){
					System.out.println(i);
				}
			}
		);
		temp.start();
		Thread.sleep(1000);
	}
	/**
	 * ������lambda���ʽ
	 * @throws InterruptedException 
	 */
	@Test
	public void test3() throws InterruptedException{
		SwingUtilities.invokeLater(()->{
			createAndShowGUI();
		});
		Thread.sleep(100000);
	}
	private void createAndShowGUI() {
//		ȷ��һ��Ư������۷��
		JFrame.setDefaultLookAndFeelDecorated(true);
//		���������ô���
		JFrame frame=new JFrame("Temp");
		frame.setSize(680,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		��� "temp"��ǩ
		JLabel label=new JLabel("Temp");
		frame.getContentPane().add(label);
//		�������
		JButton show=new JButton("Show");
		show.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("without lambda expression");
			}
		});
		show.setBounds(10, 10, 80, 50);
		JButton showL=new JButton("ShowL");
		showL.addActionListener((e)->{
			System.out.println("lambda expression");
		});
		showL.setBounds(10,70,80,50);
		frame.add(show);
		frame.add(showL);
//		��ʾ����
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * ����
	 */
	@Test
	public void test4(){
		List<String> list=Arrays.asList("1","2","3","4");
		for(String e:list){
			System.out.println(e);
		}
		List<Integer> listL=Arrays.asList(1,2,3,4,5);
		listL.forEach(e->{
			System.out.println(e);
		});
	}
	/**
	 * predicate:����
	 */
	@Test
	public void test5(){
		List<String> languages=Arrays.asList("Java","C","C++","C#","PHP","C++","Python","Object-C");
		languages.stream()
			.filter((language)->{
				return language.length()<8;
			})
			.filter((language)->{
				return language.startsWith("C");
			})
			.distinct()//ȥ��
			.forEach(System.out::println);
	}
	/**
	 * map()/reduce()
	 */
	@Test
	public void test6(){
		List<Double> numbers=Arrays.asList(32.1,22.2,14.4,33.3);
		Double sum = numbers.stream()
			.map(
				number->number+number*0.2
			)
			.reduce(
					(total,number)->{
						return total+number;
					}
			).get();
		System.out.println(sum);
	}
	/**
	 * list����
	 * 39589,39575,39517
	 */
	@Test
	public void test7(){
		List<String> list=Arrays.asList("as","aq","zx","asd","zxc","123","312");
		List<String> listAfter=list.stream()
				.filter(e->Pattern.matches("[a-zA-Z]{3}", e))
				.collect(Collectors.toList());
		System.out.println(list);
		System.out.println(listAfter);
	}
	@Test
	public void test8(){
		List<String> list=Arrays.asList("asd","as","a","123","12","a");
		List<String> listTemp=new ArrayList<String>();
		list.stream().filter(e->{
			if(e.length()>2)
				listTemp.add(e);
			return e.length()>2;
		}).forEach(System.out::println);
		System.out.println(listTemp);
	}
	/**
	 * �̵߳ȴ� 
	 * @throws InterruptedException 
	 */
	@Test
	public void test9() throws InterruptedException{
		System.out.println("�ϰ���.");
		CountDownLatch count=new CountDownLatch(2);
		Work work1=new Work("a",count);
		Work work2=new Work("b",count);
		new Thread(work1).start();
		new Thread(work2).start();
		count.await();
		System.out.println("����������.");
	}
	class Work implements Runnable{
		private String name;
		private CountDownLatch count;
		public Work(String name,CountDownLatch count){
			this.name=name;
			this.count=count;
		}
		public void run(){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println(name+"��ʼ������.");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name+"����������");
			count.countDown();
		}
	}
}
