package com.jxrt.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import com.jxrt.test.TestBase;
import com.jxrt.util.RSAUtils;
import com.jxrt.util.ResultHelper;
public class UaaApi extends TestBase  {
	public static List<Cookie> cookies; 
	
	public static void main(String [] args) {
//		loginCorp(TestBase.liantiaoCorpName,TestBase.liantiaoCorpMobile, TestBase.liantiaoCorpPass, TestBase.loginCorpWeb);
//		CorpApi.queryProductState();
//		String corpId = BizCorpApi.searchCorp("你拉开松井大辅");
//		logoutCorp();
//		login("13581978538", "a1111111", "CCBSCF_BUSINESS_WEB");
//		String corpInfo = BizCorpApi.searchCorp("你拉开松井大辅");
//		BizCorpApi.authResiteredCorp(corpInfo);
//		logout("/biz/login");
		
	}

	/**
	 *  获取cookies, 测试用
	 */
	public static List<Cookie> getCookies() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(requestUrl + "uaa/token");
        HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			//System.out.println(response.getAllHeaders());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        return cookies;
	}
	
	/**
	 * 获取public key
	 * 
	 * 
	 * @return csrf token
	 */
	public static String getPubkey() {
		String csrfToken = "";
		String request = requestUrl + "api/web/v1/auth/passwords/public-keys";

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			System.out.println(result);
			String data = ResultHelper.getJsonValueByKey(result, "data");
			result = data;
		}
		return result;
	}
	
	
	/**
	 * 获取token
	 * 
	 * 
	 * @return csrf token
	 */
	public static String getToken() {
		String csrfToken = "";
		String request = requestUrl + "uaa/token";

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		System.out.println("Cookies:\n" + TestBase.cookieStore.getCookies());
		if ("200".equals(code)) {
			System.out.println(result);
			String data = ResultHelper.getJsonValueByKey(result, "data");
			String token = ResultHelper.getJsonValueByKey(data, "token");
			csrfToken = token;
//			PropertiesUtil.setOptValue("csrf_token", token);
//			PropertiesUtil.store();
//			csrfToken = PropertiesUtil.getOptValue("csrf_token");
		}
		return csrfToken;
	}
	
	
	/**
	 * 登陆平台端
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	public static void login(String mobile, String bizpass, String type) {
		String csrfToken = getToken();
		String request = requestUrl + "uaa/login";
		String params = "";
		if(type.equals("CCBSCF_BUSINESS_WEB")) {
			String pubKey = getPubkey();
			String password = "";
			try {
				password = RSAUtils.encryptByPublicKey(pubKey, bizpass);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params = "mobile=" + mobile + "&bizPass="+ URLEncoder.encode(password) + "&redirect_uri=" + "&_csrf=" + csrfToken + "&type=" + type;
		}else {
			params = "mobile=" + mobile + "&bizPass="+ bizpass + "&redirect_uri=" + "&_csrf=" + csrfToken + "&type=" + type;
		}
		
		String result = "";

		result = ResultHelper.sendPostRequest(protocolType, request, params, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("302".equals(code)) {
				System.out.println("Login Success");
		}
	}
	
	/**
	 * 登陆企业端
	 */
	public static void loginCorp(String corpName, String mobile, String bizpass, String type) {
		String csrfToken = getToken();
		String request = requestUrl + "uaa/login";
		String params = null;
		try {
			params = "name=" + URLEncoder.encode(corpName,"utf-8") + "&mobile=" + mobile + "&bizPass="+ bizpass + "&passwordRandom=&verifyCode=&redirect_uri=" + "&_csrf=" + csrfToken + "&type=" + type;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = "";

		result = ResultHelper.sendPostRequest(protocolType, request, params, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("302".equals(code)) {
				System.out.println("Login Success");
		}
	}
	
	/**
	 * logout平台端
	 */
	public static void logout(String rediret_url) {
		String request = requestUrl + "uaa/logout" + "?redirect_url=" + rediret_url;

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		//System.out.println(TestBase.cookieStore.getCookies());
		if ("200".equals(code)) {
//			System.out.println(result);
		}
	}
	
	/**
	 * logout企业端
	 */
	public static void logoutCorp() {
		String request = requestUrl + "uaa/logout" ;

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		System.out.println(TestBase.cookieStore.getCookies());
		if ("200".equals(code)) {
//			System.out.println(result);
		}
	}
}
