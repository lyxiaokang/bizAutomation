package com.jxrt.biz.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.test.TestBase;

public class HomePage extends AbstractPage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	//白条管理菜单
	@FindBy(xpath="//div[@class='ivu-menu-submenu-title' and contains(text(),'白条管理')]")
	public WebElement creditManagementTab;
	//账款新增菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款新增')]")
	public WebElement ReceivableIssueTab;
	//账款审核菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款审核')]")
	public WebElement ReceivableApproveTab;
	//账款查询菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款查询')]")
	public WebElement ReceivableSearchTab;
	
	//白条信息查询
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '白条信息查询')]")
	public WebElement creditInstructionSearchTab;
	//代发工资申请查询
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '代发工资申请查询')]")
	public WebElement payrollCreditApplySearchTab;
	//融资交互查询（e信通和e点通高级版)
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '融资交互查询（e信通和e点通高级版）')]")
	public WebElement financeInteractiveEXTTab;
	
	
	//付款管理（白条）
	@FindBy(xpath="//div[@class='ivu-menu-submenu-title' and contains(text(),'付款管理（白条）')]")
	public WebElement redeemManagementTab;
	//付款通知书
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '付款通知书')]")
	public WebElement redeemNoticeTab;
	
	
	//菜单
	@FindBy(xpath="//div[@class='ivu-menu-submenu-title' and contains(text(),'白条融资审核')]")
	public WebElement creditFinaceAppoveTab;
	//融资资料审核菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '融资资料审核')]")
	public WebElement finaceDataApproveTab;

	/*
	 * 使账款菜单可见
	 */
	private void setReceivableTabView() throws InterruptedException{
		scrollIntoView(creditInstructionSearchTab);
		Thread.sleep(1000);
		scrollIntoView(payrollCreditApplySearchTab);
	}
	/*
	 * 进入账款新增菜单
	 */
	public void gotoReceivableIssuePage() throws InterruptedException{
		Thread.sleep(1000);
		creditManagementTab.click();
		Thread.sleep(1000);
		setReceivableTabView();
		Thread.sleep(1000);
		ReceivableIssueTab.click();
	}
	
	/*
	 * 进入账款新查询菜单
	 */
	public void gotoReceivableSearchPage() throws InterruptedException{
		Thread.sleep(1000);
		creditManagementTab.click();
		Thread.sleep(1000);
		setReceivableTabView();
		Thread.sleep(1000);
		ReceivableSearchTab.click();
	}
	/*
	 * 进入账款新审核菜单
	 */
	public void gotoReceivableApprovePage() throws InterruptedException{
		Thread.sleep(1000);
		creditManagementTab.click();
		Thread.sleep(1000);
		setReceivableTabView();
		Thread.sleep(1000);
		ReceivableApproveTab.click();
	}
	
	/*
	 * 进入融资资料审核菜单
	 */
	public void gotoFinanceDataApprovePage() throws InterruptedException{
		Thread.sleep(1000);
		creditFinaceAppoveTab.click();
		Thread.sleep(1000);
		finaceDataApproveTab.click();
	}
	
	/*
	 * 进入付款通知书菜单
	 */
	public void gotoRedeemNoticePage() throws InterruptedException{
		Thread.sleep(1000);
		redeemManagementTab.click();
		Thread.sleep(1000);
		redeemNoticeTab.click();
	}
	
	public static void main(String[] args) throws Exception {
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
		TestBase.biz.homePage().gotoRedeemNoticePage();
		Thread.sleep(1000);
		
	}
}
