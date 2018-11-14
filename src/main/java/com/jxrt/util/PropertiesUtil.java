package com.jxrt.util;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PropertiesUtil {

	public static String filePath = "./src/main/java/system.properties";
	public static PropertiesHelper prop = getProperties();
	
	public static PropertiesHelper getProperties() {
		PropertiesHelper prop = new PropertiesHelper();
		try {
			FileInputStream file = new FileInputStream(filePath);
			prop.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static String getOptValue(String key) {
		String str = prop.getProperty(key);
		String res = "";
		try {
			res = new String(str.getBytes("ISO-8859-1"),"UTF-8");
//			res = new String(str.getBytes("UTF-8"),"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		System.out.println(timeString() + key + " : " + res);
		return res;
	}
	
	public static void setOptValue(String key, String value) {
		prop.setProperty(key, value);
	}
	
	public static void store() {
		prop.store(filePath, "UTF-8");
	}

	private static String timeString() {
		Date currentTime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = "[" + formatter.format(currentTime) + "] ";
		return timeString;
	}
	
	public static long stringTime(String str){
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}
}
