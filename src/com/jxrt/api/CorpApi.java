package com.jxrt.api;

import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

import net.sf.json.JSONObject;

public class CorpApi extends TestBase{
	/**
	 * 企业端产品开通状态查询
	 * @param params
	 */
	public static JSONObject queryProductState() {
		String request = requestUrl + "api/web/corp/v1/products/states/list";

		JSONObject data = new JSONObject();

		String result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
//			System.out.println(result);
			data = JSONObject.fromObject(ResultHelper.getJsonValueByKey(result, "data"));
//			System.out.println(data);
//			String token = ResultHelper.getJsonValueByKey(data, "token");
		}
		return data;
	}
	

}
