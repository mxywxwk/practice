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
	 * ����Docģ������word�ļ�
	 * 
	 * @param dataMap
	 *            Map ��Ҫ����ģ�������
	 * @param fileName
	 *            �ļ�����
	 * @param savePath
	 *            ����·��
	 */
	public void createDoc(Map<String, Object> dataMap, String templatePath, String name){
            try{
                   //������Ҫװ���ģ��
                   Template template  = null;
                   //����ģ���ļ�
                   configure.setClassForTemplateLoading(this.getClass(),"/");
                   //���ö����װ��
                   configure.setObjectWrapper(new DefaultObjectWrapper());
                   //�����쳣������
                   configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
                   //����Template����,ע��ģ������������downloadTypeҪһ��
                   template= configure.getTemplate("demo.xml");
                   //����ĵ�
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
		data.put("title", "������");
		data.put("key1", "enen");
		data.put("value1", "dadad");
		data.put("key2", "dds");
		data.put("value2", "ɶ�ط�sad");
		WordUtil demo = new WordUtil();
		demo.createDoc(data, "ads", "sdf");
	}
}
