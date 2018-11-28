package com.jxrt.api.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

import net.sf.json.JSONObject;

public class BizOrderDrawDownQueryApi extends TestBase{
	/**
	 * 平台端订单状态查询
	 * @param params
	 */
	public static JSONObject queryOrderDrawDownState(String coreName, String dealerName,String loanApplyNum,String drawdownDraftNum,String sEffectDate,String sExpiryDate,String effectDate,String expiryDate,String page,String pageSize) { 

		String request = null;
		try {
			
			request = requestUrl + "api/web/biz/v1/drawdown-details/list?coreName=" + URLEncoder.encode(coreName,"utf-8");
			//http://test1.ccbscf.com/api/web/biz/v1/drawdown-details/list?page=1&pageSize=10&coreName=%E9%83%8E%E9%85%92%E6%AD%A3%E5%85%B4&dealerName=%E5%85%AC%E5%8F%B8%E5%9B%9B%E5%9B%9B&loanApplyNum=&drawdownDraftNum=&sEffectDate=&sExpiryDate=&effectDate=&expiryDate=
			if(dealerName!="" || dealerName != null) {
				request = request + "&"+ "dealerName=" +  URLEncoder.encode(dealerName,"utf-8") + "&";
			}
			if(loanApplyNum!="" || loanApplyNum != null) {
				request = request + "loanApplyNum=" + loanApplyNum + "&";
			}
			if(drawdownDraftNum!="" || drawdownDraftNum != null) {
				request = request + "drawdownDraftNum=" + drawdownDraftNum + "&";
			}
			if(sEffectDate!="" || sEffectDate != null) {
				request = request + "sEffectDate=" + sEffectDate + "&";
			}
			if(sExpiryDate!="" || sExpiryDate != null) {
				request = request + "sEffectDate=" + sExpiryDate + "&";
			}
			if(effectDate!="" || effectDate != null) {
				request = request + "effectDate=" + effectDate + "&";
			}
			if(expiryDate!="" || expiryDate != null) {
				request = request + "expiryDate=" + expiryDate + "&";
			}
			if(page!="" || page != null) {
				request = request + "page=" + page + "&";
			}
			if(pageSize!="" || pageSize != null) {
				request = request + "pageSize=" + pageSize ;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject data = new JSONObject();

		String result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
//			System.out.println(result);
			data = JSONObject.fromObject(ResultHelper.getJsonValueByKey(result, "data"));
			//String data =String.valueOf(TestBase.corp_signup_Status.getJSONObject("datalist").get("corpName"));
			System.out.println("\n======================");
			System.out.println("data:" + data + " ");
//			System.out.println(data);
//			String token = ResultHelper.getJsonValueByKey(data, "token");

		}
		return data;
	}
	
}
