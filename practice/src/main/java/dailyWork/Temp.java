package dailyWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Temp {
	@Test
	public void createData(){
		String driver="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@192.168.99.12:1521:orcl";
		String user="nfclock";
		String key="nfclock";
		String[] orgId={
					"fb687d99-d20c-4539-a5fc-4af140bd945e","d94ed481-2bcc-4b35-9cf3-fcc95f71e7ed",//国家电网；石家庄
					"df8ab3ec-477a-43cd-b04a-079d2c2cf664","c37d8d0c-5841-4e52-95cb-171bcc4a4da6",//唐山；河北
					"a930ad5e-0334-4025-923f-b9bfc7d5ffde","3329d6a1-0d01-4cec-8157-0bd8a3403358",//保定；北京
					"7bfd0eea-657f-4a71-a8e7-366f267839e3","8925c9c5-10ab-4c7e-89b4-346d63e4629a",//天津；山西
					"43dc3f79-5805-4d78-af0c-1a3a2b5cbfe4","70b54d10-fe0f-4484-81e6-64ac464e4eed",//山东；河南
					};
		String[] roadName={
					"东长安街","中山路",
					"长宁西路","裕华路",
					"七一西路","地安门大街",
					"咸阳路","兴华街",
					"经十路","金水路",
					};
		double[] pointX={
//					 39.914465	38.049422
					 116.300000,114.483728,
//					 39.672346	38.041636
					 118.156647,114.483763,
//					 38.887996	39.939826	
					 115.456161,116.396763,
//					 39.143986  37.893481
					 117.135224,112.530896,
//					 36.654695  34.770169
					 116.967232,113.663264
					};
		double[] pointY={
				39.914465,38.049422,
				39.672346,38.041636,
				38.887996,39.939826,
				39.143986,37.893481,
				36.654695,34.770169
				};
		Connection con=null;
		PreparedStatement ps;
		ResultSet res=null;
		try{
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,key);
			String sql="insert into NFC_EQUIPMENT (EQUIPMENT_ID,EQUIPMENT_NAME,EQUIPMENT_ADDR,EQUIPMENT_TYPE,ORG_ID,CREATE_DATE,LINE_ID,TG_ID,POS_X,POS_Y) values"
					+ 								" (?,?,?,?,?,sysdate,?,?,?,?)";
			ps=con.prepareStatement(sql);
			for(int i=0;i<orgId.length;i++){
				for(int j=1;j<11;j++){
					for(int k=1;k<11;k++){
						for(int equipmentId=1;equipmentId<11;equipmentId++){
							ps.setString(1, String.valueOf(1000*i+100*(j-1)+10*(k-1)+equipmentId));//设备Id
							ps.setString(2,String.valueOf(1000*i+100*(j-1)+10*(k-1)+equipmentId) );
							String x=String.valueOf(((double)k)/10+((double)equipmentId)/100+pointX[i]);
							String y=String.valueOf(pointY[i]+((double)equipmentId)/10);
							ps.setString(3, roadName[i]+String.valueOf(100*(j-1)+10*(k-1)+equipmentId));
							ps.setString(4, "0"+String.valueOf((int)(Math.random()*4)+1));
							ps.setString(5, orgId[i]);
							ps.setString(6, String.valueOf(10*i+j));//lineId
							ps.setString(7, String.valueOf(100*i+10*(j-1)+k));//tgId
							ps.setString(8,x);
							ps.setString(9,y);
							ps.executeUpdate();
							System.out.println("设备："+String.valueOf(1000*i+100*(j-1)+10*(k-1)+equipmentId)+"已经插入");
						}
					}
				}
			}
		}catch(Exception e){
			
		}finally{
			if(res!=null&&con!=null){
				try {
					res.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Test
	public void test2() throws Exception{
		System.out.println(getNameByPoint("39.914465,116.300000"));
	}
	public String getNameByPoint(String point) throws Exception{
		JSONObject json = readJsonFromUrl("http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location="+point+"&output=json&pois=1&ak=GyWNLZI5j5G82rzMAA8KsWMKsYctxa9N");
		return (String)((JSONObject) json.get("result")).get("formatted_address");  
	}
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {  
	    InputStream is = new URL(url).openStream();  
	    try {  
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));  
	      String jsonText = readAll(rd);
	      jsonText=jsonText.substring(jsonText.indexOf("{"),jsonText.length()-1);
	      JSONObject json = new JSONObject(jsonText);  
	      return json;  
	    } finally {  
	      is.close();  
	    }  
	  }  
	 private static String readAll(Reader rd) throws IOException {  
		    StringBuilder sb = new StringBuilder();  
		    int cp;  
		    while ((cp = rd.read()) != -1) {  
		      sb.append((char) cp);  
		    }  
		    return sb.toString();  
		  }  
}
