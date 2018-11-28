package com.jxrt.util;

import java.text.SimpleDateFormat;


import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;

import com.jxrt.httpclient.HttpRequest;
import com.jxrt.httpclient.HttpsRequest;
import com.jxrt.test.TestBase;

public class ResultHelper {
	
	public static String sheetName;

	public static String apiName;
	public static String result;
	public static String callType;
	public static int row = 1;
	public static int connectFailedCode = 250;

	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replaceAll("-", "");
		return uid;
	}

	public static String appendParam(String param, String paramKey, String paramValue) {
		if (paramValue != null) {
			param += paramKey + "=" + paramValue;
		} else {
			return param.substring(0, param.length() - 1);
		}
		return param;
	}
	
	public static boolean verifyListContains(JSONArray blist, String key, String name){
		boolean flag = true;
		for(int i=0;i<blist.size();i++){
			if(blist.getJSONObject(i).get(key).toString().contains(name)){
				flag =false;
				break;
			}
		}
		return !flag || blist.size() != 0;
	}
	
	public static String getTimeString(int i){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date().getTime() + i * 24 * 60 *60 *1000);
	}

	//验证接口返回定义的key
	public static void verifyKey(String keysStr, JSONObject jsonObj){
//		jsonObj = jsonObj.discard("returnCode");
//		if(keysStr == null || keysStr.equals("")){
//			return;
//		}
//		
//		String[] keys = keysStr.split(",");
//		Assert.assertEquals(keys.length, jsonObj.size());
//		for(int i=0;i<keys.length;i++){
//				Assert.assertTrue(jsonObj.containsKey(keys[i]), "结果返回中不包含key： " + keys[i] + ", 请检查.");
//				if(keys[i].equals("violationCauses") || keys[i].equals("violationCond") || keys[i].equals("optAffected"))
//					continue;
//				Assert.assertTrue(!jsonObj.get(keys[i]).equals(""), "结果返回中key值为空, key： " + keys[i] + ", 请检查.");
//		}
		verifyKeyAllowedNull(keysStr, jsonObj, null);
	}
	
	//验证接口返回定义的key
		public static void verifyKeyAllowedNull(String keysStr, JSONObject jsonObj, String nullKeysStr){
			jsonObj = jsonObj.discard("returnCode");
			if(keysStr == null || keysStr.equals("")){
				return;
			}
			
			String[] keys = keysStr.split(",");
			Assert.assertEquals(keys.length, jsonObj.size());
			if(nullKeysStr==null){
				for(int i=0;i<keys.length;i++){
						Assert.assertTrue(jsonObj.containsKey(keys[i]), "结果返回中不包含key： " + keys[i] + ", 请检查.");
						if(keys[i].equals("violationCauses") || keys[i].equals("violationCond") || keys[i].equals("optAffected") || keys[i].equals("optionalDNS") || keys[i].equals("timeServer") || keys[i].equals("groupPath") || keys[i].equals("operatorName"))
							continue;
						Assert.assertTrue(!jsonObj.get(keys[i]).equals(""), "结果返回中key值为空, key： " + keys[i] + ", 请检查.");
				}
			}else{
				for(int i=0;i<keys.length;i++){
					Assert.assertTrue(jsonObj.containsKey(keys[i]), "结果返回中不包含key： " + keys[i] + ", 请检查.");
					String[] nullKeys = nullKeysStr.split(",");
					int j=0;
					for(; j<nullKeys.length; j++){
						if(keys[i].equals(nullKeys[j]))
							break;
					}
					if(j==nullKeys.length)
						Assert.assertTrue(!jsonObj.get(keys[i]).equals(""), "结果返回中key值为空, key： " + keys[i] + ", 请检查.");
			}
			}
			
		}
	
	// 发送Delete请求
	public static String sendDeleteRequest(String protocolType, String request, Map<String, String> header) {
		ResultHelper.callType = "Delete";
		String result = "";
		if ("http".equals(protocolType)) {
			result = HttpRequest.sendDelete(request, header);
		} else {
			result = HttpsRequest.sendDelete(request);
		}
		System.out.println("result:" + result);
		String returnCode = ResultHelper.getJsonValueByKey(result, "returnCode");
//		if (returnCode.equals("401"))
//			TestBase.getToken();
		return result;
	}
	
	// 发送Delete请求
	public static String sendDeleteRequest(String protocolType, String request, String params, Map<String, String> header) {
		ResultHelper.callType = "Delete";
		String result = "";
		try{
			if ("http".equals(protocolType)) {
			} else {
				result = HttpsRequest.sendDelete(request,params);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("result:" + result);
		return result;
	}
	
	// 发送Put请求
	public static String sendPutRequest(String protocolType, String request, String params) {
		ResultHelper.callType = "Put";
		String result = "";
		try{
			if ("http".equals(protocolType)) {
				result = HttpRequest.sendPut(request, params);
			} else {
				result = HttpsRequest.sendPut(request, params);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("result:" + result);
		String returnCode = ResultHelper.getJsonValueByKey(result, "returnCode");
//		if (returnCode.equals("401"))
//			TestBase.getToken();
		return result;
	}

	// 发送Get请求
	public static String sendGetRequest(String protocolType, String request, String params, Map<String, String> header) {
		ResultHelper.callType = "Get";
		String result = "";
		if ("http".equals(protocolType)) {
			result = HttpRequest.sendGet(request, params, header);
		} else {
			result = HttpsRequest.sendGet(request, params);
		}
		System.out.println("result:" + result);
		String returnCode = ResultHelper.getJsonValueByKey(result, "returnCode");
//		if (returnCode.equals("401"))
//			TestBase.getToken();
		return result;
	}

	// 发送Post请求
	public static String sendPostRequest(String protocolType, String request, String params, Map<String, String> header) {
		ResultHelper.callType = "Post";
		String result = "";
		try{
			if ("http".equals(protocolType)) {
				result = HttpRequest.sendPost(request, params, header);
			} else {
				result = HttpsRequest.sendPost(request, params, header);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("result:" + result);
		String returnCode = ResultHelper.getJsonValueByKey(result, "returnCode");
//		if (returnCode.equals("401"))
//			TestBase.getToken();
		return result;
	}

	public static void saveAccessToken(String value) {
		String[] values = value.split(",");
		// values
		String accessToken = values[2].split(":")[1];
		accessToken = accessToken.replaceAll("\"", "");
		PropertiesUtil.setOptValue("access_token", accessToken);
		PropertiesUtil.store();
	}

	public static String returnResponseCode(String responseValue, String begin, String end) {
		int i = responseValue.indexOf(begin);
		int j = responseValue.indexOf(end);
		String codeValue = responseValue.substring(i + begin.length(), j);
		return codeValue;
	}

	//
	public static String returnHttpResponseCode(String responseValue) {
		String[] values = responseValue.split(",");
		// values
		String codeValue = values[0].split(":")[1];
		codeValue = codeValue.replaceAll("\"", "");
		return codeValue;
	}
	
	// json 结果解析
	public static JSONObject getJsonObject(String result){
		return JSONObject.fromObject(result);
	}
	
	public static String getJsonValueByKey(String result, String key) {
		JSONObject fromObject = getJsonObject(result);
		String codeValue = String.valueOf(fromObject.get(key));
		return codeValue;
	}

	// data object
	public static JSONObject getJsonData(String result) {
		String data = getJsonValueByKey(result, "data");
		return JSONObject.fromObject(data);
	}
	
	public static JSONArray getJsonDataArray(String result) {
		return getJsonDataArray(result, "data");
	}
	
	public static JSONArray getJsonDataArray(String result, String key) {
		String data = getJsonValueByKey(result, key);
		if (data == null || data.length()==0 || data.charAt(0)!='[')
			return null;
		return JSONArray.fromObject(data);
	}
	
	public static String addStatuCode(String result, int code){
		if(result == null || result.equals("")){
			result ="{}";
		}else if(result.startsWith("[")){//数组处理
			result = "{array:"+result+"}";
		}
		
		if(result.contains("<html")) {
			result = "{}";
		}
		JSONObject fromObject = JSONObject.fromObject(result);
		fromObject.put("returnCode", code);
		return fromObject.toString();
	}

	public static String printMap(Map<String,String> col){
		String out ="";
		for(Iterator<String> it = col.keySet().iterator();it.hasNext();){
			String key = it.next();
			out +=key+"="+col.get(key)+",";
		}
		if(out.length()>1){
			return out.substring(0, out.length()-1);
		}
		return out;
	}
	
	// 打印信息
	public static String printMessage(int errorCode) {
		String mes = "";
		switch (errorCode) {
		case 100:
			mes = "用户名密码验证失败";
			break;
		case 101:
			mes = "未携带访问令牌";
			break;
		case 102:
			mes = "访问令牌无效";
			break;
		case 103:
			mes = "访问令牌过期";
			break;
		case 111:
			mes = "参数枚举值错误";
			break;
		case 112:
			mes = "请求参数类型错误";
			break;
		case 113:
			mes = "非空参数为空";
			break;
		case 114:
			mes = "服务器内部错误";
			break;
		case 700:
			mes = "解析应用失败";
			break;
		case 701:
			mes = "应用文件没有签名";
			break;
		case 702:
			mes = "wrapping应用和非wrapping应用不能共存";
			break;
		case 703:
			mes = "存在相同包名相同版本的应用";
			break;
		case 704:
			mes = "存在该应用的不同版本";
			break;
		case 705:
			mes = "应用已存在，不能添加同一个应用。供WINDOWS8使用";
			break;
		case 706:
			mes = "未解析到iTunes 标识符";
			break;
		case 707:
			mes = "输入的url非法    //输入的url非法";
			break;
		case 708:
			mes = "上传应用失败";
			break;
		case 709:
			mes = "保存应用失败";
			break;
		case 710:
			mes = "上传文件为空";
			break;
		case 711:
			mes = "应用不存在";
			break;
		case 712:
			mes = "应用不可用";
			break;
		case 713:
			mes = "没有指定推送范围";
			break;
		case 714:
			mes = "推送类型错误";
			break;
		case 715:
			mes = "推送应用失败";
			break;
		case 716:
			mes = "应用更新失败";
			break;
		case 800:
			mes = "策略名称已存在";
			break;
		case 801:
			mes = "策略标识符已存在";
			break;
		default:
			mes = "code can't be identity: " + errorCode;
			break;
		}
		return mes;
	}
	
	public static void main(String args[]) {
//		 String str = "org.springframework.dao.DataIntegrityViolationException: could not insert: [com.netqin.mdm.entity.TUser]; SQL [insert into t_user (loginId, password, name, mail, mobile, type, canLoginSelf, canRegistDevice, canEliminateDevice, allowUnregisteredActivate, allowInconsistentActivate, allowActivateNum, autoSynContactsFlag, status, conflict, creatTime, updateTime, groupIds, groupNames, guid, syncBactchNo, groupId, groupCode) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]; nested exception is org.hibernate.exception.DataException: could not insert: [com.netqin.mdm.entity.TUser]\n\tat org.springframework.orm.hibernate3.SessionFactoryUtils.convertHibernateAccessException(SessionFactoryUtils.java:648)\n\tat org.springframework.orm.hibernate3.HibernateAccessor.convertHibernateAccessException(HibernateAccessor.java:412)\n\tat org.springframework.orm.hibernate3.HibernateTemplate.doExecute(HibernateTemplate.java:411)\n\tat org.springframework.orm.hibernate3.HibernateTemplate.executeWithNativeSession(HibernateTemplate.java:374)\n\tat org.springframework.orm.hibernate3.HibernateTemplate.save(HibernateTemplate.java:683)\n\tat com.netqin.mdm.dao.impl.BaseDaoImpl.save(BaseDaoImpl.java:677)\n\tat com.netqin.mdm.service.impl.UserServiceImpl.addUser(UserServiceImpl.java:88)\n\tat sun.reflect.GeneratedMethodAccessor178.invoke(Unknown Source)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:497)\n\tat org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:318)\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:183)\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)\n\tat org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:80)\n\tat com.netqin.mdm.component.OptLogger.loggerOpt(OptLogger.java:42)\n\tat sun.reflect.GeneratedMethodAccessor157.invoke(Unknown Source)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:497)\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:621)\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:610)\n\tat org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:65)\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:110)\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n\tat org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:90)\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)\n\tat org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:202)\n\tat com.sun.proxy.$Proxy98.addUser(Unknown Source)\n\tat com.netqin.mdm.controller.UserController.create(UserController.java:94)\n\tat sun.reflect.GeneratedMethodAccessor159.invoke(Unknown Source)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:497)\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:213)\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:126)\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:96)\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:617)\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:578)\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:923)\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:852)\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:882)\n\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:789)\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:650)\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:731)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.
//		 String result = str.replaceAll("\n\t|\n", "");
//		String in = "";
//		String aa = addStatuCode(in, 200);
		 System.out.println(getTimeString(0));
//		/*
//		 * String str = "{\"returnCode\": 0,\"data\": {\"userId\": 49821}}";
//		 * System.out.println(getJsonValueByKey(str, "returnCode"));
//		 */
//		String file = System.getProperty("user.dir") + File.separator + "ApiTest.xls";
//		Date currentTime = new Date();
//		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//		sheetName = formatter.format(currentTime);
//		book = createExcel(file);
//		createSheet(book, sheetName);
//		writeExcel(book, sheetName, "12", "123", null, "faf", "1", "这是一个测试");
//		closeExcel(book);
	}

}