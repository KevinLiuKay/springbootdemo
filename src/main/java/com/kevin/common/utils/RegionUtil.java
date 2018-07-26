package com.kevin.common.utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.kevin.common.GlobalConstant.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 地区工具类
 * 
 * @author Tiger Mo
 * @create 2016.04.13
 */
public class RegionUtil {
	
	private static Logger logger = LoggerFactory.getLogger(RegionUtil.class);
	
	public static Map<String,String>  getRefMap() {
		Map<String,String> refMap = new HashMap<String, String>();
		try {
			//ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
			//File f = new File("D:\\provCityArea.min.json");
			InputStreamReader read =  new InputStreamReader(RegionUtil.class.getResourceAsStream("/provCityArea.min.json"), GlobalConstant.UTF_8);
			//new InputStreamReader(new FileInputStream(f), GlobalConstant.ENCODING_UTF_8);
			BufferedReader br=new BufferedReader(read);
			String line="";
			StringBuffer  buffer = new StringBuffer();
			while((line=br.readLine())!=null){
				buffer.append(line);
			}
			String fileContent = buffer.toString();
			logger.debug("-----> /provCityArea.min.json  fileContent : " + fileContent);
			
			JSONArray provArray = JSON.parseArray(fileContent);
			for(Object provObj : provArray.toArray()){
				JSONObject prov = (JSONObject)provObj;
				String v = prov.getString("v");
				String n = prov.getString("n");
				JSONArray cityArray = prov.getJSONArray("s");
				for(Object cityObj : cityArray.toArray()){
					JSONObject city = (JSONObject)cityObj;
					String vc = city.getString("v");
					String nc = city.getString("n");
					JSONArray areaArray = city.getJSONArray("s");
					for(Object areaObj : areaArray.toArray()){
						JSONObject area = (JSONObject)areaObj;
						String va = area.getString("v");
						String na = area.getString("n");
						refMap.put(va, na+"_"+vc+"_"+nc+"_"+v+"_"+n);
					}
				}
			}
			if(br!=null){
				br.close();
			}
			if(read!=null){
				read.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return refMap;
	}
}
