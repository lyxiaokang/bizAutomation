package com.jxrt.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
	public static  String BizUrl = "http://"+PropertiesUtil.getOptValue("server_ip")+"/biz/login";
	public static  String browserType = PropertiesUtil.getOptValue("browserType");
	public static String baseDir = System.getProperty("user.dir");
	public static String downloadsPath = baseDir+"\\Downloads";
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
	
	//账款核心链条
	public static String limitPkDetailReceivableTeam2;
	public static String fkCorpCoreReceivableTeam2;
	public static String corpNameCoreReceivableTeam2;
	public static String fkCorpReceivableTeam2;
	public static String corpNameReceivableTeam2;
	//产品运营经办主管
	public static String productOperatorNameTeam2;
	public static String productOperatorMobileTeam2;
	public static String productOperatorPasswordTeam2;
	public static String productManagerNameTeam2;
	public static String productManagerMobileTeam2;
	public static String productManagerPasswordTeam2;
	public static String operateOperatorNameTeam2;
	public static String operateOperatorMobileTeam2;
	public static String operateOperatorPasswordTeam2;
	public static String operateManagerNameTeam2;
	public static String operateManagerMobileTeam2;
	public static String operateManagerPasswordTeam2;
	public static String operateQueryNameTeam2;
	public static String operateQueryMobileTeam2;
	public static String operateQueryPasswordTeam2;
	
	static{
		System.out.println("初始化"+PropertiesUtil.getOptValue("server_ip")+"环境企业信息");
		getCorpReceivable();
		getProductOperate();
	}
	//判断所处环境确认账款所用企业：
	public static void getCorpReceivable(){
	switch(PropertiesUtil.getOptValue("server_ip"))
	{
		case "test1.ccbscf.com":
			fkCorpCoreReceivableTeam2 = PropertiesUtil.getOptValue("fkCorpCoreReceivableTeam2_t1");
			corpNameCoreReceivableTeam2 = PropertiesUtil.getOptValue("corpNameCoreReceivableTeam2_t1");
			fkCorpReceivableTeam2 = PropertiesUtil.getOptValue("fkCorpReceivableTeam2_t1");
			corpNameReceivableTeam2 = PropertiesUtil.getOptValue("corpNameReceivableTeam2_t1");
			break;
		case "test2.ccbscf.com":
			fkCorpCoreReceivableTeam2 = PropertiesUtil.getOptValue("fkCorpCoreReceivableTeam2_t2");
			corpNameCoreReceivableTeam2 = PropertiesUtil.getOptValue("corpNameCoreReceivableTeam2_t2");
			fkCorpReceivableTeam2 = PropertiesUtil.getOptValue("fkCorpReceivableTeam2_t2");
			corpNameReceivableTeam2 = PropertiesUtil.getOptValue("corpNameReceivableTeam2_t2");
			break;
		case "test4.ccbscf.com":
			fkCorpCoreReceivableTeam2 = PropertiesUtil.getOptValue("fkCorpCoreReceivableTeam2_t4");
			corpNameCoreReceivableTeam2 = PropertiesUtil.getOptValue("corpNameCoreReceivableTeam2_t4");
			fkCorpReceivableTeam2 = PropertiesUtil.getOptValue("fkCorpReceivableTeam2_t4");
			corpNameReceivableTeam2 = PropertiesUtil.getOptValue("corpNameReceivableTeam2_t4");
			break;
		}
	}
	//判断所处环境确认运营产品经办主管：
	public static void getProductOperate(){
	switch(PropertiesUtil.getOptValue("server_ip"))
	{
		case "test1.ccbscf.com":
			productOperatorNameTeam2 = PropertiesUtil.getOptValue("productOperatorNameTeam2_t1");
			productOperatorMobileTeam2 = PropertiesUtil.getOptValue("productOperatorMobileTeam2_t1");
			productOperatorPasswordTeam2 = PropertiesUtil.getOptValue("productOperatorPasswordTeam2_t1");
			productManagerNameTeam2 = PropertiesUtil.getOptValue("productManagerNameTeam2_t1");
			productManagerMobileTeam2 = PropertiesUtil.getOptValue("productManagerMobileTeam2_t1");
			productManagerPasswordTeam2 = PropertiesUtil.getOptValue("productManagerPasswordTeam2_t1");
			operateOperatorNameTeam2 = PropertiesUtil.getOptValue("operateOperatorNameTeam2_t1");
			operateOperatorMobileTeam2 = PropertiesUtil.getOptValue("operateOperatorMobileTeam2_t1");
			operateOperatorPasswordTeam2 = PropertiesUtil.getOptValue("operateOperatorPasswordTeam2_t1");
			operateManagerNameTeam2 = PropertiesUtil.getOptValue("operateManagerNameTeam2_t1");
			operateManagerMobileTeam2 = PropertiesUtil.getOptValue("operateManagerMobileTeam2_t1");
			operateManagerPasswordTeam2 = PropertiesUtil.getOptValue("operateManagerPasswordTeam2_t1");
			operateQueryNameTeam2 = PropertiesUtil.getOptValue("operateQueryNameTeam2_t1");
			operateQueryMobileTeam2 = PropertiesUtil.getOptValue("operateQueryMobileTeam2_t1");
			operateQueryPasswordTeam2 = PropertiesUtil.getOptValue("operateQueryPasswordTeam2_t1");
			break;
		case "test2.ccbscf.com":
			productOperatorNameTeam2 = PropertiesUtil.getOptValue("productOperatorNameTeam2_t2");
			productOperatorMobileTeam2 = PropertiesUtil.getOptValue("productOperatorMobileTeam2_t2");
			productOperatorPasswordTeam2 = PropertiesUtil.getOptValue("productOperatorPasswordTeam2_t2");
			productManagerNameTeam2 = PropertiesUtil.getOptValue("productManagerNameTeam2_t2");
			productManagerMobileTeam2 = PropertiesUtil.getOptValue("productManagerMobileTeam2_t2");
			productManagerPasswordTeam2 = PropertiesUtil.getOptValue("productManagerPasswordTeam2_t2");
			operateOperatorNameTeam2 = PropertiesUtil.getOptValue("operateOperatorNameTeam2_t2");
			operateOperatorMobileTeam2 = PropertiesUtil.getOptValue("operateOperatorMobileTeam2_t2");
			operateOperatorPasswordTeam2 = PropertiesUtil.getOptValue("operateOperatorPasswordTeam2_t2");
			operateManagerNameTeam2 = PropertiesUtil.getOptValue("operateManagerNameTeam2_t2");
			operateManagerMobileTeam2 = PropertiesUtil.getOptValue("operateManagerMobileTeam2_t2");
			operateManagerPasswordTeam2 = PropertiesUtil.getOptValue("operateManagerPasswordTeam2_t2");
			operateQueryNameTeam2 = PropertiesUtil.getOptValue("operateQueryNameTeam2_t2");
			operateQueryMobileTeam2 = PropertiesUtil.getOptValue("operateQueryMobileTeam2_t2");
			operateQueryPasswordTeam2 = PropertiesUtil.getOptValue("operateQueryPasswordTeam2_t2");
			break;
		case "test4.ccbscf.com":
			productOperatorNameTeam2 = PropertiesUtil.getOptValue("productOperatorNameTeam2_t4");
			productOperatorMobileTeam2 = PropertiesUtil.getOptValue("productOperatorMobileTeam2_t4");
			productOperatorPasswordTeam2 = PropertiesUtil.getOptValue("productOperatorPasswordTeam2_t4");
			productManagerNameTeam2 = PropertiesUtil.getOptValue("productManagerNameTeam2_t4");
			productManagerMobileTeam2 = PropertiesUtil.getOptValue("productManagerMobileTeam2_t4");
			productManagerPasswordTeam2 = PropertiesUtil.getOptValue("productManagerPasswordTeam2_t4");
			operateOperatorNameTeam2 = PropertiesUtil.getOptValue("operateOperatorNameTeam2_t4");
			operateOperatorMobileTeam2 = PropertiesUtil.getOptValue("operateOperatorMobileTeam2_t4");
			operateOperatorPasswordTeam2 = PropertiesUtil.getOptValue("operateOperatorPasswordTeam2_t4");
			operateManagerNameTeam2 = PropertiesUtil.getOptValue("operateManagerNameTeam2_t4");
			operateManagerMobileTeam2 = PropertiesUtil.getOptValue("operateManagerMobileTeam2_t4");
			operateManagerPasswordTeam2 = PropertiesUtil.getOptValue("operateManagerPasswordTeam2_t4");
			operateQueryNameTeam2 = PropertiesUtil.getOptValue("operateQueryNameTeam2_t4");
			operateQueryMobileTeam2 = PropertiesUtil.getOptValue("operateQueryMobileTeam2_t4");
			operateQueryPasswordTeam2 = PropertiesUtil.getOptValue("operateQueryPasswordTeam2_t4");
			break;
		}
	}

	public static String ReceivableApproveNoPassPkCredit;
	public static String ReceivableApproveNoPassApplyAmount;
	
	//用于存储白条申请号和白条号
	public static String pkApplys=null;
	public static String pkCredit=null;
	
	//融资资料审核初始化数据
	public static String FinanceDataApproveCorpName="太平链条企业一";
	public static String FinanceDataApproveCorpNameCore="盛世集团成员二";
	/*
	 * 浏览器初始化
	 */
	public static void setupBiz() {
		driver = Browser.getDriver(browserType,BizUrl);
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
	
	// json header
	public static Map<String, String> jsonheader = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put("Content-Type", "application/json");
			put("charset", "UTF-8");
		}
	};
	
	public static String generateDateTime() {
		DateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dt.format(System.currentTimeMillis());
	}
	
	public static String generateDateTime2() {
		DateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
		return dt.format(System.currentTimeMillis());
	}

	public static String generateDateTime(int delay) {
		DateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dt.format(System.currentTimeMillis() + delay * 60 * 1000);
	}
	
    public static void getScreenShot(String cls, String methd) {
    	File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    String fileName =  TestBase.generateDateTime2() + methd  + ".jpg";
            try {
                FileUtils.copyFile(srcFile, new File(TestBase.baseDir + "/screenshots" + "/"+fileName));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

}
