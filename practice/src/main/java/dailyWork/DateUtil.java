package dailyWork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class DateUtil {
	@Test
	public void test1(){
//		获取时间戳
		Instant timestamp=Instant.now();
		System.out.println(timestamp);//yyyy-MM-dd
//		获取日期
		LocalDate localDate=LocalDate.now();
		System.out.println(localDate);//yyyy-MM-dd
//		获取时间
		LocalTime localTime=LocalTime.now();
		System.out.println(localTime);//HH:mm:sss
//		获取日期时间
		LocalDateTime localDateTime=LocalDateTime.now();
		System.out.println(localDateTime);
//		时间加减操作
		LocalDateTime localDateTime2=localDateTime.plusHours(-30);
		System.out.println(localDateTime2);
		System.out.println("一年后："+localDateTime.plus(1,ChronoUnit.YEARS));
//		LocalDate/LocalTime/LocalDateTime不可变性
		System.out.println(localDateTime);
//		获取时间中的年
		System.out.println("年："+localDateTime.getYear());
//		获取时间中的月
		System.out.println("月英文："+localDateTime.getMonth());
//		获取时间中的月
		System.out.println("月数字："+localDateTime.getMonthValue());
		System.out.println("日月中："+localDateTime.getDayOfMonth());
		System.out.println("日年中："+localDateTime.getDayOfYear());
		System.out.println("星期："+localDateTime.getDayOfWeek());
		System.out.println("小时："+localDateTime.getHour());
		System.out.println("分钟："+localDateTime.getMinute());
		System.out.println("秒："+localDateTime.getSecond());
//		创建某个日期
		LocalDate date=LocalDate.of(2017, 9, 12);
		System.out.println(date);
		System.out.println(date.equals(localDate));
		Clock clock=Clock.systemUTC();
		System.out.println(clock);
		System.out.println(Clock.systemDefaultZone());
		System.out.println(clock.millis());
		System.out.println(clock.instant());
//		创建某个特殊日
		MonthDay specialDay=MonthDay.of(10, 1);
		MonthDay tempDay=MonthDay.from(localDateTime);
		System.out.println(specialDay.equals(tempDay));
//		日期比较
		LocalDateTime time1=LocalDateTime.now();
		LocalDateTime time2=time1.plus(2,ChronoUnit.MINUTES);
		System.out.println("time1比time2小："+time1.isBefore(time2));
		System.out.println("time1比time2大："+time1.isAfter(time2));
		YearMonth yearMonth=YearMonth.now();
		System.out.println(yearMonth.lengthOfMonth());
		System.out.println(yearMonth.lengthOfYear());
		System.out.println(YearMonth.of(localDate.getYear(), localDate.getMonth()).lengthOfMonth());
		System.out.println(yearMonth.isLeapYear());
		System.out.println(localDate.isLeapYear());
		Period perid=Period.between(localDate, localDate.plus(4342,ChronoUnit.DAYS));
		System.out.println(localDate);
		System.out.println(localDate.plus(4342,ChronoUnit.DAYS));
		System.out.println("两个日期相差："+perid.getYears()+"年"+perid.getMonths()+"月"+perid.getDays()+"天 ");
//		String→time
		System.out.printf("字符串  %s 变成时间了%s \n","20170616",LocalDate.parse("20170616",DateTimeFormatter.BASIC_ISO_DATE));
		DateTimeFormatter fomatter=DateTimeFormatter.ofPattern("MMddyyyy HH:mm");
		System.out.printf("%s 格式化后：%s \n","04142017 13:12",LocalDateTime.parse("04142017 13:12",fomatter));
//		tiem→String
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd MMyyyy HH:mm ");
		System.out.printf("日期 %s 变成字符串了 %s ",LocalDateTime.now(),LocalDateTime.now().format(formatter) );
		
	}
	/**
	 * java8 Stream操作
	 */
	@Test
	public void test2(){
		List<Article> articles=new ArrayList<Article>();
		List<String> tags1=new ArrayList<String>();
		List<String> tags2=new ArrayList<String>();
		List<String> tags3=new ArrayList<String>();
		tags1.add("c");
		tags1.add("Java");
		tags1.add("c++");
		tags1.add("c#");
		tags2.add("python");
		tags2.add("object");
		tags2.add("php");
		tags3.add("Java");
		tags3.add("js");
		tags3.add("python");
		tags3.add("vb");
		articles.add(new Article("mxy","demo",tags1));
		articles.add(new Article("jmlm","test",tags2));
		articles.add(new Article("mllx","test",tags3));
		List<Article> containJava=articles.stream()
				.filter(e -> {
					System.out.println(e);
					return !e.getTags().contains("Java");
					})
				.collect(Collectors.toList());
		System.out.println(containJava.toString());
	}
	class Article{
		private String author;
		private String title;
		private List<String> tags;
		public Article() {
			super();
		}
		@Override
		public String toString() {
			return "Article [author=" + author + ", title=" + title + ", tags="
					+ tags + "]";
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public List<String> getTags() {
			return tags;
		}
		public void setTags(List<String> tags) {
			this.tags = tags;
		}
		public Article(String author, String title, List<String> tags) {
			super();
			this.author = author;
			this.title = title;
			this.tags = tags;
		}
	}
	@Test
	public void test3(){
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
	}
}
