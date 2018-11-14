package com.jxrt.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.jxrt.test.TestBase;
import com.jxrt.util.FileHelper;

public class DotTestListener extends TestListenerAdapter {
	
	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestSuccess(tr);
		System.out.print(timeString());
		System.out.println("STARTED: " + tr.getInstanceName() + "." + tr.getName());
	}
	
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		tr.getThrowable().printStackTrace();
		String instanceName = tr.getInstanceName();
		String name = tr.getName();
		System.err.println(timeString() + "FAILED: "+ instanceName + "." + name);
		
//        try {
//    		//屏幕截图
//    		//指定了OutputType.FILE做为参数传递给getScreenshotAs()方法，
//            // 其含义是将截取的屏幕以文件形式返回。
//            File srcFile = TestBase.appInt.getDriver().getScreenshotAs(OutputType.FILE);
//            //利用FileUtils工具类的copyFile()方法保存getScreenshotAs()返回的文件对象。
//            String fileName = instanceName.split("\\.")[instanceName.split("\\.").length-1] + "_" + name + "_" + TestBase.caseId  + "_"  +  TestBase.generateDateTime2() + ".jpg";
//			FileUtils.copyFile(srcFile, new File(TestBase.baseDir + "/screenshots" + "/"+fileName));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
		FileHelper fileHelper = new FileHelper();
		fileHelper.writeDataToFile(TestBase.rerunFile, TestBase.caseId);
		
		TestBase.className = instanceName;
		if (!TestBase.methodList.contains(name)) {
			TestBase.methodList.add(name);
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		
		System.out.print(timeString());
		System.out.println("PASSED: " + tr.getInstanceName() + "." + tr.getName());
	}
	
	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		System.out.print(timeString());
		System.out.println("SKIPED: " + tr.getInstanceName() + "." +  tr.getName());
		
		FileHelper fileHelper = new FileHelper();
		fileHelper.writeDataToFile(TestBase.rerunFile, String.valueOf(tr.getParameters()[0]));
		
		String instanceName = tr.getInstanceName();
		String name = tr.getName();
		TestBase.className = instanceName;
		if (!TestBase.methodList.contains(name)) {
			TestBase.methodList.add(name);
		}
		
	}
	
	@Override
	public void onStart(ITestContext testContext) {
		super.onStart(testContext);
		TestBase.testName = testContext.getName();
	}
		
/*	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		
	}*/

	private String timeString() {
		Date currentTime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = "[" + formatter.format(currentTime) + "] ";
		return timeString;
	}
	
}
