package com.jxrt.api.web;

import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CoreProductApi extends TestBase{
	
	public static String fkCore = "";
	public static String fkWorkflow = "";
	public static String fkApprove = "";
	public static String pkCore = "";
	public static String coreCorpInfo = "";
	
	
	
	
	/**
	 * cores/ccbscfs/receivables
	 * 核心企业产品新增提交审核
	 * @author 杨建花
	 */
	public static boolean createPendingCoreCorpProduct(String tCiCoreRequestVO) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/products/cores/ccbscfs/receivables";

		String result = "";

		result = ResultHelper.sendPostRequest(protocolType, request, tCiCoreRequestVO,jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	

	/**
	 *
	 * 查询已生效的核心企业产品信息
	 * @author 杨建花
	 */
	public static String getCoreCorpProductInfoByName(String fkCore) {
		String request = null;
		String corpInfo = null;
		request = requestUrl + "api/web/biz/v1/products/cores/ccbscfs/list?fkCore=" + fkCore;

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-core-admit-controller
	 * 查询所有待审核的核心企业产品
	 * @author 杨建花
	 */
	public static String getAllPendingCoreCorpInfo(String value) {
		System.out.println(value+ "222222222222222");
		String request = null;
		String corpInfo = null;
		request = requestUrl + "api/web/biz/v1/pending/products/cores/ccbscfs/list?fkCore="+ value;

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * get pkCore 从未生效核心企业产品列表里获取的
	 */
	public static String getFkcore(String corpInfo) {
		JSONObject tmp = JSONObject.fromObject(corpInfo);
		JSONArray tmp1 = tmp.getJSONArray("datalist");
		JSONObject tmp2 = tmp1.getJSONObject(0);
		String fkCore = tmp2.getString("fkCore");
		CoreProductApi.fkCore = fkCore;
		return fkCore;		
		
	}
	

	/**
	 * biz-ci-core-admit-controller
	 * 查询所有已生效的核心企业信息
	 */
	public static String getAllCoreCorpInfo(String value) {
		String request = null;
		String corpInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/cores/list?";

		if(params.has("coreName")) {
			request = request + "coreName=" + params.getString("coreName") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		if(params.has("taxId")) {
			request = request + "taxId=" + params.getString("taxId") + "&";
		}
		if(params.has("totalPages")) {
			request = request + "totalPages=" + params.getString("totalPages") + "&";
		}
		if(params.has("count")) {
			request = request + "count=" + params.getString("count") + "&";
		}
		if(params.has("needCount")) {
			request = request + "needCount=" + params.getString("needCount") + "&";
		}

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	
	/**
	 * get pkCore 从核心企业搜索结果中获取fkCore
	 */
	public static String getPkcore(String corpInfo) {
		String pkCore = "";
		JSONObject tmp = JSONObject.fromObject(corpInfo);
		JSONArray tmp1 = tmp.getJSONArray("datalist");
		if(tmp1.size() ==1 ) {
			JSONObject tmp2 = tmp1.getJSONObject(0);
			pkCore = tmp2.getString("number");
			CoreProductApi.pkCore = pkCore;
		}
		return pkCore;		
		
	}
	
	/**
		 * get pkWorkflow
		 */
		public static String getFkWorkFlow(String corpInfo) {
			JSONObject tmp = JSONObject.fromObject(corpInfo);
			JSONArray tmp1 = tmp.getJSONArray("datalist");
			JSONObject tmp2 = tmp1.getJSONObject(0);
			String fkWorkflow = tmp2.getString("fkWorkflow");
			return fkWorkflow;		
			
		}
		
		/**
		 * get pkArrprove
		 */
		public static String getFkApprove(String corpInfo) {
			JSONObject tmp = JSONObject.fromObject(corpInfo);
			JSONArray tmp1 = tmp.getJSONArray("datalist");
			JSONObject tmp2 = tmp1.getJSONObject(0);
			String fkApprove = tmp2.getString("fkApprove");
			return fkApprove;
					
		}
		
		
		/**
		 * cores/ccbscfs/receivables
		 * 核心企业产品审核通过
		 * @author 杨建花
		 */
		public static boolean passCoreCorpProduct(String fkCore, String fkWorkflow, String fkApprove) {
			String request = null;
			request = requestUrl + "api/web/biz/v1/pending/products/cores/"+ fkCore+ "/ccbscfs/receivables/workflows/" + fkWorkflow + "/" + fkApprove + "/PASS?reasonType=&reason=";
	
			String result = "";
			System.out.println(request);
			result = ResultHelper.sendPutRequest(protocolType, request, null);
			String code = ResultHelper.getJsonValueByKey(result, "returnCode");
			if ("200".equals(code)) {
				return true;
			}
			return false;
		}
	
	}
