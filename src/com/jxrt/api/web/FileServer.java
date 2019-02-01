package com.jxrt.api.web;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jxrt.api.UaaApi;
import com.jxrt.httpclient.MultiHttpRequest;
import com.jxrt.test.TestBase;
import com.jxrt.util.ResultHelper;

public class FileServer  extends TestBase{
	/**
	 * 文件上传接口
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
		result = MultiHttpRequest.PostFileRequest(request, "123", filePath);
		System.out.println(result);
	}
	/**
	 * test主类
	 * @throws IOException 
	 */	
	public static void main(String[] args) throws SQLException, IOException{
//		UaaApi.login("17710253335", "a1111111", "CCBSCF_BUSINESS_WEB");
//		FileServer.fileUpload("111.txt", "", "", "D:\\accessory\\111.txt");
		Random rand=new Random(47);
		int a=rand.nextInt();
		System.out.println(a);
	}
}
