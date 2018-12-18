package com.jxrt.api.web;

import com.jxrt.util.ResultHelper;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.jxrt.test.TestBase;

public class BizLimit extends TestBase{

	/**
	 * corp-limit-ccbscf-controller
	 * 查询可用额度
	 * @param params
	 * @author 邱刚
	 */
	public static Map<String,String> ccbscfLimitAvailableList(String fkCorp,String fkProductCcbscf,String limitUseChannel) {
		String request = requestUrl + "api/web/corp/v1/limits/ccbscfs/details/channels/can-occupy/list?fkCorp="+fkCorp+"&fkProductCcbscf="+fkProductCcbscf+"&limitUseChannel="+limitUseChannel;
		String limitInfo = null;
		String[] dataList=null;
		String result = "";
		Map<String,String> map=new HashMap<String,String>();
		result = ResultHelper.sendGetRequest(protocolType, request, null, null);
		
		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			String data = ResultHelper.getJsonValueByKey(result, "data");

			//因为是模糊查询，datalist是一个list，而非String
			limitInfo = data.replace("[", "").replace("]", "").replaceAll("},", "}},");
			//
			dataList= limitInfo.split("},");
			//默认取第一个
			JSONObject body = JSONObject.fromObject(dataList[0]);
			String fkApplySource=body.get("pkDetail").toString();
			String corpApplySource=body.get("corpShortnameSource").toString();
			map.put("fkApplySource", fkApplySource);
			map.put("corpApplySource", corpApplySource);
			return map;
		}
		return null;
	}
}
