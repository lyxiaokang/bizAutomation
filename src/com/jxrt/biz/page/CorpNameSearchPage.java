package com.jxrt.biz.page;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.common.constans.CreditStateEnum;
import com.jxrt.common.constans.ProductTypeCcbscfEnum;
import com.jxrt.dbutil.OracleDataFactory;
import com.jxrt.test.TestBase;

import junit.framework.Assert;
/*
 * 平台端-快捷查询-供应商查询
 */
public class CorpNameSearchPage extends AbstractPage {

	public CorpNameSearchPage(WebDriver driver) {
		super(driver);
	}
	
	//核心企业输入框
	@FindBy(xpath="//span[contains(text(), '企业名称：')]/..//input")
	public WebElement corpNameInput;
	//按钮
	@FindBy(xpath="//button/span[contains(text(), '查询供应商')]")
	public WebElement searchBtn;
	
	

	public static void main(String[] args) throws InterruptedException, SQLException {
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		TestBase.biz.homePage().gotoCorpNameSearchPage();
		Thread.sleep(2000);
		TestBase.biz.corpNameSearchPage().corpNameInput.clear();
		TestBase.biz.corpNameSearchPage().corpNameInput.sendKeys("太平链条企业一");
		TestBase.biz.corpNameSearchPage().setElementColor(TestBase.biz.corpNameSearchPage().corpNameInput);
		TestBase.biz.corpNameSearchPage().searchBtn.click();
	}

}
