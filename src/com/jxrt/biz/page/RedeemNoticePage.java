package com.jxrt.biz.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.test.TestBase;
/*
 * 平台端-付款管理（白条）-付款通知书
 */
public class RedeemNoticePage extends AbstractPage {

	public RedeemNoticePage(WebDriver driver) {
		super(driver);
	}
	
	//核心企业输入框
	@FindBy(xpath="//span[contains(text(), '核心企业：')]/../descendant::input")
	public WebElement corpNameCoreInput;
	
	//链条企业输入框
	@FindBy(xpath="//span[contains(text(), '链条企业：')]/../descendant::input")
	public WebElement corpNameInput;
	
	//平台产品
	@FindBy(xpath="//span[contains(text(), '平台产品：')]/../div/descendant::span[1]")
	public WebElement productType;
	//产品类型全部
	@FindBy(xpath="//div/ul/li[contains(text(), '融信')]")
	public WebElement productTypeCredit;
	//产品类型e点通
	@FindBy(xpath="//div/ul/li[contains(text(), '账款融资')]")
	public WebElement productTypeReceivable;
	//产品类型e点通
	@FindBy(xpath="//div/ul/li[contains(text(), '云条')]")
	public WebElement productTypeNote;
	
	//付款日期开始
	@FindBy(xpath="//span[contains(text(), '付款到期日：')]/../descendant::input[1]")
	public WebElement redeemDateBeginInput;
	
	//付款日期结束
	@FindBy(xpath="//span[contains(text(), '付款到期日：')]/../descendant::input[2]")
	public WebElement redeemDateEndInput;
	
	//查询按钮
	@FindBy(xpath="//button/span[contains(text(), '查询')]")
	public WebElement searchBtn;
	//查询按钮
	@FindBy(xpath="//button/span[contains(text(), '生成通知书')]")
	public WebElement generateNoticeBtn;
	
	//付款白条表单
	//付款白条checkbox
	@FindBy(xpath="//table/tbody/tr/td[1]/div/label")
	public List<WebElement> redeemListCheckBoxs;
	//付款白条Index
	@FindBy(xpath="//table/tbody/tr/td[2]/div")
	public List<WebElement> redeemListIndexs;
	//付款白条白条编号
	@FindBy(xpath="//table/tbody/tr/td[3]/div")
	public List<WebElement> redeemListPkCredits;
	//付款白条Core
	@FindBy(xpath="//table/tbody/tr/td[4]/div")
	public List<WebElement> redeemListCorpNameCores;
	//付款白条供应商名称
	@FindBy(xpath="//table/tbody/tr/td[5]/div")
	public List<WebElement> redeemListCorpNames;
	//付款白条产品类型
	@FindBy(xpath="//table/tbody/tr/td[6]/div")
	public List<WebElement> redeemListProductTypes;
	//付款白条签发日期
	@FindBy(xpath="//table/tbody/tr/td[7]/div")
	public List<WebElement> redeemListIssueDates;
	//付款白条承诺付款金额
	@FindBy(xpath="//table/tbody/tr/td[8]/div")
	public List<WebElement> redeemListMaturityAmounts;
	//付款白条付款到期日
	@FindBy(xpath="//table/tbody/tr/td[9]/div")
	public List<WebElement> redeemListMaturityDates;
	//付款白已付款金额
	@FindBy(xpath="//table/tbody/tr/td[10]/div")
	public List<WebElement> redeemListRedeemedAmounts;
	//付款白条待付款金额
	@FindBy(xpath="//table/tbody/tr/td[11]/div")
	public List<WebElement> redeemListRedeemAmounts;
	//付款白条白条状态
	@FindBy(xpath="//table/tbody/tr/td[12]/div")
	public List<WebElement> redeemListCreditStates;

	
	
	public static void main(String[] args) throws InterruptedException {
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
		TestBase.biz.homePage().gotoRedeemNoticePage();
		Thread.sleep(1000);
		
	}

}
