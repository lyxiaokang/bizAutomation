package com.jxrt.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

import net.sf.json.JSONObject;

public class BizCorpApi extends TestBase{

	/**
	 * biz-ci-register-controller
	 * 平台端发起企业注册
	 * @param params
	 */
	public static void registerCorp(String params) {
		String request = requestUrl + "api/web/biz/v1/corps/register";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, params, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
//			System.out.println(result);
		}
	}
	
	/**
	 * biz-ci-register-controller 
	 * 分页查询所有待审核未生效的企业注册信息
	 */
	public static String searchPendingCorp(String corpName) {
		String request = null;
		String corpInfo = null;
		try {
			request = requestUrl + "api/web/biz/v1/pending/corps/registers/list?name=" + URLEncoder.encode(corpName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			String data = ResultHelper.getJsonValueByKey(result, "data");
			String datalist = ResultHelper.getJsonValueByKey(data, "datalist");
			corpInfo = datalist.replace("[", "").replace("]", "");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-register-controller 
	 * 查询企业注册基本信息
	 */
	public static String checkCorpRegisterInfo(String corpId) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/register/info";
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-register-controller
	 * deletePendingRegisterInfo
	 */
	public static String deletePendingRegisterInfo(String corpInfo, String mobile) {
		String request = null;
		String corpId = getCorpId(corpInfo);
	
		request = requestUrl + "api/web/biz/v1/corps/" + String.valueOf(corpId) +  "/mobile-phone/" + mobile + "/registers";
		request = request.replace("\"", "");

		String result = "";

		result = ResultHelper.sendDeleteRequest(protocolType, request, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			String data = ResultHelper.getJsonValueByKey(result, "data");
			String datalist = ResultHelper.getJsonValueByKey(data, "datalist");
			corpInfo = datalist.replace("[", "").replace("]", "");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-register-controller
	 * 平台端发起企业注册信息更新
	 */
	public static String updateRegisterInfo(String corpInfo, String corpInfoVo) {
		String request = null;
		String corpId = getCorpId(corpInfo);
	
		request = requestUrl + "api/web/biz/v1/corps/" + String.valueOf(corpId) +  "/register";
		request = request.replace("\"", "");

		String result = "";

		result = ResultHelper.sendPutRequest(protocolType, request, corpInfoVo);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			String data = ResultHelper.getJsonValueByKey(result, "data");
			String datalist = ResultHelper.getJsonValueByKey(data, "datalist");
			corpInfo = datalist.replace("[", "").replace("]", "");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-register-controller
	 * sendSmsNoticeForRegister
	 */
	public static void sendSmsNoticeForRegister(String corpId) {
		String request = null;
	
		request = requestUrl + "api/web/biz/v1/corps/" + corpId +  "/register/notice";
		request = request.replace("\"", "");

		String result = "";

		result = ResultHelper.sendPutRequest(protocolType, request, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
		}
	}
	
	/**
	 * biz-ci-register-controller 
	 * 分页查询所有已生效的企业注册信息
	 */
	public static String searchCorp(String corpName) {
		String request = null;
		String corpInfo = null;
		String[] dataList=null;
		try {
			request = requestUrl + "api/web/biz/v1/corps/registers/list?name=" + URLEncoder.encode(corpName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			String data = ResultHelper.getJsonValueByKey(result, "data");
			String datalist = ResultHelper.getJsonValueByKey(data, "datalist");
			//因为是模糊查询，datalist是一个list，而非String
			corpInfo = datalist.replace("[", "").replace("]", "").replaceAll("},", "}},");
			//
			dataList= corpInfo.split("},");
			for(int i=0;i<dataList.length;i++){
				JSONObject body = JSONObject.fromObject(dataList[i]);
				if(body.get("corpName").equals(corpName)){
					return dataList[i];
				}
			}
		}
		return corpInfo;
	}
	
	/**
	 * 从企业信息中提取企业id
	 */
	public static String getCorpId(String corpInfo) {
		JSONObject cI = JSONObject.fromObject(corpInfo);
		return cI.getString("pkCorp");
	}
	
	
	/**
	 * biz-ci-register-controller
	 * 企业注册审核
	 * @param corpInfo, 注册企业的信息（搜索返回）
	 */
	public static void authResiteredCorp(String corpInfo) {
		String request = "";
		String corpId = getCorpId(corpInfo);
		request = requestUrl + "api/web/biz/v1/corps/" + String.valueOf(corpId) +  "/register/plat-supervisor";
		request = request.replace("\"", "");
		String result = "";

		result = ResultHelper.sendPostRequest(protocolType, request, corpInfo, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
//			System.out.println(result);
		}
	}
	
	
	/**
	 * biz-ci-controller 
	 * 分页查询所有已生效的企业信息
	 */
	public static String checkValidCorpInfo(String corpName, String taxId, String page, String pageSize) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/active/list?";
		if(corpName!="" || corpName != null) {
			request = request + "name=" + corpName + "&";
		}
		if(taxId!="" || taxId != null) {
			request = request + "taxId=" + taxId + "&";
		}
		if(page!="" || page != null) {
			request = request + "page=" + page + "&";
		}
		if(pageSize!="" || pageSize != null) {
			request = request + "pageSize=" + pageSize + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * 根据企业名称分页模糊查询所有已认证的企业信息
	 */
	public static String checkAuthCorpInfo(String corpName, String page, String pageSize) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/auth/list?";
		if(corpName!="" || corpName != null) {
			request = request + "name=" + corpName + "&";
		}
		if(page!="" || page != null) {
			request = request + "page=" + page + "&";
		}
		if(pageSize!="" || pageSize != null) {
			request = request + "pageSize=" + pageSize + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * 根据restful规约模糊查询企业和金融机构相关信息
	 */
	public static String checkCorpInfo(String corpName, String taxId, String financeBranchCode, String userType, String userState, String createTimeBegin, String createTimeEnd, String page, String pageSize) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/list?";
		if(corpName!="" || corpName != null) {
			request = request + "corpName=" + corpName + "&";
		}
		if(taxId!="" || taxId != null) {
			request = request + "taxId=" + taxId + "&";
		}
		if(financeBranchCode!="" || financeBranchCode != null) {
			request = request + "financeBranchCode=" + financeBranchCode + "&";
		}
		if(userType!="" || userType != null) {
			request = request + "userType=" + userType + "&";
		}
		if(userState!="" || userState != null) {
			request = request + "userState=" + userState + "&";
		}
		if(createTimeBegin!="" || createTimeBegin != null) {
			request = request + "createTimeBegin=" + createTimeBegin + "&";
		}
		if(createTimeEnd!="" || createTimeEnd != null) {
			request = request + "createTimeEnd=" + createTimeEnd + "&";
		}
		if(page!="" || page != null) {
			request = request + "page=" + page + "&";
		}
		if(pageSize!="" || pageSize != null) {
			request = request + "pageSize=" + pageSize + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 根据企业名称分页模糊查询企业相关信息(包含链条企业、核心企业)
	 */
	public static String checkCorpByName(String name, String page, String pageSize) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/list/by/name?";
		if(name!="" || name != null) {
			request = request + "name=" + name + "&";
		}
		if(page!="" || page != null) {
			request = request + "page=" + page + "&";
		}
		if(pageSize!="" || pageSize != null) {
			request = request + "pageSize=" + pageSize + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 根据企业名称分页模糊查询所有正常的企业信息
	 */
	public static String checkNormalCorpInfo(String name, String page, String pageSize) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/normal/list?";
		if(name!="" || name != null) {
			request = request + "name=" + name + "&";
		}
		if(page!="" || page != null) {
			request = request + "page=" + page + "&";
		}
		if(pageSize!="" || pageSize != null) {
			request = request + "pageSize=" + pageSize + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * 查询所有未实名认证的企业信息
	 */
	public static String checkUnauthCorpInfo(String name, String page, String pageSize) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/unauths/list?";
		if(name!="" || name != null) {
			request = request + "name=" + name + "&";
		}
		if(page!="" || page != null) {
			request = request + "page=" + page + "&";
		}
		if(pageSize!="" || pageSize != null) {
			request = request + "pageSize=" + pageSize + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 根据企业编号查询企业是否可以进行实名认证新增
	 */
	public static boolean checkAllowRealname(String corpId) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/corps/" + corpId + "/allow/realname";

		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 根据企业编号查询企业员工信息
	 */
	public static String checkPersonsInfo(String corpId) {
		String request = null;
		String personsInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/" + corpId + "/persons/infos";
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			personsInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return personsInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * 查询企业实名认证的基本信息
	 */
	public static String checkCorpAuthInfo(String corpId) {
		String request = null;
		String authInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/" + corpId + "/auths";
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			authInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return authInfo;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 通过corpId和年份找到corp index
	 */
	public static String findCorpIndexByIdAndYear(String corpId, String year) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/" + corpId + "/indexs/" + year;
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * findCorpInvoice
	 */
	public static String findCorpInvoice(String corpId) {
		String request = null;
		String invoiceInfo = null;

		request = requestUrl + "api/web/biz/v1/corps/" + corpId + "/invoices";
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			invoiceInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return invoiceInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * 分页查询所有待审核未生效的企业信息
	 */
	public static String checkPendingCorpInfo(String corpName, String taxId, String page, String pageSize) {
		String request = null;
		String corpInfo = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/list?";
		if(corpName!="" || corpName != null) {
			request = request + "name=" + corpName + "&";
		}
		if(taxId!="" || taxId != null) {
			request = request + "taxId=" + taxId + "&";
		}
		if(page!="" || page != null) {
			request = request + "page=" + page + "&";
		}
		if(pageSize!="" || pageSize != null) {
			request = request + "pageSize=" + pageSize + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * deletePendingCorpsInfo
	 */
	public static boolean deletePendingCorpInfo(String corpId) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId;
		
		String result = "";
		result = ResultHelper.sendDeleteRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 查询企业实名认证的基本信息
	 */
	public static String checkPendingCorpAuthInfo(String corpId) {
		String request = null;
		String authInfo = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/auths";
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			authInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return authInfo;
	}
	
	/**
	 * biz-ci-controller 
	 * 企业信息实名认证
	 */
	public static boolean corpRealNameCert(String corpId, String corpInfoVO) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/realnames";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, corpInfoVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 平台端企业实名认证修改--经办操作（已生效）
	 */
	public static boolean corpRealNameCertModifyE(String corpId, String corpInfoVO) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/realnames/effective/plat-update/operator";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, corpInfoVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-controller 
	 * 企业信息实名认证平台经办审核
	 */
	public static boolean corpRealNameCertOperate(String corpId, String corpInfoVO) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/realnames/platoperator";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, corpInfoVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-controller 
	 * 实名认证平台主管审核提交
	 */
	public static boolean corpRealNameCertSupevisor(String corpId, String corpInfoVO) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/realnames/platsupervisor";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, corpInfoVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-controller 
	 * 平台端企业实名认证修改--经办操作（未生效）
	 */
	public static boolean corpRealNameCertModifyUE(String corpId, String corpInfoVO) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/realnames/uneffective/plat-update/operator";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, corpInfoVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-controller 
	 * 实名认证基本信息临时保存
	 */
	public static boolean corpRealNameTempSave(String corpId, String corpInfoVO) {
		String request = null;

		request = requestUrl + "api/web/biz/v1/pending/corps/" + corpId + "/auths/workflows";

		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, corpInfoVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-account-controller 
	 * searchCorpAccountDetails
	 */
	public static String searchCorpAccountDetails(String value) {
		String request = null;
		String accountInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		
		// value中pkCorp是必须有的
		request = requestUrl + "api/web/biz/ac/v1/corps" + params.getString("pkCorp") + "/vc/details?";

		if(params.has("transferTimeBegin.nanos")) {
			request = request + "transferTimeBegin.nanos=" + params.getString("transferTimeBegin.nanos") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		if(params.has("fkAccount")) {
			request = request + "fkAccount=" + params.getString("fkAccount") + "&";
		}
		if(params.has("accountNameCp")) {
			request = request + "accountNameCp=" + params.getString("accountNameCp") + "&";
		}
		if(params.has("accountCodeCp")) {
			request = request + "accountCodeCp=" + params.getString("accountCodeCp") + "&";
		}
		if(params.has("amountLower")) {
			request = request + "amountLower=" + params.getString("amountLower") + "&";
		}
		if(params.has("amountUpper")) {
			request = request + "amountUpper=" + params.getString("amountUpper") + "&";
		}
		
		if(params.has("serialNo")) {
			request = request + "serialNo=" + params.getString("serialNo") + "&";
		}
		if(params.has("accountInfoCp")) {
			request = request + "accountInfoCp=" + params.getString("accountInfoCp") + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			accountInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return accountInfo;
	}
	
	
	/**
	 * biz-account-controller 
	 * listOrganPlatformAccounts
	 */
	public static String listOrganPlatAccounts(String pkOrg) {
		String request = null;
		String accountInfo = null;

		request = requestUrl + "api/web/biz/ac/v1/organ/" + pkOrg + "/vc/list";

		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			accountInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return accountInfo;
	}
	
	
	/**
	 * biz-account-detail-controller 
	 * 查询已生效的内部户流水信息
	 */
	public static String checkValidAccountDetail(String value) {
		String request = null;
		String accountInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/account/bi/detail?";

		if(params.has("transferTimeBegin.nanos")) {
			request = request + "transferTimeBegin.nanos=" + params.getString("transferTimeBegin.nanos") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		if(params.has("fkAccount")) {
			request = request + "fkAccount=" + params.getString("fkAccount") + "&";
		}
		if(params.has("accountNameCp")) {
			request = request + "accountNameCp=" + params.getString("accountNameCp") + "&";
		}
		if(params.has("accountCodeCp")) {
			request = request + "accountCodeCp=" + params.getString("accountCodeCp") + "&";
		}
		if(params.has("amountLower")) {
			request = request + "amountLower=" + params.getString("amountLower") + "&";
		}
		if(params.has("amountUpper")) {
			request = request + "amountUpper=" + params.getString("amountUpper") + "&";
		}
		
		if(params.has("serialNo")) {
			request = request + "serialNo=" + params.getString("serialNo") + "&";
		}
		if(params.has("accountInfoCp")) {
			request = request + "accountInfoCp=" + params.getString("accountInfoCp") + "&";
		}
		
		String result = "";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			accountInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return accountInfo;
	}
	
	
	/**
	 * biz-account-detail-controller 
	 * 创建内部户流水审核记录，如果是暂存过的记录，需要将fkapprove传来； 如果是暂存数据，会以传入数据为准
	 */
	public static String createAccountDetail(String accountDetailDOS) {
		String request = null;
		String accountInfo = null;
		request = requestUrl + "api/web/biz/v1/account/bi/detail?";
		
		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, accountDetailDOS, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			accountInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return accountInfo;
	}
	
	
	/**
	 * biz-account-detail-controller 
	 * 导入内部流水之前，对数据做检查
	 */
	public static String accountDataCheck(String type, String pkFile) {
		String request = null;
		String accountInfo = null;
		request = requestUrl + "api/web/biz/v1/account/bi/detail/check/" + type + "?pkFile=" + pkFile;
		
		String result = "";
		result = ResultHelper.sendPostRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			accountInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return accountInfo;
	}
	
	/**
	 * biz-account-detail-controller
	 * 对内部户流水补录做审核
	 */
	public static boolean accountPendingSubmit(String form) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/account/bi/detail/pending/submit";

		String result = "";

		result = ResultHelper.sendPutRequest(protocolType, request, form);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-account-detail-controller
	 * 删除某个批次的未审核完成的内部户流水补录信息
	 */
	public static boolean deleteAccountPending(String fkApprove) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/account/bi/detail/pending/" + fkApprove;

		String result = "";

		result = ResultHelper.sendDeleteRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-account-detail-controller
	 * 查询某个审核批次中的所有补录的流水明细信息
	 */
	public static String checkAccountPending(String fkApprove) {
		String request = null;
		String accountInfo = null;
		request = requestUrl + "api/web/biz/v1/account/bi/detail/pending/" + fkApprove;

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			accountInfo = ResultHelper.getJsonValueByKey(result, "data"); 
		}
		return accountInfo;
	}
	
	
	/**
	 * biz-account-detail-controller
	 * 内部户流水补录时，暂存相关信息，也可以用于暂存的数据的更新
	 */
	public static String saveAccountPending(String detailTempDOS) {
		String request = null;
		String accountInfo = null;
		request = requestUrl + "api/web/biz/v1/account/bi/detail/save";

		String result = "";

		result = ResultHelper.sendPostRequest(protocolType, request, detailTempDOS, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			accountInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return accountInfo;
	}
	
	/**
	 * biz-calender-controller
	 * 查询某地区本年度所有的节假日
	 */
	public static String getHolidays(String region) {
		String request = null;
		String holidayInfo = null;
		request = requestUrl + "api/web/biz/v1/dates/holidays/currentYear?region=" + region;

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			holidayInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return holidayInfo;
	}
	
	/**
	 * biz-ci-core-admit-controller
	 * 查询已生效的核心企业信息（根据企业编号）
	 */
	public static String getCoreCorpInfo(String pkCore) {
		String request = null;
		String corpInfo = null;
		request = requestUrl + "api/web/biz/v1/cores?pkCore=" + pkCore;

		String result = "";

		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			corpInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return corpInfo;
	}
	
	
	/**
	 * biz-ci-core-admit-controller
	 * 修改已生效的核心企业信息
	 */
	public static boolean modifyCoreCorpInfo(String tCiCoreRequestVO) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/cores";

		String result = "";

		result = ResultHelper.sendPutRequest(protocolType, request, tCiCoreRequestVO);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
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
	 * biz-ci-core-admit-controller
	 * 查询已生效的核心企业信息(根据企业全称)
	 */
	public static String getCoreCorpInfoByName(String corpName) {
		String request = null;
		String corpInfo = null;
		request = requestUrl + "api/web/biz/v1/cores/names?corpName=" + corpName;

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
	 * 修改待审核的核心企业信息（审核不通过）
	 */
	public static boolean modifyPendingCoreCorpInfo(String tCiCoreRequestVO) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/cores";

		String result = "";

		result = ResultHelper.sendPutRequest(protocolType, request, tCiCoreRequestVO);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-core-admit-controller
	 * 新增核心企业信息
	 */
	public static boolean createPendingCoreCorpInfo(String tCiCoreRequestVO) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/cores";

		String result = "";

		result = ResultHelper.sendPostRequest(protocolType, request, tCiCoreRequestVO,jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-core-admit-controller
	 * 查询所有待审核的核心企业信息
	 */
	public static String getAllPendingCoreCorpInfo(String value) {
		String request = null;
		String corpInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/pending/cores/list?";

		if(params.has("coreName")) {
			request = request + "coreName=" + params.getString("coreName") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		if(params.has("submitDate")) {
			request = request + "submitDate=" + params.getString("submitDate") + "&";
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
	 * biz-ci-core-admit-controller
	 * 查询待审核的核心企业信息
	 */
	public static String getPendingCoreCorpInfo(String fkWorkflow, String pkCorp) {
		String request = null;
		String corpInfo = null;
		request = requestUrl + "api/web/biz/v1/pending/cores/workflows/" + fkWorkflow + "?";
		if(pkCorp != "" || pkCorp != null) {
			request = request + "pkCorp=" + pkCorp;
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
	 * biz-ci-core-admit-controller
	 *  删除未生效流程
	 */
	public static boolean delInvalidWorkflow(String pkWorkflow, String pkCore) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/cores/" + pkCore +"/workflows/" + pkWorkflow;
		String result="";
		result = ResultHelper.sendDeleteRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-finance-admit-controller
	 * 查询所有已生效的金融机构信息
	 */
	public static String getFinancesInfo(String value) {
		String request = null;
		String financeInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/finances/list?";

		if(params.has("financeName")) {
			request = request + "coreName=" + params.getString("coreName") + "&";
		}
		if(params.has("financeType")) {
			request = request + "financeType=" + params.getString("financeType") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		if(params.has("orgCode")) {
			request = request + "orgCode=" + params.getString("orgCode") + "&";
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
			financeInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return financeInfo;
	}
	
	/**
	 * biz-ci-finance-admit-controller
	 * 修改已生效金融机构信息
	 */
	public static boolean modifyFinanceInfo(String financeRequestTemp) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/finances";

		String result = "";

		result = ResultHelper.sendPutRequest(protocolType, request, financeRequestTemp);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-finance-admit-controller
	 * 查询的金融机构信息(根据金融机构编号)
	 */
	public static String getFinancesInfoByPkFinance(String pkFinance) {
		String request = null;
		String financeInfo = null;
		request = requestUrl + "api/web/biz/v1/finances/" + pkFinance;

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			financeInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return financeInfo;
	}
	
	/**
	 * biz-ci-finance-admit-controller
	 *  删除未生效金融机构
	 */
	public static boolean delInvalidFinance(String pkFinance, String pkWorkflow) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/finances?pkFinance=" + pkFinance +"&pkWorkflow=" + pkWorkflow;
		String result="";
		result = ResultHelper.sendDeleteRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-finance-admit-controller
	 * 修改待审核的金融机构信息（审核不通过）
	 */
	public static boolean modifyPendingFinanceInfo(String financeRequestTemp) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/finances";

		String result = "";

		result = ResultHelper.sendPutRequest(protocolType, request, financeRequestTemp);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-finance-admit-controller
	 * 新增金融机构准入信息
	 */
	public static boolean createPendingFinanceInfo(String ciFinanceRequestVO) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/finances";

		String result = "";

		result = ResultHelper.sendPostRequest(protocolType, request, ciFinanceRequestVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-finance-admit-controller
	 * 查询所有待审核的金融机构信息
	 */
	public static String getPendingFinancesInfo(String value) {
		String request = null;
		String financeInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/pending/finances/list?";

		if(params.has("financeName")) {
			request = request + "coreName=" + params.getString("coreName") + "&";
		}
		if(params.has("sumitDate")) {
			request = request + "sumitDate=" + params.getString("sumitDate") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		if(params.has("orgCode")) {
			request = request + "orgCode=" + params.getString("orgCode") + "&";
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
			financeInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return financeInfo;
	}
	
	/**
	 * biz-ci-finance-admit-controller
	 * 查询待审核的核心企业信息
	 */
	public static String getPendingFinanceInfo(String pkFinance, String workFlow) {
		String request = null;
		String corpInfo = null;
		request = requestUrl + "api/web/biz/v1/pending/finances/workflows/" + workFlow + "?";
		if(pkFinance != "" || pkFinance != null) {
			request = request + "pkFinance=" + pkFinance;
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
	 * biz-ci-finance-admit-controller
	 * 修改待审核的金融机构信息（审核不通过）
	 */
	public static boolean approvePendingFinanceInfo(String fkWorkflow, String fkApprove, String result, String financeRequestTemp) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/finances/workflows/" + fkWorkflow + "/" + fkApprove + "/" + result;

		String res = null;
		res = ResultHelper.sendPutRequest(protocolType, request, financeRequestTemp);
		String code = ResultHelper.getJsonValueByKey(res, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-partner-controller
	 * 查询所有的合作平台信息
	 */
	public static String getAllPlatformInfo() {
		String request = null;
		String PlatInfo = null;
		request = requestUrl + "api/web/biz/v1/partners/all/list";

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			PlatInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return PlatInfo;
	}
	
	/**
	 * biz-ci-partner-controller
	 * 查询所有的合作平台信息
	 */
	public static String getPlatformInfo(String value) {
		String request = null;
		String PlatInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/partners/list?";

		if(params.has("name")) {
			request = request + "name=" + params.getString("name") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			PlatInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return PlatInfo;
	}
	
	/**
	 * biz-ci-person-role-controller
	 * 查询所有的合作平台信息
	 */
	public static String getUserRoles(String userId) {
		String request = null;
		String PlatInfo = null;
		request = requestUrl + "api/web/biz/v1/users/" + userId + "/persons/roles/list";

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			PlatInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return PlatInfo;
	}
	
	/**
	 * biz-ci-person-role-controller
	 * 查询所有企业UEKY
	 */
	public static String getUserUkeys(String userId) {
		String request = null;
		String PlatInfo = null;
		request = requestUrl + "api/web/biz/v1/users/" + userId + "/ukeys/list";

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			PlatInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return PlatInfo;
	}
	
	/**
	 * biz-ci-supervisor-controller
	 * 修改未生效监管企业准入信息
	 */
	public static boolean modifyPendingSupervisor(String tCiSupervisorTempRequestVO) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/pending/supervisors";

		String res = null;
		res = ResultHelper.sendPutRequest(protocolType, request, tCiSupervisorTempRequestVO);
		String code = ResultHelper.getJsonValueByKey(res, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-supervisor-controller
	 * 查询监管企业准入未生效信息
	 */
	public static String getPendingSupervisorInfo(String value) {
		String request = null;
		String supervisorInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/pending/supervisors/list?";

		if(params.has("supervisorName")) {
			request = request + "supervisorName=" + params.getString("supervisorName") + "&";
		}
		if(params.has("submitTime")) {
			request = request + "submitTime=" + params.getString("submitTime") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			supervisorInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return supervisorInfo;
	}
	
	/**
	 * biz-ci-supervisor-controller
	 *  查看未生效监管企业准入信息
	 */
	public static String getPendingSupervisorInfo2(String pkSupervisor, String fkWorkflow) {
		String request = null;
		String supervisorInfo = null;
		request = requestUrl + "api/web/biz/v1/pending/supervisors/" + pkSupervisor + "/workflows/" + fkWorkflow;

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			supervisorInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return supervisorInfo;
	}
	
	/**
	 * biz-ci-supervisor-controller
	 *  删除未生效监管企业准入信息
	 */
	public static boolean delPendingSupervisorInfo(String pkSupervisor, String fkWorkflow, String fkApprove) {
		String request = null;
		String supervisorInfo = null;
		request = requestUrl + "api/web/biz/v1/pending/supervisors/" + pkSupervisor + "/workflows/" + fkWorkflow + "/" + fkApprove;

		String result="";
		result = ResultHelper.sendDeleteRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-ci-supervisor-controller
	 * 审批未生效监管企业准入信息
	 */
	public static boolean approvePendingSupervisorInfo(String value) throws Exception{
		String request = null;
		String supervisorInfo = null;
		JSONObject params = JSONObject.fromObject(value);

		if((!params.has("pkSupervisor")) || (!params.has("fkWorkflow")) || (!params.has("fkApprove")) || (!params.has("approveResult"))) {
			throw  new Exception("");
		}
		request = requestUrl + "api/web/biz/v1/pending/supervisors/" + params.getString("pkSupervisor") + "/workflows/" + params.getString("fkWorkflow") + "/" + params.getString("fkApprove")+ "/" + params.getString("approveResult") + "?";
		
		if(params.has("approveReasonType")) {
			request = request + "approveReasonType=" + params.getString("approveReasonType") + "&";
		}
		if(params.has("approveReason")) {
			request = request + "approveReason=" + params.getString("approveReason") + "&";
		}
		String result="";
		result = ResultHelper.sendPutRequest(protocolType, request, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-supervisor-controller
	 *  新增监管企业准入信息
	 */
	public static boolean addSupervisor(String tCiSupervisorRequestVO) {
		String request = null;
		String supervisorInfo = null;
		request = requestUrl + "api/web/biz/v1/supervisors/";

		String result="";
		result = ResultHelper.sendPostRequest(protocolType, request, tCiSupervisorRequestVO, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-supervisor-controller
	 * 修改已生效监管企业准入信息
	 */
	public static boolean modifySupervisor(String tCiSupervisorTempRequestVO) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/supervisors";

		String res = null;
		res = ResultHelper.sendPutRequest(protocolType, request, tCiSupervisorTempRequestVO);
		String code = ResultHelper.getJsonValueByKey(res, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-ci-supervisor-controller
	 * 查询监管企业准入已生效信息
	 */
	public static String getSupervisorInfo(String value) {
		String request = null;
		String supervisorInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/supervisors/list?";

		if(params.has("supervisorName")) {
			request = request + "supervisorName=" + params.getString("supervisorName") + "&";
		}
		if(params.has("updateTime")) {
			request = request + "updateTime=" + params.getString("updateTime") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			supervisorInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return supervisorInfo;
	}
	
	
	/**
	 * biz-ci-supervisor-controller
	 * 查询所有监管企业已生效信息
	 */
	public static String getAllSupervisorInfo() {
		String request = null;
		String supervisorInfo = null;
		request = requestUrl + "api/web/biz/v1/supervisors/list/all";

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			supervisorInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return supervisorInfo;
	}
	
	
	/**
	 * biz-ci-supervisor-controller
	 * 查询已生效监管企业准入信息
	 */
	public static String getValidSupervisorInfo(String pkSupervisor) {
		String request = null;
		String supervisorInfo = null;
		request = requestUrl + "api/web/biz/v1/supervisors/" + pkSupervisor;

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			supervisorInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return supervisorInfo;
	}
	
	/**
	 * biz-contract-controller
	 * 查看融资合同
	 */
	public static String getContract(String value) {
		String request = null;
		String contractInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/ccb/contract/credits/list?";

		if(params.has("corpName")) {
			request = request + "corpName=" + params.getString("corpName") + "&";
		}
		if(params.has("coreName")) {
			request = request + "coreName=" + params.getString("coreName") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		if(params.has("limitCoreName")) {
			request = request + "limitCoreName=" + params.getString("limitCoreName") + "&";
		}
		if(params.has("contractState")) {
			request = request + "contractState=" + params.getString("contractState") + "&";
		}
		if(params.has("contractEffectStartDate")) {
			request = request + "contractEffectStartDate=" + params.getString("contractEffectStartDate") + "&";
		}
		
		if(params.has("contractEffectEndDate")) {
			request = request + "contractEffectEndDate=" + params.getString("contractEffectEndDate") + "&";
		}
		if(params.has("contractExpiryStartDate")) {
			request = request + "contractExpiryStartDate=" + params.getString("contractExpiryStartDate") + "&";
		}
		
		if(params.has("contractExpiryEndDate")) {
			request = request + "contractExpiryEndDate=" + params.getString("contractExpiryEndDate") + "&";
		}
		
		if(params.has("ccbscfProductType")) {
			request = request + "ccbscfProductType=" + params.getString("ccbscfProductType") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			contractInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return contractInfo;
	}
	
	
	/**
	 * biz-contract-controller
	 * 查看融资合同
	 */
	public static boolean  downloadContract(String value) {
		String request = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/ccb/contract/credits/list/download?";

		if(params.has("corpName")) {
			request = request + "corpName=" + params.getString("corpName") + "&";
		}
		if(params.has("coreName")) {
			request = request + "coreName=" + params.getString("coreName") + "&";
		}
		if(params.has("limitCoreName")) {
			request = request + "limitCoreName=" + params.getString("limitCoreName") + "&";
		}
		if(params.has("contractState")) {
			request = request + "contractState=" + params.getString("contractState") + "&";
		}
		if(params.has("contractEffectStartDate")) {
			request = request + "contractEffectStartDate=" + params.getString("contractEffectStartDate") + "&";
		}
		
		if(params.has("contractEffectEndDate")) {
			request = request + "contractEffectEndDate=" + params.getString("contractEffectEndDate") + "&";
		}
		if(params.has("contractExpiryStartDate")) {
			request = request + "contractExpiryStartDate=" + params.getString("contractExpiryStartDate") + "&";
		}
		
		if(params.has("contractExpiryEndDate")) {
			request = request + "contractExpiryEndDate=" + params.getString("contractExpiryEndDate") + "&";
		}
		
		if(params.has("ccbscfProductType")) {
			request = request + "ccbscfProductType=" + params.getString("ccbscfProductType") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * biz-contract-controller
	 * 根据核心企业用户编号和链条企业用户编号查询生效合同对应的金融机构产品类型
	 */
	public static String getContractByCoreAndCorp(String value) {
		String request = null;
		String contractInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/ccb/contract/order/effect/financeproduct?";

		if(params.has("pkCore")) {
			request = request + "pkCore=" + params.getString("pkCore") + "&";
		}
		if(params.has("pkCorp")) {
			request = request + "pkCorp=" + params.getString("pkCorp") + "&";
		}

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			contractInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return contractInfo;
	}
	
	/**
	 * biz-contract-controller
	 * 查询订单融资合同
	 */
	public static String  getOrderContract(String value) {
		String request = null;
		String orderContractInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/ccb/contract/orders/list?";

		if(params.has("corpName")) {
			request = request + "corpName=" + params.getString("corpName") + "&";
		}
		if(params.has("coreName")) {
			request = request + "coreName=" + params.getString("coreName") + "&";
		}
		if(params.has("productTypeFinance")) {
			request = request + "productTypeFinance=" + params.getString("productTypeFinance") + "&";
		}
		if(params.has("contractCode")) {
			request = request + "contractCode=" + params.getString("contractCode") + "&";
		}
		if(params.has("ccbContractState")) {
			request = request + "ccbContractState=" + params.getString("ccbContractState") + "&";
		}
		
		if(params.has("tradeStatus")) {
			request = request + "tradeStatus=" + params.getString("tradeStatus") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			orderContractInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return orderContractInfo;
	}
	
	/**
	 * biz-contract-controller
	 * 查询订单合同详细信息
	 */
	public static String getContractDetail(String pkContract) {
		String request = null;
		String contractInfo = null;
		request = requestUrl + "api/web/biz/v1/ccb/contract/" + pkContract + "/orders";


		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			contractInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return contractInfo;
	}
	
	/**
	 * biz-contract-controller
	 *  根据主键更新合同发送状态
	 */
	public static boolean updateContractSendState(String pkContract) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/ccb/contract/" + pkContract + "/orders/sendstate";

		String result="";
		result = ResultHelper.sendPostRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-counter-party-controller
	 *  根据主键更新合同发送状态
	 */
	public static String addCounterInfo(String pkCorp, String counterParty) {
		String request = null;
		String counterInfo = null;
		request = requestUrl + "api/web/biz/v1/corps/" + pkCorp + "/cp";

		String result="";
		result = ResultHelper.sendPostRequest(protocolType, request, counterParty, jsonheader);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			counterInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return counterInfo;
	}
	
	
	/**
	 * biz-counter-party-controller
	 * 更新指定企业编号的交易对手信息
	 */
	public static String modifyCounterInfo(String pkCorp, String counterParty) {
		String request = null;
		String counterInfo = null;
		request = requestUrl + "api/web/biz/v1/corps/" + pkCorp + "/cps";

		String result="";
		result = ResultHelper.sendPutRequest(protocolType, request, counterParty);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			counterInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return counterInfo;
	}
	
	/**
	 * biz-counter-party-controller
	 * 查询批量新增交易对手结果信息
	 */
	public static String getCounterBatchList(String pkCorp, String value) {
		String request = null;
		String counterInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/corps/" + pkCorp + "/cps/batchs/result/list";

		if(params.has("counterpartyType")) {
			request = request + "counterpartyType=" + params.getString("counterpartyType") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null,null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			counterInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return counterInfo;
	}
	
	
	/**
	 * biz-counter-party-controller
	 * 批量导入交易对手
	 */
	public static String addBatchCounterInfo(String pkCorp, String pkFile) {
		String request = null;
		String counterInfo = null;
		request = requestUrl + "api/web/biz/v1/corps/" + pkCorp + "/cps/import/" + pkFile;

		String result="";
		result = ResultHelper.sendPostRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			counterInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return counterInfo;
	}
	
	/**
	 * biz-counter-party-controller
	 * 根据查询条件查询指定企业的交易对手信息
	 */
	public static String  getCounterInfoByCond(String pkCorp, String value) {
		String request = null;
		String counterInfo = null;
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/corps/" + pkCorp + "/cps/list?";

		if(params.has("corpNameCp")) {
			request = request + "corpNameCp=" + params.getString("corpNameCp") + "&";
		}
		if(params.has("createDateStart")) {
			request = request + "createDateStart=" + params.getString("createDateStart") + "&";
		}
		if(params.has("createDateEnd")) {
			request = request + "createDateEnd=" + params.getString("createDateEnd") + "&";
		}
		if(params.has("counterpartyType")) {
			request = request + "counterpartyType=" + params.getString("counterpartyType") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}
		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			counterInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return counterInfo;
	}
	
	/**
	 * biz-counter-party-controller
	 * 删除指定企业的交易对手信息
	 */
	public static boolean deleteCounterInfo(String pkCorp, String pkCps) {
		String request = null;
		request = requestUrl + "api/web/biz/v1/corps/" + pkCorp + "/cps/" + pkCps;

		String result="";
		result = ResultHelper.sendDeleteRequest(protocolType, request, null,null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * biz-credit-apply-batch-controller
	 * 白条申请一般申请查询
	 */
	public static String getCreditBatchList(String value) {
		String request = null;
		String creditInfo = "";
		JSONObject params = JSONObject.fromObject(value);
		request = requestUrl + "api/web/biz/v1/credits/apply/batch/list";
		
		if(params.has("workflowTypes")) {
			request = request + "workflowTypes=" + params.getString("workflowTypes") + "&";
		}
		if(params.has("fkCredit")) { 
			request = request + "fkCredit=" + params.getString("fkCredit") + "&";
		}
		if(params.has("approveState")) {
			request = request + "approveState=" + params.getString("approveState") + "&";
		}
		if(params.has("pkBatch")) {
			request = request + "pkBatch=" + params.getString("pkBatch") + "&";
		}
		if(params.has("startApplyTime")) {
			request = request + "startApplyTime=" + params.getString("startApplyTime") + "&";
		}
		if(params.has("endApplyTime")) {
			request = request + "endApplyTime=" + params.getString("endApplyTime") + "&";
		}
		if(params.has("page")) {
			request = request + "page=" + params.getString("page") + "&";
		}
		if(params.has("pageSize")) {
			request = request + "pageSize=" + params.getString("pageSize") + "&";
		}

		String result="";
		result = ResultHelper.sendGetRequest(protocolType, request, null,null);
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			creditInfo = ResultHelper.getJsonValueByKey(result, "data");
		}
		return creditInfo;
	}
}
