 package dailyWork;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import util.ImageUtil;
import util.MapUtil;

public class Daily {
	/**
	 * 打印日历表的方法，格式参考电脑上面的系统时间 可以查询人一年月日的月日历； 用*标记当天
	 */
	@Test
	public void practice() {
		boolean flag = true;
		while (flag) {
			System.out.println();
			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入你要查询的日期!只允许输入格式为2017-1-1");
			String aa = scanner.nextLine();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Calendar calendar = Calendar.getInstance();
				Date date = dateFormat.parse(aa);
				System.out.println(dateFormat.format(date.getTime()));
				calendar.set(Calendar.YEAR, date.getYear() + 1900);
				calendar.set(Calendar.MONTH, date.getMonth());
				calendar.set(Calendar.DATE, date.getDate());
				System.out.println(" Sun Mon Thu Wed Thu Fri Sat");
				int today = calendar.get(Calendar.DAY_OF_MONTH);// 当天
				int month = calendar.get(Calendar.MONTH);// 当月
				int firstDayOfWeek = calendar.getFirstDayOfWeek();// 每周从周几开始
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				int firstday = calendar.get(Calendar.DAY_OF_WEEK);
				do {
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					if (day == 1) {
						System.out.print(" ");
						for (int i = 0; i < firstday - 1; i++) {
							System.out.print(" ");
						}
					}
					System.out.printf("%3s", day);
					if (day == today) {
						System.out.print("*");
					} else {
						System.out.print(" ");
					}
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					if (calendar.get(Calendar.DAY_OF_WEEK) == firstDayOfWeek) {
						System.out.println();
						System.out.print(" ");
					}
				} while (calendar.get(Calendar.MONTH) == month);
				flag = true;
			} catch (ParseException e) {
				System.out.println("你输入的格式有误请重新输入");
				flag = true;
			}
		}
	}

	/**
	 * 四种方法解析xml文件
	 */
	// 1.DOM
	@Test
	public void XMLReader1() {
		// 计时
		long start = System.currentTimeMillis();
		try {
			File xml = new File("src/main/resources/parseXML.xml");
			javax.xml.parsers.DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(xml);
			NodeList nodeList = doc.getElementsByTagName("String");
			for (int i = 0; i < nodeList.getLength(); i++) {
				System.out.print("int:"+ doc.getElementsByTagName("int").item(i).getFirstChild().getNodeValue());
				System.out.println("	double:"+ doc.getElementsByTagName("double").item(i).getFirstChild().getNodeValue());
			}
			long end = System.currentTimeMillis();
			System.out.println("用时(毫秒)：" + (end - start));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	// 2.DOM4j
	@Test
	public void XMLReader2(){
		long start=System.currentTimeMillis();
		try{
			File file=new File("src/main/resources/parseXML.xml");
			SAXReader reader=new SAXReader();
			org.dom4j.Document doc=reader.read(file);
			Element root=doc.getRootElement();
			Element foo;
			for(Iterator<Element> i=root.elementIterator("String");i.hasNext();){
				foo=i.next();
				System.out.print("int:"+foo.elementText("int"));
				System.out.println("	double:"+foo.elementText("double"));
			}
			long end = System.currentTimeMillis();
			System.out.println("用时(毫秒)：" + (end - start));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//3.JDOM
	@Test
	public void XMLReader3(){
		long start=System.currentTimeMillis();
		try{
			org.jdom2.input.SAXBuilder builder=new SAXBuilder();
			File file=new File("src/main/resources/parseXML.xml");
			org.jdom2.Document doc=(org.jdom2.Document) builder.build(file);
			org.jdom2.Element foo=(org.jdom2.Element) doc.getRootElement();
			List<org.jdom2.Element> allChildren=foo.getChildren();
			for(int i=0;i<allChildren.size();i++){
				System.out.print("int:"+(allChildren.get(i)).getChild("int").getText());
				System.out.println("	double:"+allChildren.get(i).getChild("double").getText());
			}
			long end = System.currentTimeMillis();
			System.out.println("用时(毫秒)：" + (end - start));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//4.SAX
	class SAXXMLReader extends DefaultHandler{
		java.util.Stack tags=new java.util.Stack();
		public SAXXMLReader(){
			
		}
		public void characters(char ch[],int start,int length){
			String tag=(String) tags.peek();
			if(tag.equals("int")){
				System.out.print("int:"+new String(ch,start,length));
			}
			if(tag.equals("double")){
				System.out.println("	double"+new String(ch,start,length));
			}
		}
		public void startElement(String uri,String localName,String qName,Attributes attrs){
			tags.push(qName);
			
		}
	}
	@Test
	public void XMLReader4(){
		long start=System.currentTimeMillis();
		try{
			SAXParserFactory factory=SAXParserFactory.newInstance();
			SAXParser parser=factory.newSAXParser();
			SAXXMLReader reader=new SAXXMLReader();
			parser.parse(new InputSource("src/main/resources/parseXML.xml"), reader);
		}catch(Exception r){
			r.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("用时(毫秒)：" + (end - start));
	}
	/**
	 * 创建线程的三种方法
	 */
	@Test
	public void thread1(){
		
	}
	/**
	 * 字符串转二进制实例
	 * 二进制转字符串
	 */
	@Test 
	public void temp1(){
		String secretKey = UUID.randomUUID().toString().substring(0,16);//秘钥
		String randomKey = (int)(Math.random()*90000000+10000000)+"";//8位随机数
		char[] charArray = secretKey.substring(secretKey.length()-8).toCharArray();
		char[] stringRandomKeyChar = randomKey.toCharArray();
		StringBuilder res=new StringBuilder();
		StringBuilder resBinary=new StringBuilder();
		for(int i = 0;i<charArray.length;i++){
			int temp=(((int)charArray[i])^((int)stringRandomKeyChar[i]));
			String ascii=Integer.toBinaryString(temp);
			String a =String.valueOf(temp);
			res.append(a);
			resBinary.append(ascii);
		}
		System.out.println(res);
		int ascii=Integer.parseInt(resBinary.toString());
		int decimal = 0;
	    int p = 0;
	    while(true){
	      if(ascii == 0){
	        break;
	      } else {
	          int temp = ascii%10;
	          decimal += temp*Math.pow(2, p);
	          ascii = ascii/10;
	          p++;
	       }
	    }
		System.out.println(decimal);
	}
	/**
	 * 秘钥加密
	 */
	@Test 
	public void temp2(){
		Scanner sca=new Scanner(System.in);
		int i=0;
		List<String> stringList=new ArrayList<String>();
		List<String> decimalList=new ArrayList<String>();
		while(i<2){
			System.out.print("请输入字符串 "+(i+++1)+" (最多三位)：");
			String str=sca.nextLine();
			stringList.add(str);
			decimalList.add(StringToDecimal(str));
		}
		i=0;
		while(i<2){
			System.out.println("字符串 "+(i+1)+" 为:"+stringList.get(i)+"\t十进制码为:"+decimalList.get(i)+"\t二进制为:"+Integer.toBinaryString(Integer.parseInt(decimalList.get(i++))));
		}
		String decimalRes="";
		decimalRes=String.valueOf(Integer.valueOf(decimalList.get(0))^Integer.valueOf(decimalList.get(1)));
		System.out.println("\t\t异或操作:  "+Integer.parseInt(decimalRes)+"\t二进制为:"+Integer.toBinaryString(Integer.parseInt(decimalRes)));
	}
	/**
	 * 字符串转十进制
	 * @param str
	 * @return
	 */
	public String StringToDecimal(String str){
		char[] chars=str.toCharArray();
		StringBuilder decimal=new StringBuilder();
		for(int i=0;i<chars.length;i++){
			decimal.append(String.valueOf((int)chars[i]));
		}
		return decimal.toString();
	}
	/**
	 * 字符串转二进制
	 * @param str
	 * @return
	 */
	public String StringToBinary(String str){
		char[] chars=str.toCharArray();
		StringBuilder ascii=new StringBuilder();
		for(int i=0;i<chars.length;i++){
			String temp=String.valueOf(Integer.toBinaryString((int)chars[i]));
			if(temp.length()<9){
				for(int j=0;j<9-temp.length();j++){
					temp="0"+temp;
				}
			}
			ascii.append(temp);
		}
		return ascii.toString();
	}
	/**
	 * 二进制转字符串
	 * @param str
	 * @return
	 */
	public String BinaryToString(String str){
		StringBuilder res=new StringBuilder();
		char c=0;
		for(int i=0;i<str.length()/8;i++){
			c=(char) Integer.parseInt(str.substring(8*i,8*(i+1)),2);
			res.append(c);
		}
		return res.toString();
	}
	@Test
	public void test1(){
		String s = new String("asdfa3");  
        System.out.println("原始：" + s);  
        System.out.println("加密的：" + convertMD5(s));  
        System.out.println("解密的：" + convertMD5(convertMD5(s)));
	}
	public static String convertMD5(String inStr){  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 'M');  
        }  
        String s = new String(a);  
        return s;  
	  
	}  
	@Test
	public void test2() throws Exception{
		String keyIn="asdfd11sfewdsaf";
		char[] chars = keyIn.toCharArray();  
        for (int i = 0; i < chars.length; i++){  
            chars[i] = (char) (chars[i] ^'M');  
        }  
        String keyOut = new String(chars);
         System.out.println(keyOut);
		
	}
	@Test
	public void test3(){
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("a", "1");
		maps.put("a", "2");
		maps.put("a", "3");
		maps.put("q", "q");
		maps.put("w", "d");
		System.out.println(maps.toString());
	}
	
	@Test
	public void test4() throws Exception{
		double distance=MapUtil.getDistance( "37.480563","121.467113", "37.480591", "121.467926");
		System.out.println(distance);
		byte a='a';
		System.out.println(a);
		char[] str={(char)0xFE};
		System.out.println((char)0x04==(char)0x4);
		String[] point=MapUtil.BD09ToGCJ02("39.995476","116.487556");
		System.out.println(Arrays.toString(point));
		byte[] data=new byte[2];
		System.out.println(data.length);
	}
	
	public MimeMessage getMail(Session session, String user, String receive) throws Exception{
//		链接邮件服务器的参数配置
		Properties	properties=new Properties();
//		穿件邮件对象
		MimeMessage msg=new MimeMessage(session);
//									发件人							昵称		昵称编码
		msg.setFrom(new InternetAddress(user,"MXY","UTF-8"));
//		添加收件
		msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receive,"MXC","UTF-8"));
//		msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("maxiangyi@power-peak.com.cn","MXYWY","UTF-8"));
		msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("mxywxwk@163.com","MXYWY","UTF-8"));
/*		抄送（可选）
		msg.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com", "USER_EE", "UTF-8"));
                     密送（可选）
		msg.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));*/
//		主题
		msg.setSubject("邮件测试","utf-8");
//		邮件正文（可以使用html标签）
		msg.setContent("测试内容，嘿嘿嘿...", "text/html;charset=UTF-8");
//      设置显示的发件时间
		msg.setSentDate(new Date());
//		 保存前面的设置
        msg.saveChanges();
        return msg;
	}
	@Test
	public void sendMail() throws Exception{
//		发件人信息
//		String user="mxywxwk@163.com";
//		String key="wxwk196445";
		String user="maxiangyi@power-peak.com.cn";
		String key="************";
//		发件人服务器地址
//		String mailHost="smtp.163.com";
		String mailHost="smtp.power-peak.com.cn";
//		收件人
//		String receive="mixiangchong@power-peak.com.cn";
		String receive="sunshiyun@power-peak.com.cn";
//		参数配置
		Properties	properties=new Properties();
//		使用的协议（JavaMail规范要求）
		properties.setProperty("mail.transport.protocol", "smtp");  
//		发件人的邮箱的 SMTP 服务器地址
		properties.setProperty("mail.smtp.host", mailHost); 
//		需要请求认证
		properties.setProperty("mail.smtp.auth", "true");    
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);
//      根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(properties);
//      设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);                                
        MimeMessage message = getMail(session, user, receive);
//      根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        // 
        //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
        //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
        //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //
        //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //           (1) 邮箱没有开启 SMTP 服务;
        //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        //
        //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(user, key);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
	}
	
	@Test
	public void temp() throws Exception{
		String path="e:\\test\\222222.png";
		File saveDir = new File(path); 
//		判断文件夹是否存在，不存在则建立相应文件夹            
        if (!saveDir.getParentFile().exists()){
            saveDir.getParentFile().mkdirs();
        }
        ImageUtil.GenerateImage("iVBORw0KGgoAAAANSUhEUgAAAJYAAACWCAIAAACzY+a1AAAAA3NCSVQICAjb4U/gAAAgAElEQVR4nGy9WZMl2XEm9n3uJ+JmZlVld/WOhUCTAAYAAVAgKREcQqQ4ook0UaZnkSY+4L/Nk0QzmmTiA8c0kARiBkMQIJYWNhFsNIBGr6iuqq4lM++N4/7pwU9E3m5MPnRn3Ywbyzm+fP75Evyf/6e/+Iu/+Is/+e//ZJ7njGitJRzA17/x3b/5m7/51jd/2HuHW2a++cLH//RP//ST/+OfHg793g9+cnl5+YkPPndycvLwlde/8Y1vvPqtb3/oQx/67Mf+1e3bt+eb+eEPf/hzv3N68/RmgwEQ5qvD1atvHV555ZU3X9Mbb7zx81d++vbbb+fVA5Lnv/GhL33pS8/+1gcMENCBt17v3/3ud1/5+2/97Gc/2+0fZebp0w/+8i//8t/8mxdmm8/wQYIN5wB++eDq1Vdf/b+/+uOvf/3rV1eHzHz0/Plf/dVf/cHvfPhW8+cBAAYA+Dbw6usPv/H1n3/729+eX/7/SE7BzAwPki1nksGDpIYpM5MimeokRZD0BICASDIlSQ2SmghAEsn6r5sykySAhEiaMjPNLDO9KSIcykwZM7PRMnNqnplSfVcAesrMjAuAiCBJovdOkyTISLbbt2+/+OKLy7IcDoery8tbt27tTm8CmOf59PTUzNw9CTO7devWc889F6H79++7+wsvvPCZzzx/Y4e7J0/87Gc/e9299351dZWZAM7OziJ6V5+4M5jgZnZ1dXV5eSntzKy1Ns/zfk93B2BmCTiwAwSY2b179+7fvx8R9dezs7Pbt28/evTo1q1bILafW7dObt68WWtXl5a0LEtmne/6R8DhcJAkCcC23PVFU5pZKs0sepiZNPbgeHskiYgIB0nWimsICTIzM929lrvWHcb603Z8PVTdxvHGR8S4ApkZAEiTBGL9MOuuMmO787bv+3fuv/P0o9uZOc07mgEpaNrdODk7d0P0MHhE3rx4eH71+CMP33nq8OizT+eLLz51a4cEfr67/M2n7Cez+qN3Qr++X5ad5ouLqzzsdjdvEk2A0N69d3FxD9if7ZdlCUQsEYukaZo+99EPPn/anniwf/J8J+Ai8Nr/859+9u+/enj48Caw5MHMHt2xl/7xpzefeOJqaSfnfjKdHAAAb9/V33/tpR/84EfLEsYWEQoervrlPDkQwALsgAPw8wu8ftX6O3fx7oNM9b64myRPU2b4VQiOSV1k75HkhMxkEGQHqDAJghJQwJSaBEmlSTQB8EapE5QkJh0tpUyZMmNyX5aelpJSRprUJUGMCExI5ZwJMkFJiDCzUNTJjNYVEYGSYvbMbG+++ea9e/cePXpkZo8eXwB4YmrLsvz4xz9+6aWX9vt9790bzezB48dvvfXWB/vHnnnmmeda3Lw5Abi82r/88stf+cpX7t69e35+fnl5GRH7/f7evXsPHpw9+eSTl8sVyUcP+Oqrr/7yl/tHjx5lThFhZiWDn/rUp37/93+/tbYsy+PL6Z2H9//u7/7uH/6vHz569KhlZqa5HQ6HVH7ta1/75cXh6aef/h/+61svvvgirV1eXv3bf/u/vvTSS1dX8+FwgKxUcFmWO3ce9CfP7109Ojs7u1L03u/enf/pn/7p0Y9fefjw4a1lKZtmZpmxLIuV3aNJImPTuWRKavJScQC9L+6eGXWG1eKyHioiSUamu2+qk5kR4e7Lcm0SNWxGSAJM0qr96r2nOQBFaXkHYLLyCpmJYQMiM9t+j9deu/P8Bx+cnZ21ZldXh37n4d27d7/87/7ujVd/jsUoHeKC5PTg7o//4T98+iMvfOhjH+sv3PjFvQe7+ezOnXv/+5f/449+cYft5GQ+OygOillnDx7nW+8+ef+f9w8e3XN3bzd/8dZy98ESudvrMmZcqD0Km/Lwi1df+ebX5k9+8pNPPffs4XD4yt9/7Zvf/GbGosylNzPry5WTF8vUH8c/fPOG+/LaW//xd3/36mx64vvf//7ff/vHETOapc03logeFnz7tbdOfv2Tfj944+b+Tk7eDof8zv/yv738ox+dQ6cRmmzJPFt6RMASCukkU4HHZpbyzJztkJnGBmnxPYDWvfeuhh7d02PJ3gSgRUpy9+zdlQDahGV/QdLMDpaJ9EQkhudDAgiVTFhmdiaAk3RFHBCpnBKSCGTvNrXee+AAAImIMEemjBOR/O3P/1c3btx49oWnnn/++Y985MMf+MAHbp4/9d3vfvff/59fORwOOzuNiDS01ubbL3zkIx+5/YHnf+3Xfu25p+dpmvqid9555/v/cudwOFh4a+32jSfOz8+fff45dz97sh0Oh0O/NLNpPr+6ugqdZObS9xcXF3feuPvyyy8/fu1Hh8OhTZjn+ez81rIsjx7vl2VxgyTLufduXFprZ8+88NGPfnR64kkzc9/P89x0KmlhkxTS1dXVxetvvvbaa4+fOP/0pz999qnfPD09vWS21gz5zjvvnP78nYi47bYsy+P7b7/yyit8+LD33vMAgH4iyV0R4dNJZro6AMjMLG2JiJmTpHQBwAIzS8/MnNDKuQJwpSRa1KbW8ZnpicyEW0ScNMtMGHvv7lNmyiTphL4sixyZOdE295nDF2bBgnULk2iS+Nnf+jRJSK21NM7zjCjPDADiVP759PT01qf+9fn5uTVJop/03ttsmdmjZeZueXz//v2bN5/6xCc+sTs9c/fwRvLQ9yQbuHn4vuzv3r375ve/9corr5zEIzODlz8XgAIL0EQSGZL27eTpp5/+wOf/9fn5Oc0l+e6s995AM4tYdrvdfPngRz/60S9/9sPz8/MXfvuPnnrqqW4nETGbR4Q3SNIiknNc/fM///ODl791dXVlqYigIyJmmCS5STIU3qmFttqMiGChFpokL9zRPCJAurulJHUuJGfNmRkGd8/lAMDZSIYBgCdI9gyS4MHMek+SSJlZKEk2mKRFYWZeoGYgpi5pRiuFJNlOTk5IuhkAm6eImNrk7oCRhM0RcXJy8uKLL+5+42NmZk2ZGWqttavDBYAejeTF25d379790Id+fVkW+oFkbWHPRVI9Xjm/w/7y3r179+7d2+12pwoAPrfMNAPJKCnGHBFTyezJzc985jOHmzczE4Iklt/SwIr7/f7O66+/9tprT928+bnPfe7y5s39fr9XZuZVpJmBkZnoiIhHv3z95Zdfvt07STO6u5hmVgFDGjOzrute0srSBnf3Zr33lAphAsiIii8yU5EkC98sfclMTB4RpgSQuUhSs977BHN3EZmZ2ksiPSKQAkA3AAxlJpoBODs5jYjMXJYFyIhY0CICJpL84z/+fZLdAGBHb611y2VZmhyAOSRJ026343xtLtJ7RGCxiJAyM9FjWZb55DQzfSZJk5NUKbWRZOOUmWBKQjlquqRmmKap9mPAHO+ZqcOOZOrSzCQHkEwAOzdJhNdlSMqt925qJJMZEXOhdmNmFswRsUGMrh4ROzeSGUaygyTLk6X59e8JADYFAPVrbZB1ABQy021XxhN1i5nwLsnTzSzQJWUFdc7apMxkAACVkhYqM5t52bzMVPbM3LVpt9stqf1+b1ZBCyQlFgBQy8zWWgNQYGduM8kkpmnyNHePXEhKrRBXySPJyKX3zu4REdEBMNLdD4cDyeWqm9lks6RUd3cY3f0QBwBgGY0EACIz2zxvGLXsbahLotR7B6P3nmlmVvjQpxYR2xa6++EQkhB9OyYHHM+IqC1s81SPACAQJK+WA0liiohCgA3KzKC5O3qhVphZ7K9ITnZSwBJA137bwuZ09+iHiHA2AFlPCmRmbSHkZY0BKAOAJTejXeuQwzVEKX0tyH6/l/k8z70f3N3dAGQFFWru3t5+505mujMz7/tE8hBlZIY7lWRstfRmhsjNIkuFvAMAyjqzL8sy+7wFxZBLKt4BrUma0En2khp0MzNrdU4zQzcAkfUwjzNTPCFJxObehxSzeA2QJDwi", 
				path);
		
	}
	@Test
	public void test7(){
		Gson gson=new Gson();
		List<String> list=gson.fromJson("[\"11.11\",\"32.1\",\"52.21\",\"22\"]", new TypeToken<List<String>>(){}.getType());
		System.out.println(list);
		list.add(String.format("%.2f", Math.random()*100+1));
		System.out.println(list);
	}
	/**
	 * 获取请求IP
	 * @param request
	 */
//	@Test
//	public void test8(HttpServletRequest request){
//	    String ip = request.getHeader("X-Forwarded-For");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = ip.indexOf(",");
//            if(index != -1){
//                ip=ip.substring(0,index);
//            }else{
//                ip=ip;
//            }
//        }
//        ip = request.getHeader("X-Real-IP");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//           ip=ip;
//        }
//        ip=request.getRemoteAddr();
//	}
	@Test
	public void test9(){
		System.out.println("start");
		List<Integer> lists=add(new ArrayList<Integer>());
		System.out.println(lists);
	}
	private int i=0;
	public List<Integer> add(List<Integer> lists){
		for(;i<11;i++){
			if(i<7){
				lists.add(i++);
				add(lists);
			}else
				lists.add(i);
		}
		return lists;
	}
	@Test
	public void test10(){
		
	}
}























