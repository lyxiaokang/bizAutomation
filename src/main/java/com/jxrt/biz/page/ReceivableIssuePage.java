package com.jxrt.biz.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.test.TestBase;

/*
 * 平台端-交易管理-账款融资管理-账款新增
 */
public class ReceivableIssuePage extends AbstractPage{

	public ReceivableIssuePage(WebDriver driver) {
		super(driver);
	}
	
	//核心企业显示框
	@FindBy(xpath="//label[contains(text(), '核心企业名称：')]/../descendant::input")
	private WebElement corpNameApplyInput;
	//核心企业查询按钮
	@FindBy(xpath="//label[contains(text(), '核心企业名称：')]/../descendant::button")
	private WebElement corpNameApplySearchBtn;
	//核心企业弹出框
	//核心企业输入框
	@FindBy(xpath="//span[contains(text(), '核心企业：')]/../descendant::input")
	private WebElement corpNameCoreInput;
	//核心企业查询按钮
	@FindBy(xpath="//span[contains(text(), '核心企业：')]/../descendant::button")
	private WebElement corpNameCoreSearchBtn;
	//核心企业列表选择按钮
	@FindBy(xpath="//button/span[contains(text(), '选择')]")
	private List<WebElement> corpNameCoreSelectBtns;
	/*
	 * 查找并选择对应核心企业
	 */
	public void searchAndSelectCorpNameCore(String corpNameCore) throws InterruptedException{
		if(corpNameCoreSelectBtns.get(0).isDisplayed())
		{
			corpNameCoreInput.sendKeys(corpNameCore);
			corpNameCoreSearchBtn.click();
			Thread.sleep(2000);
			corpNameCoreSelectBtns.get(0).click();
		}
	}
	//供应商查询按钮
	@FindBy(xpath="//label[contains(text(), '供应商名称：')]/../descendant::input")
	private WebElement corpNameAcceptInput;	
	//供应商查询按钮
	@FindBy(xpath="//label[contains(text(), '供应商名称：')]/../descendant::button")
	private WebElement corpNameAcceptSearchBtn;
	
	//供应商企业弹出框
	//供应商企业输入框
	@FindBy(xpath="//span[contains(text(), '供应商名称：')]/../descendant::input")
	private WebElement corpNameInput;
	//核心企业查询按钮
	@FindBy(xpath="//span[contains(text(), '供应商名称：')]/../descendant::button")
	private WebElement corpNameSearchBtn;
	//核心企业列表选择按钮
	@FindBy(xpath="//td[6]/div/div/button/span")
	private WebElement corpNameSelectBtn;
	/*
	 * 查找并选择对应企业
	 */
	public void searchAndSelectCorpName(String corpName) throws InterruptedException{
		corpNameInput.sendKeys(corpName);
		corpNameSearchBtn.click();
		Thread.sleep(2000);
		corpNameSelectBtn.click();
	}
	
	//商务合同编号
	@FindBy(xpath="//label[contains(text(), '商务合同编号：')]/../descendant::input")
	private WebElement busiContractCodeInput;
	//申请金额
	@FindBy(xpath="//label[contains(text(), '应付账款金额：')]/../descendant::input")
	private WebElement applyAmountInput;
	//承诺付款日期
	@FindBy(xpath="//label[contains(text(), '应付账款到期日：')]/../descendant::input")
	private WebElement maturityDateInput;
	//摘要
	@FindBy(xpath="//label[contains(text(), '摘要：')]/../descendant::input")
	private WebElement abstractInput;
	
	//单笔新增按钮
	@FindBy(xpath="//button/span[contains(text(), '单笔新增')]")
	private WebElement singleIssueBtn;
	
	public void ReceivableIssue() throws InterruptedException{
		corpNameApplySearchBtn.click();
		searchAndSelectCorpNameCore("上海先荣建筑集团有限公司");
		Thread.sleep(1000);
		corpNameAcceptSearchBtn.click();
		searchAndSelectCorpName("陕西盛佳建筑装饰有限公司");
		Thread.sleep(1000);
		busiContractCodeInput.sendKeys("sw");
		applyAmountInput.sendKeys("100");
		maturityDateInput.sendKeys("2018-11-30");
		abstractInput.sendKeys("zy");
		singleIssueBtn.click();
	}
	public static void main(String[] args) throws InterruptedException{

	}
}
