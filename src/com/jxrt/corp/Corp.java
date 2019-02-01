package com.jxrt.corp;

import org.openqa.selenium.WebDriver;
import com.jxrt.corp.page.*;

public class Corp {
	private final WebDriver driver;

	public Corp(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	/*
	 * 登录页初始化
	 */
	public LoginPage corpLoginPage() {
		return new LoginPage(driver);
	}
	/*
	 * 首页初始化
	 */
	public HomePage homePage() {
		return new HomePage(driver);
	}
	/*
	 * 账款签发页面初始化
	 */
	public ReceivableIssuePage receivableIssuePage() {
		return new ReceivableIssuePage(driver);
	}
	
}
