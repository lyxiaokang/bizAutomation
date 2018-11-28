package com.jxrt.httpclient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.jxrt.util.ResultHelper;

public class MultiHttpRequest {
	static CloseableHttpClient httpclient = null;

	public static CloseableHttpClient getHttpClient() {
		if (httpclient != null) {
			return httpclient;
		}
		HttpClientBuilder httpclientBuilder = null;
		SSLConnectionSocketFactory sslsf = null;
		PoolingHttpClientConnectionManager cm = null;
		try {
			httpclientBuilder = HttpClients.custom();
			/*SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());*/
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext,
					new String[] { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3"},
					null,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			// sslsf = new SSLConnectionSocketFactory(builder.build());
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", new PlainConnectionSocketFactory()).register("https", sslsf).build();

			cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(2000);// max connection

			// System.setProperty("jsse.enableSNIExtension", "false"); //""
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// .setProxy(new HttpHost("127.0.0.1", 8888,"http"))
		return httpclientBuilder.setSSLSocketFactory(sslsf).setConnectionManager(cm).build();
	}

	public static String post(String protocolType, String url, String keyFile, String filePath, Map<String, String> param) {
		return post(protocolType, url, keyFile, filePath, param, false);
//		ResultHelper.callType = "Post";
//		String result = "";
//		try {
//			if ("http".equals(protocolType)) {
//				result = MultiHttpRequest.PostRequest(url, keyFile, filePath, param);
//			} else {
//				result = MultiHttpRequest.httpsPostRequest(url, keyFile, filePath, param);
//			}
//		} catch (Exception e) {
//		}
//		System.out.println(result);
//		return result;
	}
	
	public static String post(String protocolType, String url, String keyFile, String filePath, Map<String, String> param, boolean isChineseName) {
			ResultHelper.callType = "Post";
			String result = "";
			try {
				if ("http".equals(protocolType)) {
					result = MultiHttpRequest.PostRequest(url, keyFile, filePath, param);
				} else {
					if(isChineseName)
						result = MultiHttpRequest.httpsPostRequest(url, keyFile, filePath, param, true);
					else
						result = MultiHttpRequest.httpsPostRequest(url, keyFile, filePath, param);
				}
			} catch (Exception e) {
			}
			System.out.println(result);
			return result;
	}
	
	public static String put(String protocolType, String url, String keyFile, String filePath, Map<String, String> param) {
		ResultHelper.callType = "Put";
		String result = "";
		try {
			if ("http".equals(protocolType)) {
				result = MultiHttpRequest.PutRequest(url, keyFile, filePath, param);
			} else {
				result = MultiHttpRequest.httpsPutRequest(url, keyFile, filePath, param);
			}
		} catch (Exception e) {
		}
		System.out.println(result);
		return result;
	}

	private static String PutRequest(String url, String keyFile, String filePath, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String httpsPostRequest(String url, String keyFile, String filePath, Map<String, String> param) throws IOException {
		return httpsPostRequest(url, keyFile, filePath, param, false);
	}
	
	public static String httpsPostRequest(String url, String keyFile, String filePath, Map<String, String> param, boolean isChineseName) throws IOException {
		int httpCode = 200;
		String resData = "";
		CloseableHttpClient httpclient = getHttpClient();
		try {

			HttpPost httppost = new HttpPost(url);
			MultipartEntityBuilder multiBody = MultipartEntityBuilder.create();
			
			if(isChineseName){
				multiBody.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				multiBody.setCharset(Charset.forName("utf-8"));
			}
			if (filePath.contains("*")) {
				File fileObj = new File(filePath.substring(0, filePath.indexOf("*")));
				System.out.println("=====" + filePath.substring(filePath.indexOf("*") + 1));
				FileBody bin = new FileBody(fileObj);
				for (int i = 0; i < Integer.parseInt(filePath.substring(filePath.indexOf("*") + 1)); i++) {
					multiBody.addPart(keyFile, bin);
					// multiBody.addBinaryBody(keyFile+i, fileObj);
				}
			}else if (filePath.contains(",")){
				String[] keys =  keyFile.split(",");
				String[] files = filePath.split(",");
				for (int i=0; i<keys.length&&i<filePath.length(); i++){
					File fileObj = new File(files[i]);
					FileBody bin = new FileBody(fileObj);
					multiBody.addPart(keys[i], bin);
				}
			}else {
				if (!filePath.equals("")) {
					FileBody bin = new FileBody(new File(filePath));
					multiBody.addPart(keyFile, bin);
				}
			}

			if (param != null) {
				for (Iterator<String> it = param.keySet().iterator(); it.hasNext();) {
					String key = it.next();
					StringBody value = new StringBody(param.get(key), ContentType.TEXT_PLAIN);
					if (value != null) {
						multiBody.addPart(key, value);
					}
				}
			}

			HttpEntity reqEntity = multiBody.build();
			httppost.setEntity(reqEntity);

			System.out.println("executing request " + httppost.getRequestLine());
			System.out.println("Params: " + param);
			CloseableHttpResponse response = null;
			response = httpclient.execute(httppost);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				resData = EntityUtils.toString(resEntity);
			}
//			EntityUtils.consume(resEntity);
			if (response != null) {
				response.close();
			}
		} catch (Exception e) {
			System.out.println("send POST Error" + e);
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
		return ResultHelper.addStatuCode(resData, httpCode);
	}
	
	public static String httpsPutRequest(String url, String keyFile, String filePath, Map<String, String> param) throws IOException {
		int httpCode = 200;
		String resData = "";
		CloseableHttpClient httpclient = getHttpClient();
		try {

			HttpPut httpput = new HttpPut(url);
			MultipartEntityBuilder multiBody = MultipartEntityBuilder.create();
			if (filePath.contains("*")) {
				File fileObj = new File(filePath.substring(0, filePath.indexOf("*")));
				System.out.println("=====" + filePath.substring(filePath.indexOf("*") + 1));
				FileBody bin = new FileBody(fileObj);
				for (int i = 0; i < Integer.parseInt(filePath.substring(filePath.indexOf("*") + 1)); i++) {
					multiBody.addPart(keyFile, bin);
					// multiBody.addBinaryBody(keyFile+i, fileObj);
				}
			}else if (filePath.contains(",")){
				String[] keys =  keyFile.split(",");
				String[] files = filePath.split(",");
				for (int i=0; i<keys.length&&i<filePath.length(); i++){
					File fileObj = new File(files[i]);
					FileBody bin = new FileBody(fileObj);
					multiBody.addPart(keys[i], bin);
				}
			}else {
				if (!filePath.equals("")) {
					FileBody bin = new FileBody(new File(filePath));
					multiBody.addPart(keyFile, bin);
				}
			}

			if (param != null) {
				for (Iterator<String> it = param.keySet().iterator(); it.hasNext();) {
					String key = it.next();
					StringBody value = new StringBody(param.get(key), ContentType.TEXT_PLAIN);
					if (value != null) {
						multiBody.addPart(key, value);
					}
				}
			}

			HttpEntity reqEntity = multiBody.build();
			httpput.setEntity(reqEntity);

			System.out.println("executing request " + httpput.getRequestLine());
			System.out.println("Params: " + param);
			CloseableHttpResponse response = null;
			response = httpclient.execute(httpput);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				resData = EntityUtils.toString(resEntity);
			}
//			EntityUtils.consume(resEntity);
			if (response != null) {
				response.close();
			}
		} catch (Exception e) {
			System.out.println("send POST Error" + e);
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
		return ResultHelper.addStatuCode(resData, httpCode);
	}	

	public static String PostRequest(String url, String keyFile, String filePath, Map<String, String> param) throws IOException {
		ResultHelper.callType = "Post";
		int httpCode = 200;
		String resData = "";
		CloseableHttpClient httpclient = getHttpClient();
		try {
			HttpPost httppost = new HttpPost(url);
			FileBody bin = new FileBody(new File(filePath));
			MultipartEntityBuilder multiBody = MultipartEntityBuilder.create().addPart(keyFile, bin);
			if (param != null) {
				for (Iterator<String> it = param.keySet().iterator(); it.hasNext();) {
					String key = it.next();
					StringBody value = new StringBody(param.get(key), ContentType.TEXT_PLAIN);
					multiBody.addPart(key, value);
				}
			}
			HttpEntity reqEntity = multiBody.build();
			httppost.setEntity(reqEntity);

			System.out.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = null;
			response = httpclient.execute(httppost);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				resData = EntityUtils.toString(resEntity);
			}
//			EntityUtils.consume(resEntity);
			if (response != null) {
				response.close();
			}
		} catch (Exception e) {
			System.out.println("send POST Error" + e);
			e.printStackTrace();
		} finally {
			httpclient.close();
		}

		return ResultHelper.addStatuCode(resData, httpCode);
	}

	public static String PostFileRequest(String url, String keyFile, String filePath) throws IOException {
		ResultHelper.callType = "Post";
		int httpCode = 200;
		String resData = "";
		CloseableHttpClient httpclient = getHttpClient();
		try {
			HttpPost httppost = new HttpPost(url);
			FileBody bin = new FileBody(new File(filePath));
			MultipartEntityBuilder multiBody = MultipartEntityBuilder.create().addPart(keyFile, bin);
			multiBody.addPart(keyFile, bin);

			HttpEntity reqEntity = multiBody.build();
			httppost.setEntity(reqEntity);

			System.out.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = null;
			response = httpclient.execute(httppost);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			httpCode = response.getStatusLine().getStatusCode();
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				resData = EntityUtils.toString(resEntity);
			}
//			EntityUtils.consume(resEntity);
			if (response != null) {
				response.close();
			}
		} catch (Exception e) {
			System.out.println("send POST Error" + e);
			e.printStackTrace();
		} finally {
			httpclient.close();
		}

		return ResultHelper.addStatuCode(resData, httpCode);
	}

	public static void main(String args[]) throws Exception {
		// MultiHttpRequest.PostRequest();
	}
}