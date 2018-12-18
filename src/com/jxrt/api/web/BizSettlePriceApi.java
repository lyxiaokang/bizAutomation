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
 * @data 2018年10月16日 下午5:12:11
 */
public class BizSettlePriceApi extends TestBase {
	/**
	 * 新增结算价格
	 * 
	 * @param params
	 *            json
	 * @return
	 */
	public static String newSettlePrice(String params) {
		String request = requestUrl + "api/web/biz/v1/prices/settles";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, params, jsonheader);

		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return result;
		}
		return null;
	}

	/**
	 * 查询已生效结算价格详情 /biz/v1/prices/settles/{pkSettle}
	 * 
	 * @param params
	 *            pksettle
	 * @return
	 */
	public static String getSettlePrice(String pkSettle) {
		String request = requestUrl + "api/web/biz/v1/prices/settles/" + pkSettle;

		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return result;
		}
		return null;

	}

	/**
	 * 查询待审核结算价格详情
	 * /biz/v1/pending/prices/settles/{pkSettle}/workflows/{fkWorkflow}
	 */

	public static String getPendingSettlePrice(String pkSettle, String fkWorkflow) {
		String request = requestUrl + "api/web/biz/v1/pending/prices/settles/" + pkSettle + "/workflows/" + fkWorkflow;

		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return result;
		}
		return null;
	}

	/**
	 * 查询已生效的结算价格 /biz/v1/prices/settles/list
	 * 
	 * @return
	 */
	public static String getSettlesPriceList(String settleName, String page, String pageSize) {
		String request = requestUrl + "api/web/biz/v1/prices/settles/list?";

		if (!StringUtils.isBlank(settleName)) {
			try {
				request = request + "settleName=" + URLEncoder.encode(settleName, "utf-8") + "&";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!StringUtils.isBlank(page)) {
			request = request + "page=" + page + "&";

		}
		if (!StringUtils.isBlank(pageSize)) {
			request = request + "pageSize=" + pageSize + "&";

		}

		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return result;
		}
		return null;
	}

	/**
	 * 查询待审核的结算价格 /biz/v1/pending/prices/settles/list
	 * 
	 * @return
	 */
	public static String getPendingSettlesPriceList(String settleName, String page, String pageSize) {
		String request = requestUrl + "api/web/biz/v1/pending/prices/settles/list?";

		if (!StringUtils.isBlank(settleName)) {
			try {
				request = request + "settleName=" + URLEncoder.encode(settleName, "utf-8") + "&";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!StringUtils.isBlank(page)) {
			request = request + "page=" + page + "&";

		}
		if (!StringUtils.isBlank(pageSize)) {
			request = request + "pageSize=" + pageSize + "&";

		}

		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return result;
		}
		return null;
	}

	/**
	 * 审核结算价格通过
	 * @param pkSettle
	 * @param fkWorkflow
	 * @param fkApprove
	 * @return
	 */
	public static String settlePricePass(String pkSettle, String fkWorkflow, String fkApprove) {
		// /biz/v1/pending/prices/settles/{pkSettle}/workflows/{fkWorkflow}/{fkApprove}/{result}

		String request = requestUrl + "api/web/biz/v1/pending/prices/settles/" + pkSettle + "/workflows/" + fkWorkflow
				+ "/" + fkApprove + "/PASS";

		String result = "";
		result = ResultHelper.sendPutRequest(protocolType, request, null);

		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return result;
		}
		return null;

	}

}
