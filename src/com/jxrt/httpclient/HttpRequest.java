package com.jxrt.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

public class HttpRequest {
	
	//添加默认header
	public static void  addCommHeader(HttpRequestBase request){
			Map<String, String> header =  new HashMap<String, String>(){
				private static final long serialVersionUID = 1L;
				{
					put("accept", "*/*");
					put("connection", "Keep-Alive");
					put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				}
			};
			
			for(Iterator<String> it= header.keySet().iterator(); it.hasNext();){
				String key = it.next();
				request.setHeader(key, header.get(key));
			}
	}
	
	/**
	 * PUT
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPut(String url, String param) throws UnsupportedEncodingException{
		ResultHelper.callType = "Put";
		int httpCode = 200;
		//CloseableHttpClient httpclient = HttpClients.createDefault();  
		HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(TestBase.cookieStore).build();
		HttpPut putMethod = new HttpPut(url);
		System.out.println("url:" + url);
		if(param != null){
			System.out.println("params:" + param);
			StringEntity entity = new StringEntity(param, "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			putMethod.setEntity(entity);
		}
		addCommHeader(putMethod);
		String resData = "";
		try {
			HttpResponse result = httpClient.execute(putMethod);
			httpCode = result.getStatusLine().getStatusCode();
			// 请求结束，返回结果
			resData = EntityUtils.toString(result.getEntity());  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			putMethod.releaseConnection();
			httpClient.getConnectionManager().shutdown();
		}
		return ResultHelper.addStatuCode(resData, httpCode);
//        JSONObject resJson = json.parseObject(resData);  
//        String code = resJson.get("result_code").toString(); // 对方接口请求返回结果：0成功  1失败  
//        logger.info("请求返回结果集{'code':" + code + ",'desc':'" + resJson.get("result_desc").toString() + "'}");  
	}
	
	/**
	 * DELETE
	 * @param url
	 * @param header
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String sendDelete(String url, Map<String,String> header){
		ResultHelper.callType = "Delete";
		int httpCode = 200;
		String resData = "";
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(TestBase.cookieStore).build();
		HttpDelete deleteMethod = new HttpDelete(url);
		System.out.println("url:" + url);
		addCommHeader(deleteMethod);
		for(Iterator<String> it = header.keySet().iterator();it.hasNext();){
			String key = it.next();
			deleteMethod.setHeader(key, header.get(key));
		}
		try{
			HttpResponse result = httpClient.execute(deleteMethod);
			httpCode = result.getStatusLine().getStatusCode();
			// 请求结束，返回结果
			resData = EntityUtils.toString(result.getEntity());  
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			deleteMethod.releaseConnection();
			httpClient.getConnectionManager().shutdown();
		}
		return ResultHelper.addStatuCode(resData, httpCode);
	}
	
	public static String sendGet(String url, String param, Map<String, String> header) {
		ResultHelper.callType = "Get";
		String result = "";
		int httpCode = 200;
//		HttpClient httpClient = HttpClients.createDefault();
		HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(TestBase.cookieStore).build();
		String urlNameString = url;
		if(param!=null){
			urlNameString += "?" + param;
		}
		System.out.println("urlNameString:" + urlNameString);
		HttpGet httpget = new HttpGet(urlNameString); 
		httpget.setHeader("Charset", "UTF-8");
		
		HttpResponse response;
		try {
			response = httpClient.execute(httpget);
			httpCode = response.getStatusLine().getStatusCode();
			String responserHeader = response.getHeaders("Content-Type")[0].getValue();
			if (responserHeader.contains("image"))
				result = null;
			else if (responserHeader.contains("application/octet-stream"))
				result = null;
			else{
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity); 
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			httpCode = ResultHelper.connectFailedCode;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpget.releaseConnection();
			httpClient.getConnectionManager().shutdown();
		}
		if (result.contains("<!DOCTYPE HTML>")) {
			result = null;
		}
		return ResultHelper.addStatuCode(result, httpCode);
	}
	
	public static String sendPost(String url, String param, Map<String,String> header) throws UnsupportedEncodingException{
		ResultHelper.callType = "Post";
		String result = "";
		int httpCode = 200;
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(TestBase.cookieStore).build();
		System.out.println("url:" + url);
		System.out.println("param:" + param);
		HttpPost httppost = new HttpPost(url);
		addCommHeader(httppost);
		httppost.setHeader("Charset", "UTF-8");
		
		if(param != null){
			if(header != null && header.containsKey("Content-Type")){
				httppost.setHeader("Content-Type", header.get("Content-Type"));
				
				if(header.get("Content-Type").equalsIgnoreCase("application/json")){
					StringEntity stringEntity = new StringEntity(param.toString(),"UTF-8");//解决中文乱码问题  
					stringEntity.setContentEncoding("UTF-8");  
					stringEntity.setContentType("application/json");
					httppost.setEntity(stringEntity);
				}
				
			}else{
				httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				StringEntity stringEntity = new StringEntity(param.toString(),"UTF-8");
				stringEntity.setContentEncoding("UTF-8");  
				httppost.setEntity(stringEntity);
			}
		}
		
		HttpResponse response;
		try {
			response = httpClient.execute(httppost);
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);  
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			httpCode = ResultHelper.connectFailedCode;
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httppost.releaseConnection();
			httpClient.getConnectionManager().shutdown();
		}
		
		return ResultHelper.addStatuCode(result, httpCode);
	}
}