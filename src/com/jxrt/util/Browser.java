package com.jxrt.util;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.session.FirefoxFilter;

import com.jxrt.test.TestBase;

public class Browser {
	public static WebDriver webdriver;

	public static WebDriver getDriver(String browserType, String url) {
		switch (browserType) {
		case "Chrome":
			DesiredCapabilities caps = setDownloadsPath();
			webdriver = new ChromeDriver(caps);
			break;
		case "IE":
			webdriver = new InternetExplorerDriver();
			break;
		case "Firefox":
			webdriver = new FirefoxDriver();
			break;
		default:
			System.out.println("非法浏览器配置！");
		}
		webdriver.get(url);
		// 放大浏览器
		webdriver.manage().window().maximize();
		webdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return webdriver;
	}

	private static DesiredCapabilities setDownloadsPath() {
	    String downloadsPath = TestBase.downloadsPath;
	    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	    chromePrefs.put("download.default_directory", downloadsPath);
	    ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("prefs", chromePrefs);
	    DesiredCapabilities caps = new DesiredCapabilities();
	    caps.setCapability(ChromeOptions.CAPABILITY, options);
	    return caps;
	}
}