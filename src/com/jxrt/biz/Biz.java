package com.jxrt.biz;

import org.openqa.selenium.WebDriver;
import com.jxrt.biz.page.*;

public class Biz {
	private final WebDriver driver;

	public Biz(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	/*
	 * 登录页初始化
	 */
	public LoginPage bizLoginPage() {
		return new LoginPage(driver);
	}
	
	/*
	 * 首页初始化
	 */
	public HomePage homePage() {
		return new HomePage(driver);
	}
	
	/*
	 * 账款签发初始化
	 */
	public ReceivableIssuePage receivableIssuePage() {
		return new ReceivableIssuePage(driver);
	}
	/*
	 * 账款查询初始化
	 */
	public ReceivableSearchPage receivableSearchPage() {
		return new ReceivableSearchPage(driver);
	}
	/*
	 * 账款查询已确认初始化
	 */
	public ReceivableSearchIssuedTabPage receivableSearchIssuedTabPage() {
		return new ReceivableSearchIssuedTabPage(driver);
	}
	/*
	 * 账款审核初始化
	 */
	public ReceivableApprovePage receivableApprovePage() {
		return new ReceivableApprovePage(driver);
	}
	
	/*
	 * 融资资料审核初始化
	 */
	public FinanceDataApprovePage financeDataApprove() {
		return new FinanceDataApprovePage(driver);
	}
	
	/*
	 * 付款通知书页面初始化
	 */
	public RedeemNoticePage redeemNoticePage() {
		return new RedeemNoticePage(driver);
	}
	
	/*
	 * 代发工资申请查询页面初始化
	 */
	public PayrollCreditApplySearchPage payrollCreditApplySearchPage() {
		return new PayrollCreditApplySearchPage(driver);
	}
	
	/*
	 * 代发工资申请查询签收中tab页面初始化
	 */
	public PayrollCreditApplySearchIssuingTabPage payrollCreditApplySearchIssuingTabPage() {
		return new PayrollCreditApplySearchIssuingTabPage(driver);
	}
	
	/*
	 * 代发工资申请查询融资中tab页面初始化
	 */
	public PayrollCreditApplySearchFinancingTabPage payrollCreditApplySearchFinancingTabPage() {
		return new PayrollCreditApplySearchFinancingTabPage(driver);
	}
	
	/*
	 * 代发工资申请查询已放款tab页面初始化
	 */
	public PayrollCreditApplySearchLoanedTabPage payrollCreditApplySearchLoanedTabPage() {
		return new PayrollCreditApplySearchLoanedTabPage(driver);
	}
	
	
	
}
