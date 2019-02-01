package com.jxrt.corp.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.test.TestBase;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(name="name")
	private WebElement corpNameInput;	
	@FindBy(name="mobile")
	private WebElement mobileInput;
	@FindBy(id="password")
	public WebElement passwordInput;
	
	@FindBy(xpath="//input[@class='sub pointer']")
	private WebElement loginBtn;
	
	public void login(String corpName,String mobile,String password){
		corpNameInput.clear();
		corpNameInput.sendKeys(corpName);
		mobileInput.clear();
		mobileInput.sendKeys(mobile);
		//必须点击一下，否则输入时会自动带入密码，再次输入会出现密码错误
		passwordInput.click();
		passwordInput.clear();
		passwordInput.sendKeys(password);
		loginBtn.click();
	}
	


	public static void main(String[] args){
		TestBase.setupCorp();
		TestBase.corp.corpLoginPage().login("北冥有鱼", "16690885132", "a1111111");
	}
}
