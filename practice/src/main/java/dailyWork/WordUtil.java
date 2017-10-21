package dailyWork;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class WordUtil {
	private Configuration configure = null;

	public WordUtil() {
		configure = new Configuration();
		configure.setDefaultEncoding("utf-8");
	}

	/**
	 * 根据Doc模板生成word文件
	 * 
	 * @param dataMap
	 *            Map 需要填入模板的数据
	 * @param fileName
	 *            文件名称
	 * @param savePath
	 *            保存路径
	 */
	public void createDoc(Map<String, Object> dataMap, String templatePath, String name){
            try{
                   //加载需要装填的模板
                   Template template  = null;
                   //加载模板文件
                   configure.setClassForTemplateLoading(this.getClass(),"/");
                   //设置对象包装器
                   configure.setObjectWrapper(new DefaultObjectWrapper());
                   //设置异常处理器
                   configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
                   //定义Template对象,注意模板类型名字与downloadType要一致
                   template= configure.getTemplate("demo.xml");
                   //输出文档
                   File outFile = new File("d:\\word\\"+name+".doc");
                   if(!outFile.getParentFile().exists()){
                	   outFile.getParentFile().mkdirs();
                   }
                   Writer out = null;
                   out= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));                                   
                   template.process(dataMap,out);
                   outFile.delete();
            }catch (Exception e) {
                   e.printStackTrace();
            }
     }

	public static void main(String[] args) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("title", "啦啦啦");
		data.put("key1", "enen");
		data.put("value1", "dadad");
		data.put("key2", "dds");
		data.put("value2", "啥地方sad");
		WordUtil demo = new WordUtil();
		demo.createDoc(data, "ads", "sdf");
	}
}
