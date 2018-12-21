package com.jxrt.api.web;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.jxrt.api.BizCorpApi;
import com.jxrt.api.UaaApi;
import com.jxrt.dbutil.*;
import com.jxrt.test.TestBase;
import com.jxrt.util.PropertiesUtil;
import com.jxrt.util.ResultHelper;

public class BizCredit extends TestBase{


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
	
	
	/**
	 * 
	 * 企业端融资进度查询——根据一组白条编号查询融资进度信息
	 * @param creditsList
	 * @author 王晶晶
	 */
	public static ArrayList<String> getFinanceScheduleInfo(ArrayList<String> creditsList) {
		ArrayList<String> scheduleList = new ArrayList<>();
		if(creditsList.size()==0) {
			System.out.println("白条列表为空");
		}else {
			for(int i = 0; i < creditsList.size(); i++) {
				String pkCredit = creditsList.get(i);
				String request = requestUrl + "api/web/corp/v1/credits/process/" + pkCredit;
				String params = null;
				params = "isAccept=false";
				String result = "";
				result = ResultHelper.sendGetRequest(protocolType, request, params, null);
				String code = ResultHelper.getJsonValueByKey(result, "returnCode");
				String data = ResultHelper.getJsonValueByKey(result, "data");
				System.out.println(code);
				System.out.println(data);
			}
		}
		return scheduleList;
	}
	
	/**
	 * test主类
	 */	
	public static void main(String[] args) throws SQLException{
		//UaaApi.login("17710253335", "a1111111", "CCBSCF_BUSINESS_WEB");
		UaaApi.login("17710253335", "a1111111", "CCBSCF_CORP_WEB");
		bizCreditSubmit("");
	}
	

}
