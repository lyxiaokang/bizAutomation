package com.jxrt.biz;

import org.openqa.selenium.WebDriver;
import com.jxrt.biz.page.*;

public class Biz {
	private final WebDriver driver;

	public Biz(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	/*
	 * 登录页初始化
	 */
	public LoginPage bizLoginPage() {
		return new LoginPage(driver);
	}
	
	/*
	 * 首页初始化
	 */
	public HomePage homePage() {
		return new HomePage(driver);
	}
	
	/*
	 * 首页初始化
	 */
	public ReceivableIssuePage receivableIssuePage() {
		return new ReceivableIssuePage(driver);
	}
}
