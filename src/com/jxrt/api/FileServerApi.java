package com.jxrt.api;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.jxrt.httpclient.MultiHttpRequest;

import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;
import com.jxrt.api.*;

public class FileServerApi extends TestBase{

	/**
	 * 平台端发起企业注册
	 * @param params
	 * @throws IOException 
	 */
	public static void fileUpload(String fileName,String expiryDate,String effectDate,String filePath ) throws IOException {
		String request = requestUrl + "api/web/v1/files";
		Map<String,String> param=new HashMap<String,String>();
		param.put("fileName", fileName);
		param.put("expiryDate", expiryDate);
		param.put("effectDate", effectDate);
		
		String result = "";
		result = MultiHttpRequest.PostRequest(request, "123", filePath, param);

		String code = ResultHelper.getJsonValueByKey(result, "returnCode");
		if ("200".equals(code)) {
			System.out.println(result);
		}
	}
	
}
