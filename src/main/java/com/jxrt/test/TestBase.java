package com.jxrt.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.openqa.selenium.WebDriver;

import com.jxrt.biz.Biz;
import com.jxrt.dbOperate.DBOperate;
import com.jxrt.dbutil.DBUtil;
import com.jxrt.dbutil.DataFactory;
import com.jxrt.util.Browser;
import com.jxrt.util.PropertiesHelper;
import com.jxrt.util.PropertiesUtil;

import net.sf.json.JSONObject;


public class TestBase {
	public static  WebDriver driver = null;
	public static  String url = PropertiesUtil.getOptValue("host");
	public static  String browserType = PropertiesUtil.getOptValue("browserType");
	public static String baseDir = System.getProperty("user.dir");
	public static Biz biz;
	
	public static String priority = PropertiesUtil.getOptValue("priority");	

	public static String rerunCaseFilePath = baseDir + "/rerunCase.txt";
	public static File rerunFile = null;
	public static File unLockFile = null;
	public static List<String> rerunCaseList = new ArrayList<String>();

	public static DataFactory dataFactory = null;
	public static Boolean isReRun = Boolean.valueOf(PropertiesUtil.getOptValue("rerun"));
	public static String protocolType = PropertiesUtil.getOptValue("protocol_type");
	public static String dbType = PropertiesUtil.getOptValue("db_type");
	public static String serverIp = PropertiesUtil.getOptValue("server_ip");
	public static String dbIp = DBUtil.oracle_ip;
	public static String requestUrl = protocolType + "://" + serverIp+"/";

	public static String userId = "";
	public static long groupCode = 10;
	public static long subGroupCode = 10;

	public final static int CREATE_SUCCESS_CODE = 201;
	public final static int SUCCESS_CODE = 200;
	public final static int FAIL_CODE = 1;

	public final static String P1 = "P1";
	public final static String P2 = "P2";
	public final static String P3 = "P3";

	public static String caseId = "000";

	public static DBOperate dbOperate = new DBOperate();
	
	public static List<Cookie> cookies; 
	public static CookieStore cookieStore = new BasicCookieStore();

	// 写rerun.xml
	public static String testName = "";
	public static String className = "";
	public static List<String> methodList = new ArrayList<String>();
	public static boolean writeTest = true;

	/*
	 * 浏览器初始化
	 */
	public static void setupBiz() {
		driver = Browser.getDriver(browserType,url);
		biz = new Biz(driver);

	}
	/*
	 * 浏览器退出
	 */
	public static void tearDownBiz() {
		if(driver != null) {
			driver.quit();
		}
	}

}
