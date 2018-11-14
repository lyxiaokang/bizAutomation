package com.jxrt.biz.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.jxrt.test.TestBase;

public class HomePage extends AbstractPage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	//交易管理菜单
	@FindBy(xpath="//div[@class='ivu-menu-submenu-title' and contains(text(),'交易管理')]")
	private WebElement tradeManagementTab;
	//账款新增菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款新增')]")
	private WebElement ReceivableIssueTab;
	
	//账款查询菜单
	@FindBy(xpath="//li[@class='ivu-menu-item' and contains(text(), '账款查询')]")
	private WebElement ReceivableListTab;
	/*
	 * 进入账款新增菜单
	 */
	public void gotoReceivableIssuePage(){
		tradeManagementTab.click();
		ReceivableIssueTab.click();
	}

}
