package com.jxrt.api.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

import net.sf.json.JSONObject;

public class BizOrderQueryApi extends TestBase{
	/**
	 * 平台端订单状态查询
	 * @param params
	 */
	public static JSONObject queryOrderState(String coreName, String pledgeorName,String productType,String loanApplyNum,String orderBizSection,String orderStatus,String page,String pageSize) { 

		String request = null;
		try {
			
			request = requestUrl + "api/web/biz/v1/loan-applys/plat?coreName=" + URLEncoder.encode(coreName,"utf-8");

			if(pledgeorName!="" || pledgeorName != null) {
				request = request + "&"+ "pledgeorName=" +  URLEncoder.encode(pledgeorName,"utf-8") + "&";
			}
			if(productType!="" || productType != null) {
				request = request + "productType=" + productType + "&";
			}
			if(loanApplyNum!="" || loanApplyNum != null) {
				request = request + "loanApplyNum=" + loanApplyNum + "&";
			}
			if(orderBizSection!="" || orderBizSection != null) {
				request = request + "orderBizSection=" + orderBizSection + "&";
			}
			if(orderStatus!="" || orderStatus != null) {
				request = request + "orderStatus=" + orderStatus + "&";
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
