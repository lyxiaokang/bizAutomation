package com.jxrt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestngRetry implements IRetryAnalyzer {
	private int retryCount = 1;
	private static int maxRetryCount;

	static {
		// 外围文件配置最大运行次数
		maxRetryCount = 2;
	}

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			String message = "RERUN: " + result.getName() + " Retrying " + retryCount + " times";
			System.out.println(timeString() + message);
			Reporter.setCurrentTestResult(result);
			System.out.println(timeString() + "RunCount=" + (retryCount + 1));
			retryCount++;
			return true;
		}
		return false;
	}

	private static String timeString() {
		Date currentTime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = "[" + formatter.format(currentTime) + "] ";
		return timeString;
	}
}