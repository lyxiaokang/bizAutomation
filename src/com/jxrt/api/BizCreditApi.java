package com.jxrt.api;

import java.awt.print.Printable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;
import com.jxrt.api.BizCorpApi;
import com.jxrt.api.UaaApi;
import net.sf.json.JSONObject;

public class BizCreditApi  extends TestBase{
	public static String pkApplys=null;
	public static String fkApplySource=null;
	public static String corpApplySource=null;
	public static String pkCredit=null;

	/**
	 * biz-credit-issue-controller
	 * 平台端发起账款签发
	 * @param params
	 * @author 邱刚
	 */
	public static void bizCreditIssue(String params) {
		String request = requestUrl + "api/web/biz/v1/pending/credits/issue";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, params, jsonheader);
		
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			String data = ResultHelper.getJsonValueByKey(result, "data");
			pkApplys=data;
		}
	}
	
	/**
	 * biz-credit-issue-controller
	 * 平台端提交账款申请
	 * @param params
	 * @author 邱刚
	 */
	public static void bizCreditSubmit(String params) {
		
		String request = requestUrl + "api/web/biz/v1/credits/issue/"+pkApplys;

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, params, jsonheader);
		
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
//			System.out.println(result);
		}
	}
	
	/**
	 * biz-credit-issue-controller
	 * 平台端主管账款审核
	 * @param params
	 * @author 邱刚
	 */
	public static void bizCreditApprove(String params) {
		//只做单条
		String request = requestUrl + "api/web/biz/v1/credits/issue/approve";
		params="[\""+pkApplys+"\"]";
		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, params, jsonheader);
		
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			
		}
	}
	
	public static void main(String[] args){
		System.out.println();
	}

	
}
