package com.jxrt.httpclient;

import java.util.Map;
import javax.net.ssl.SSLContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.jxrt.util.ResultHelper;

public class HttpsRequest {

	static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
		public static final String METHOD_NAME = "DELETE";

		public String getMethod() {
			return METHOD_NAME;
		}

		public HttpDeleteWithBody(String uri) {
			super();
			setURI(URI.create(uri));
		}

		public HttpDeleteWithBody() {
			super();
		}
	}

	public static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
							new String[] { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3"},
							null,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static String sendDelete(String url) {
		ResultHelper.callType = "Delete";
		String result = "";
		int httpCode = 200;
		HttpClient httpClient = createSSLInsecureClient();
		HttpDelete httpdel = new HttpDelete(url);
		httpdel.setHeader("Charset", "UTF-8");
		httpdel.setHeader("Content-Type", "application/json");
		System.out.println("url:" + url);
		HttpResponse response;
		try {

			response = httpClient.execute(httpdel);
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResultHelper.addStatuCode(result, httpCode);
	}

	public static String sendDelete(String url, String params) throws UnsupportedEncodingException {
		ResultHelper.callType = "Delete";
		String result = "";
		int httpCode = 200;
		HttpClient httpClient = createSSLInsecureClient();
		HttpDeleteWithBody httpdeletet = new HttpDeleteWithBody(url);
		httpdeletet.setHeader("Charset", "UTF-8");
		if (params != null) {// param json处理
			StringEntity stringEntity = new StringEntity(params, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpdeletet.setEntity(stringEntity);
		}
		System.out.println("url:" + url);
		System.out.println("params:" + params);
		HttpResponse response;
		try {
			response = httpClient.execute(httpdeletet);
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultHelper.addStatuCode(result, httpCode);
	}

	public static String sendPut(String url, String params) throws UnsupportedEncodingException{
		ResultHelper.callType = "Put";
		String result = "";
		int httpCode = 200;
		HttpClient httpClient = createSSLInsecureClient();
		HttpPut httpput = new HttpPut(url);
		httpput.setHeader("Charset", "UTF-8");
		if (params != null) {// param json处理
			StringEntity stringEntity = new StringEntity(params, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpput.setEntity(stringEntity);
		}
		System.out.println("url:" + url);
		System.out.println("params:" + params);
		HttpResponse response;
		try {
			response = httpClient.execute(httpput);
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultHelper.addStatuCode(result, httpCode);
	}

	public static String sendPost(String url, String param, Map<String, String> header) throws UnsupportedEncodingException {
		ResultHelper.callType = "Post";
		String result = "";
		int httpCode = 200;
		HttpClient httpClient = createSSLInsecureClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Charset", "UTF-8");

		if(param != null){
			if(header!=null && header.containsKey("Content-Type")){
				httpPost.setHeader("Content-Type", header.get("Content-Type"));
				if(header.get("Content-Type").equalsIgnoreCase("application/json")){
					StringEntity stringEntity = new StringEntity(param.toString(),"UTF-8");//解决中文乱码问题  
					stringEntity.setContentEncoding("UTF-8");  
					stringEntity.setContentType("application/json");
					httpPost.setEntity(stringEntity);
				}
			}else{
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				StringEntity stringEntity = new StringEntity(param.toString(),"UTF-8");
				stringEntity.setContentEncoding("UTF-8");  
				httpPost.setEntity(stringEntity);
			}
		}
		
		System.out.println("url:" + url);
		System.out.println("param:" + param);
		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResultHelper.addStatuCode(result, httpCode);
	}

	public static String sendGetEx(String url, String param) {
		ResultHelper.callType = "Get";
		String result = "";
		int httpCode = 200;
		HttpClient httpClient = createSSLInsecureClient();
		String urlNameString = url;
		if (param != null) {
			urlNameString += "?" + param;
		}
		System.out.println("urlNameString:" + urlNameString);
		HttpGet httpget = new HttpGet(urlNameString.replace("|", "%7C").replace(" ", "%20"));
		httpget.setHeader("Charset", "UTF-8");

		HttpResponse response;
		try {
			response = httpClient.execute(httpget);
			httpCode = response.getStatusLine().getStatusCode();

			int headersLen = response.getHeaders("Content-Type").length;
			if (headersLen != 0) {
				String responserHeader = response.getHeaders("Content-Type")[0].getValue();
				if (responserHeader.contains("image"))
					result = null;
				else if (responserHeader.contains("application/octet-stream"))
					result = null;
				else if (responserHeader.contains("ms-excel"))
					result = null;
				else {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("init result：" + result);
		if (httpCode == 200 || httpCode == 404) {
			result = String.valueOf(httpCode) + ":" + result;
		} else {
			try {
				result = ResultHelper.addStatuCode(result, httpCode);
			} catch (Exception e) {
				result = ResultHelper.addStatuCode(null, httpCode);
			}
		}
		return result;
	}

	public static String sendGet(String url, String param) {
		ResultHelper.callType = "Get";
		String result = "";
		int httpCode = 200;
		HttpClient httpClient = createSSLInsecureClient();
		String urlNameString = url;
		if (param != null) {
			urlNameString += "?" + param;
		}
		System.out.println("urlNameString:" + urlNameString);
		HttpGet httpget = new HttpGet(urlNameString.replace("|", "%7C").replace(" ", "%20"));
		httpget.setHeader("Charset", "UTF-8");

		HttpResponse response;
		try {
			response = httpClient.execute(httpget);
			httpCode = response.getStatusLine().getStatusCode();
			//System.out.println("============" + httpCode);
			int headersLen = response.getHeaders("Content-Type").length;
			if (headersLen != 0) {
				String responserHeader = response.getHeaders("Content-Type")[0].getValue();
				if (responserHeader.contains("image"))
					result = null;
				else if (responserHeader.contains("application/octet-stream"))
					result = null;
//				else if (responserHeader.contains("application/x-tex")) {
//					String absolutefilePath = System.getProperty("user.dir") + File.separator + "file" + File.separator + TestBase.APNsFileName; // 文件路径
//					File file = new File(absolutefilePath);
//					FileOutputStream outputStream = new FileOutputStream(file);
//					InputStream inputStream = response.getEntity().getContent();
//					byte b[] = new byte[1024];
//					int j = 0;
//					while ((j = inputStream.read(b)) != -1) {
//						outputStream.write(b, 0, j);
//					}
//					outputStream.flush();
//					outputStream.close();
//					result = null;
//				} 
				else if (responserHeader.contains("ms-excel"))
					result = null;
				else {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
				}
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
		}

		System.out.println("init result：" + result);
		if (result.contains("<!DOCTYPE HTML>")) {
			result = null;
		}
		return ResultHelper.addStatuCode(result, httpCode);
	}

	public static HttpResponse sendGetAndReturnHttpResponse(String url, String param) {
		ResultHelper.callType = "Get";
		HttpClient httpClient = createSSLInsecureClient();
		String urlNameString = url;
		if (param != null) {
			urlNameString += "?" + param;
		}
		System.out.println("urlNameString:" + urlNameString);
		HttpGet httpget = new HttpGet(urlNameString.replace("|", "%7C").replace(" ", "%20"));
		httpget.setHeader("Charset", "UTF-8");

		HttpResponse response = null;
		try {
			response = httpClient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("HttpResponse:" + response);
		return response;
	}

	public static String greatWallSendPost(String url, String param, String authorization) throws UnsupportedEncodingException{
		int httpCode = 200;
		String result = "";
		HttpClient httpClient = createSSLInsecureClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Charset", "UTF-8");
		httpPost.setHeader("Connection", "Keep-Alive");
		httpPost.setHeader("user-agent", "Fiddler");
		// "Basic ZmVuZ2NoajpmZW5nY2hq"
		httpPost.setHeader("Authorization", authorization);
		httpPost.setHeader("Host", "192.8.202.21");
		httpPost.setHeader("Content-Length", "0");
		// json
		StringEntity stringEntity = new StringEntity(param.toString(), "UTF-8");// 解决中文乱码问题
		stringEntity.setContentEncoding("UTF-8");
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);

		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResultHelper.addStatuCode(result, httpCode);
	}
}