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
 * 平台端-白条管理-代发工资-代发工资申请查询
 */
public class PayrollCreditApplySearchPage extends AbstractPage {

	public PayrollCreditApplySearchPage(WebDriver driver) {
		super(driver);
	}
	
	//tab页
	//代发申请中Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '代发申请中')]")
	public WebElement payrollApplyTab;
	//代发申请中数值
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '代发申请中')]/../span[2]")
	public WebElement payrollApplyTabCount;
	
	//代发申请中Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '签发中')]")
	public WebElement issuingTab;
	//代发申请中数值
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '签发中')]/../span[2]")
	public WebElement issuingTabCount;
	
	//代发申请中Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '融资处理中')]")
	public WebElement finacingTab;
	//代发申请中数值
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '融资处理中')]/../span[2]")
	public WebElement finacingTabCount;
	
	//代发申请中Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '已放款')]")
	public WebElement loanedTab;
	//代发申请中数值
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '已放款')]/../span[2]")
	public WebElement loanedTabCount;
	
	//核心企业输入框
	@FindBy(xpath="//span[contains(text(), '核心企业：')]/../descendant::input")
	public WebElement corpNameCoreInput;
	
	//链条企业输入框
	@FindBy(xpath="//span[contains(text(), '链条企业：')]/../descendant::input")
	public WebElement corpNameInput;
	
	//合作平台输入框
	@FindBy(xpath="//span[contains(text(), '合作平台：')]/../descendant::input")
	public WebElement partnerNameInput;
	
	//创建时间开始
	@FindBy(xpath="//span[contains(text(), '创建时间：')]/../descendant::input[1]")
	public WebElement redeemDateBeginInput;
	
	//创建时间结束
	@FindBy(xpath="//span[contains(text(), '创建时间：')]/../descendant::input[2]")
	public WebElement redeemDateEndInput;
	
	//状态
	@FindBy(xpath="//span[contains(text(), '状态：')]/../div")
	public WebElement state;
	//新增中
	@FindBy(xpath="//div/ul/li[contains(text(), '新增中')]")
	public WebElement stateINCREASING;
	//新增失败
	@FindBy(xpath="//div/ul/li[contains(text(), '新增失败')]")
	public WebElement stateINCREASE_FAIL;
	//待提交
	@FindBy(xpath="//div/ul/li[contains(text(), '待提交')]")
	public WebElement stateSUBMITTING;
	//待确认
	@FindBy(xpath="//div/ul/li[contains(text(), '待确认')]")
	public WebElement stateCOMMITTING;
	//核心企业审核不通过
	@FindBy(xpath="//div/ul/li[contains(text(), '核心企业审核不通过')]")
	public WebElement stateAPPROVE_FAIL;
	
	//代发申请中表单
	//代发申请中Index
	@FindBy(xpath="//table/tbody/tr/td[1]/div")
	public List<WebElement> payrollListIndexs;
	//代发申请中核心企业
	@FindBy(xpath="//table/tbody/tr/td[2]/div/div/p[1]")
	public List<WebElement> payrollListCorpNameCores;
	//代发申请中链条企业
	@FindBy(xpath="//table/tbody/tr/td[2]/div/div/p[2]")
	public List<WebElement> payrollListCorpNames;
	//代发申请中平台产品
	@FindBy(xpath="//table/tbody/tr/td[3]/div")
	public List<WebElement> payrollListProductTypeCcbscfs;
	//代发申请中合作平台
	@FindBy(xpath="//table/tbody/tr/td[4]/div")
	public List<WebElement> payrollListPartnerNames;
	//代发申请中项目名称
	@FindBy(xpath="//table/tbody/tr/td[5]/div")
	public List<WebElement> payrollListProjectNames;
	//代发申请中班组
	@FindBy(xpath="//table/tbody/tr/td[6]/div")
	public List<WebElement> payrollListBatchTeamGroup;
	//代发申请中工资期限
	@FindBy(xpath="//table/tbody/tr/td[7]/div")
	public List<WebElement> payrollListPayrollDurings;
	//代发申请中代发笔数
	@FindBy(xpath="//table/tbody/tr/td[8]/div")
	public List<WebElement> payrollListPaymentDetailCounts;
	//代发申请中总金额
	@FindBy(xpath="//table/tbody/tr/td[8]/div")
	public List<WebElement> payrollListPaymentDetailTotalAmounts;
	//代发申请中创建时间
	@FindBy(xpath="//table/tbody/tr/td[9]/div")
	public List<WebElement> payrollListCreatTimes;
	//代发申请中状态
	@FindBy(xpath="//table/tbody/tr/td[10]/div")
	public List<WebElement> payrollListStates;
	
	public static void main(String[] args) throws InterruptedException, SQLException {
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2, TestBase.operateManagerPasswordTeam2);
		TestBase.biz.homePage().gotoPayrollCreditApplySearchPage();
		Thread.sleep(2000);
		
	}

}
