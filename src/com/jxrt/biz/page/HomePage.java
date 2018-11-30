package com.jxrt.biz.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	//交易管理菜单
	@FindBy(xpath="//div[@class='ivu-menu-submenu-title' and contains(text(),'交易管理')]")
	public WebElement tradeManagementTab;
	//账款新增菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款新增')]")
	public WebElement ReceivableIssueTab;
	//账款审核菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款审核')]")
	public WebElement ReceivableApproveTab;
	//账款查询菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款查询')]")
	public WebElement ReceivableSearchTab;
	
	
	//融资资料管理菜单
	@FindBy(xpath="//div[@class='ivu-menu-submenu-title' and contains(text(),'融资资料管理')]")
	public WebElement finaceDataManagementTab;
	//融资资料审核菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '融资资料审核')]")
	public WebElement finaceDataApproveTab;
	
	/*
	 * 进入账款新增菜单
	 */
	public void gotoReceivableIssuePage() throws InterruptedException{
		Thread.sleep(1000);
		tradeManagementTab.click();
		Thread.sleep(1000);
		ReceivableIssueTab.click();
	}
	
	/*
	 * 进入账款新查询菜单
	 */
	public void gotoReceivableSearchPage() throws InterruptedException{
		Thread.sleep(1000);
		tradeManagementTab.click();
		Thread.sleep(1000);
		ReceivableSearchTab.click();
	}
	/*
	 * 进入账款新审核菜单
	 */
	public void gotoReceivableApprovePage() throws InterruptedException{
		Thread.sleep(1000);
		tradeManagementTab.click();
		Thread.sleep(1000);
		ReceivableApproveTab.click();
	}
	
	/*
	 * 进入融资资料审核菜单
	 */
	public void gotofinanceDataApprovePage() throws InterruptedException{
		Thread.sleep(1000);
		finaceDataManagementTab.click();
		Thread.sleep(1000);
		finaceDataApproveTab.click();
	}
}
