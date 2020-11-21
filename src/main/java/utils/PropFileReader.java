package utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PropFileReader {
	
	public static Properties urlprop = new Properties();
	public static Properties endPointProp = new Properties();
	
	public static Map<String, String> endpoints = new HashMap<String, String>();
	
	public static Map<String, String> endpoint(){
		
		try {
			FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/inputs/EndPoints.properties");
			endPointProp.load(file);
			for (Entry<Object, Object> entry : endPointProp.entrySet()) {
				endpoints.put((String) entry.getKey(), (String) entry.getValue());
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return endpoints;
	}
	
	public static String baseURL() {
		
		String baseUrl = "";
		try {
			FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/inputs/Env.properties");
			urlprop.load(file);
			String env = urlprop.getProperty("env");
			String url = urlprop.getProperty("baseurl");
			baseUrl = url.replaceAll("env", env);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return baseUrl;
	}
}
