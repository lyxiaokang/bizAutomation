package com.jxrt.biz.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.biz.Biz;
import com.jxrt.test.TestBase;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(name="mobile")
	private WebElement mobileInput;
	
	@FindBy(name="bizPass")
	private WebElement bizPassInput;
	
	@FindBy(id="submitBtn")
	private WebElement submitBtn;
	
	public void login(String mobile,String bizPass){
		mobileInput.clear();
		mobileInput.sendKeys(mobile);
		bizPassInput.clear();
		bizPassInput.sendKeys(bizPass);
		submitBtn.click();
	}

}
