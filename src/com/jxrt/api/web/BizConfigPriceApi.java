/**
 * 
 */
package com.jxrt.api.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

/**
 * @author mabo
 * @data 2018年10月16日 下午5:11:01
 */
public class BizConfigPriceApi extends TestBase {

	/**
	 * 查询已生效的价格配置 /biz/v1/prices/configs/list
	 * 
	 * @return
	 */
	public static String getConfigPriceList(String configName, String configType, String coreShortnameSource,
			String coreShortnameIssue, String financeShortname, String partnerShortname, String ccbscfProductType,
			String financeProductType, String userNameCustom, String fkCredit, String page, String pageSize) {
		String request = requestUrl + "api/web/biz/v1/prices/configs/list?";

		request = addChineseParameter(configName, request, "configName=");
		request = addEnglishParameter(configType, request, "configType=");
		request = addChineseParameter(coreShortnameSource, request, "coreShortnameSource=");
		request = addChineseParameter(coreShortnameIssue, request, "coreShortnameIssue=");
		request = addChineseParameter(financeShortname, request, "financeShortname=");
		request = addEnglishParameter(ccbscfProductType, request, "ccbscfProductType=");
		request = addEnglishParameter(financeProductType, request, "financeProductType=");
		request = addChineseParameter(userNameCustom, request, "userNameCustom=");
		request = addEnglishParameter(fkCredit, request, "fkCredit=");
		request = addEnglishParameter(page, request, "page=");
		request = addEnglishParameter(pageSize, request, "pageSize=");

		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");

		if ("200".equals(code)) {
			return result;
		}
		return null;
	}
	
	/**
	 * /biz/v1/prices/configs/{pkConfig}
	 * 查询已生效价格配置详情
	 * @param pkConfig
	 * @return
	 */
	public static String getConfigPriceByPkConfig(String pkConfig) {
		String request = requestUrl + "api/web/biz/v1/prices/configs/"+pkConfig;
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		
		if ("200".equals(code)) {
			return result;
		}
		return null;
		
	}
	

	private static String addEnglishParameter(String page, String request, String urlPart) {
		if (!StringUtils.isBlank(page)) {
			request = request + urlPart + page + "&";

		}
		return request;
	}

	private static String addChineseParameter(String configName, String request, String urlPart) {
		if (!StringUtils.isBlank(configName)) {
			try {
				request = request + urlPart + URLEncoder.encode(configName, "utf-8") + "&";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return request;
	}
}
