package com.jxrt.corp.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.test.TestBase;

public class ReceivableIssuePage  extends AbstractPage {

	public ReceivableIssuePage(WebDriver driver) {
		super(driver);
	}
	
	//供应商名称
	@FindBy(xpath="//span[@class='word large-word' and contains(text(), '供应商名称')]/..//input")
	public WebElement corpNameAcceptInput;
	//供应商查询按钮
	@FindBy(xpath="//span[@class='word large-word' and contains(text(), '供应商名称')]/..//a[contains(text(), '查询')]")
	public WebElement corpNameAcceptSearchBtn;
	//弹出框
	//供应商名称
	@FindBy(xpath="//span[@class='word' and contains(text(), '供应商名称：')]")
	public WebElement selectWindowCorpNameAcceptInput;
	//查询按钮
	@FindBy(xpath="//span[contains(@class,'btn') and text()='查询']")
	public WebElement selectWindowSearchBtn;
	//选择按钮
	@FindBy(xpath="//span[contains(@class,'btn') and text()='选择']")
	public WebElement selectWindowChoseBtn;
	
	//商务合同编号
	@FindBy(xpath="//span[@class='word large-word' and contains(text(), '商务合同编号')]/..//input")
	public WebElement busiContractCodeInput;
	//应付账款金额：
	@FindBy(xpath="//span[@class='word large-word' and contains(text(), '应付账款金额')]/..//input")
	public WebElement applyAmountInput;
	//应付账款到期日 ：
	@FindBy(xpath="//span[@class='word large-word' and contains(text(), '应付账款到期日')]/..//input")
	public WebElement maturityDateInput;
	//摘要
	@FindBy(xpath="//span[@class='word large-word' and contains(text(), '摘要')]/..//input")
	public WebElement abstractInput;
	
	//单笔新增按钮
	@FindBy(xpath="//span[contains(@class,'btn') and text()='单笔新增']")
	public WebElement singleIsuueBtn;
	//批量导入按钮
	@FindBy(xpath="//span[contains(@class,'btn') and text()='批量导入']")
	public WebElement importBtn;
	
	public static void main(String[] args) throws InterruptedException {
		TestBase.setupCorp();
		TestBase.corp.corpLoginPage().login("北冥有鱼", "16690885132", "a1111111");
		Thread.sleep(2000);
		TestBase.corp.homePage().gotoReceivableIssueTab();
		TestBase.corp.receivableIssuePage().busiContractCodeInput.sendKeys("123");
		TestBase.corp.receivableIssuePage().applyAmountInput.sendKeys("123");
		TestBase.corp.receivableIssuePage().abstractInput.sendKeys("123");
		TestBase.corp.receivableIssuePage().corpNameAcceptSearchBtn.click();
		TestBase.corp.receivableIssuePage().setElementColor(TestBase.corp.receivableIssuePage().corpNameAcceptSearchBtn);
		Thread.sleep(1000);
		TestBase.corp.receivableIssuePage().selectWindowCorpNameAcceptInput.sendKeys("鹏程万里");
		TestBase.corp.receivableIssuePage().selectWindowSearchBtn.click();
		Thread.sleep(1000);
		TestBase.corp.receivableIssuePage().selectWindowChoseBtn.click();
		Thread.sleep(1000);
		TestBase.corp.receivableIssuePage().singleIsuueBtn.click();
	}

}
