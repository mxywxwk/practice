package dailyWork;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

public class Crawler {
	@Test
	public void test1(){
		int a=1;
		int b=0;
		System.out.println(a|b);
		System.out.println(a|a);
		System.out.println(b|b);
		System.out.println(a^b);
		System.out.println(a^a);
		System.out.println(b^b);
		System.out.println(a&b);
		System.out.println(a&a);
		System.out.println(b&b);
	}
	/**
	 * 爬图片
	 * @throws IOException
	 */
	private int i=0;
	@Test
	public void test2() throws IOException{
//		待爬取网站url
		String url="http://www.gamersky.com";
//		获得链接
		Document doc=Jsoup.connect(url).get();
		Elements as=doc.getElementsByTag("a");
//		获取图片元素
		Elements imgs=doc.getElementsByTag("img");
		imgs.stream()
			.forEach(img->{
				InputStream is=null;
				BufferedInputStream bis=null;
				BufferedOutputStream bos=null;
				try{
//					获取该图片
					URL imgUrl=new URL(img.attr("src"));
					System.out.println("start download "+imgUrl);
					URLConnection uc=imgUrl.openConnection();
					HttpURLConnection httpUc=(HttpURLConnection) uc;
//					获取字节流
					is=httpUc.getInputStream();
//					创建本地下载文件
					File file=new File("e:/img/"+i+++".png");
					if(!file.getParentFile().exists())
						file.getParentFile().mkdirs();
					bis=new BufferedInputStream(is);
					bos=new BufferedOutputStream(new FileOutputStream(file));
					byte[] bytes=new byte[1024];
					int len=0;
					while((len=bis.read(bytes))!=-1){
						bos.write(bytes,0,len);
					}
				}catch(Exception e){
					
				}finally{
					try{
						is.close();
						bis.close();
						bos.close();
					}catch(Exception e){
						
					}
				}
				
			});
	}
	/**
	 * webmagic
	 */
	@Test
	public void test3(){
		Spider.create(new GithubRepoPageProcessor())
			.addUrl("http://www.gamersky.com")
			.setScheduler(new RedisScheduler("localhost"))
			.addPipeline(new JsonFilePipeline("e:/data/webmagic"))
			.thread(5)
			.run();
	}
	
	@Test
	public void test4(){
		Spider.create(new GithubRepoPageProcessor())
			.addUrl("https://github.com/code4craft")
//			.addPipeline(new JsonFilePipeline("e:/data/webmagic"))
			.thread(5)
			.run();
	}
	class GithubRepoPageProcessor implements PageProcessor{
		private Site site=Site.me().setRetryTimes(3).setSleepTime(10000);
		@Override
		public void process(Page page) {
			page.addTargetRequests( page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
			page.putField("author",page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
			page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
			if(page.getResultItems().get("name")==null)
				page.setSkip(true);
			page.putField("readme",page.getHtml().xpath("//div[@id='readme']/tidyText()"));
		}

		@Override
		public Site getSite() {
			return site;
		}
		
	}
	
	@Test
	public void test5(){
		Integer a=127,b=127,c=128,d=128;
		System.out.println(a==b);
		System.out.println(c==d);
	}
}
