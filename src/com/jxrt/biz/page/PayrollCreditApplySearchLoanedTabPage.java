package com.jxrt.biz.page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jxrt.common.constans.ProductTypeCcbscfEnum;
import com.jxrt.dbutil.OracleDataFactory;
import com.jxrt.test.TestBase;

import junit.framework.Assert;
/*
 * 平台端-白条管理-代发工资-代发工资申请查询-已放款tab页
 */
public class PayrollCreditApplySearchLoanedTabPage extends AbstractPage {

	public PayrollCreditApplySearchLoanedTabPage(WebDriver driver) {
		super(driver);
	}
	
	final String xpathFinal="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[2]/div[4]";
	//核心企业输入框
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '核心企业：')]/../descendant::input")
	public WebElement corpNameCoreInput;
	
	//链条企业输入框
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '链条企业：')]/../descendant::input")
	public WebElement corpNameInput;
	
	//代发处理状态
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '代发处理状态：')]/../div")
	public WebElement payrollState;
	//融资审核中（链条企业）
	@FindBy(xpath=xpathFinal+"//div/ul/li[contains(text(), '成功')]")
	public WebElement payrollStatePass;
	//融资审核中（建信融通）
	@FindBy(xpath=xpathFinal+"//div/ul/li[contains(text(), '失败')]")
	public WebElement payrollStateFail;
	//融资审核中（金融机构）
	@FindBy(xpath=xpathFinal+"//div/ul/li[contains(text(), '待处理')]")
	public WebElement payrollStateUndo;
	//融资审核中（金融机构）
	@FindBy(xpath=xpathFinal+"//div/ul/li[contains(text(), '处理中')]")
	public WebElement payrollStateDoing;
	
	//白条编号输入框
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '白条编号：')]/../descendant::input")
	public WebElement pkCreditInput;
	
	//创建日期开始
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '创建日期：')]/../descendant::input[1]")
	public WebElement createDateBeginInput;
	
	//创建日期结束
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '创建日期：')]/../descendant::input[2]")
	public WebElement createDateEndInput;
	
	//放款时间开始
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '放款时间：')]/../descendant::input[1]")
	public WebElement loanTimeBeginInput;
	
	//放款时间结束
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '放款时间：')]/../descendant::input[2]")
	public WebElement loanTimeEndInput;

	//查询按钮
	@FindBy(xpath=xpathFinal+"//button/span[contains(text(), '查询')]")
	public WebElement searchBtn;
	
	
	//已放款表单
	//已放款Index
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[1]/div")
	public List<WebElement> payrollListIndexs;
	//已放款白条编号
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[2]/div")
	public List<WebElement> payrollListPkCredits;
	//已放款核心企业
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[3]/div/div/p[1]")
	public List<WebElement> payrollListCorpNameCores;
	//已放款链条企业
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[3]/div/div/p[2]")
	public List<WebElement> payrollListCorpNames;
	//已放款平台产品
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[4]/div")
	public List<WebElement> payrollListProductTypeCcbscfs;
	//已放款代发笔数
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[5]/div/div/p[1]")
	public List<WebElement> payrollListPaymentDetailCounts;
	//已放款总金额
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[5]/div/div/p[2]")
	public List<WebElement> payrollListPaymentDetailTotalAmounts;
	//已放款合作平台
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[6]/div")
	public List<WebElement> payrollListPartnerNames;
	//已放款放款时间
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[7]/div")
	public List<WebElement> payrollListLoanedTimes;
	//已放款代发时间
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[8]/div")
	public List<WebElement> payrollListPayrollTimes;
	//已放款代发处理状态
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[9]/div")
	public List<WebElement> payrollListPayrollStates;
	//已放款成功笔数
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[10]/div/div/p[1]")
	public List<WebElement> payrollListPaymentDetailCountSuccesss;
	//已放款成功总金额
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[10]/div/div/p[2]")
	public List<WebElement> payrollListPaymentDetailTotalAmountSuccesss;
	//已放款失败笔数
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[11]/div/div/p[1]")
	public List<WebElement> payrollListPaymentDetailCountFails;
	//已放款失败总金额
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[11]/div/div/p[2]")
	public List<WebElement> payrollListPaymentDetailTotalAmountFails;
	
	//总计：
	@FindBy(xpath=xpathFinal+"//div[3]/span[contains(text(), '总计：')]")
	public WebElement totalAmountAndCount;
	//页码
	@FindBy(xpath=xpathFinal+"//ul[@class='ivu-page mini']/li[@class='ivu-page-item' or @class='ivu-page-item ivu-page-item-active']/a")
	public List<WebElement> pageNums;
	//活动页码
	@FindBy(xpath=xpathFinal+"//ul[@class='ivu-page mini']/li[@class='ivu-page-item ivu-page-item-active']/a")
	public WebElement activePageNum;
	//上一页按钮
	@FindBy(xpath=xpathFinal+"//ul[@class='ivu-page mini']/li[contains(@class,'ivu-page-prev')]")
	public WebElement prevPageNum;
	//下一页按钮
	@FindBy(xpath=xpathFinal+"//ul[@class='ivu-page mini']/li[contains(@class,'ivu-page-next')]")
	public WebElement nextPageNum;
	//第一页
	@FindBy(xpath=xpathFinal+"//ul[@class='ivu-page mini']/li[@class='ivu-page-item' or @class='ivu-page-item ivu-page-item-active']/a[text()='1']")
	public WebElement firstPage;	
	
	/*
	 * 获取最后一页页码
	 */
	public int getLastPageNum(){
		return Integer.parseInt(pageNums.get(pageNums.size()-1).getText());
	}
	/*
	 * 获取最后一页元素
	 */
	public WebElement getLastPageElement(){
		return pageNums.get(pageNums.size()-1);
	}
	
	/*
	 * 获取总计代发金额
	 */
	public BigDecimal getPayrollTotalAmount(){
		String totalAmountAndCountString=totalAmountAndCount.getText();
		Pattern pattern = Pattern.compile("代发金额(.*?)元");
		Matcher matcher = pattern.matcher(totalAmountAndCountString);
		if(matcher.find()){
			BigDecimal payrollTotalAmount=new BigDecimal(matcher.group(1).trim().replaceAll(",", ""));
			return payrollTotalAmount;
		}
		return new BigDecimal(0);
	}
	
	/*
	 * 获取总计代发总数
	 */
	public long getPayrollTotalCount(){
		String totalAmountAndCountString=totalAmountAndCount.getText();
		Pattern pattern = Pattern.compile("元，(.*?)条数据");
		Matcher matcher = pattern.matcher(totalAmountAndCountString);
		if(matcher.find()){
			long payrollTotalCount=Long.parseLong(matcher.group(1).replaceAll(",", ""));
			return payrollTotalCount;
		}
		return 0;
	}
	
	public void payrollCreditNum(String corpNameCore,String corpName,String payrollState,String pkCredit,
			LocalDate createDateBegin,LocalDate createDateEnd,LocalDate loanTimeBegin,LocalDate loanTimeEnd) throws Exception{
		//确认已放款页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);
		int lastPageCreditNum=payrollListIndexs.size();
		int CreditCount=lastPageNum*10+lastPageCreditNum-10;
		System.out.println(CreditCount);

		//通过数据库查询已放款白条数量和金额
		long oracleCount=OracleDataFactory.getPayrollCreditLoanedCount(corpNameCore,corpName,payrollState,pkCredit,
				createDateBegin,createDateEnd,loanTimeBegin,loanTimeEnd);
		BigDecimal oracleAmount=OracleDataFactory.getPayrollCreditLoanedAmount(corpNameCore,corpName,payrollState,pkCredit,
				createDateBegin,createDateEnd,loanTimeBegin,loanTimeEnd);
		
		Assert.assertEquals(CreditCount, oracleCount);
		Assert.assertEquals(getPayrollTotalCount(), oracleCount);
		Assert.assertEquals(getPayrollTotalAmount(), oracleAmount);
		//恢复到第一页
		firstPage.click();
	}
	
	public void payrollCreditLoanedList(String corpNameCore,String corpName,String payrollState,String pkCredit,
			LocalDate createDateBegin,LocalDate createDateEnd,LocalDate loanTimeBegin,LocalDate loanTimeEnd) throws Exception{
		//确认已放款页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);

		//通过数据库查询已放款白条总数
		List<Map<String, String>> oracleList=OracleDataFactory.getPayrollCreditLoanedList(corpNameCore,corpName,payrollState,pkCredit,
				createDateBegin,createDateEnd,loanTimeBegin,loanTimeEnd);
		for(int i=0;i<payrollListCorpNameCores.size();i++){
			Assert.assertEquals(payrollListPkCredits.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("pkCredit"));
			Assert.assertEquals(payrollListCorpNameCores.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpNameCore"));
			Assert.assertEquals(payrollListCorpNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpName"));
			Assert.assertEquals(payrollListProductTypeCcbscfs.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get(i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(payrollListPaymentDetailCounts.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("payrollDetailCount"));
			Assert.assertEquals(payrollListPaymentDetailTotalAmounts.get(i).getText().replaceAll(",", ""), oracleList.get((lastPageNum*10-10)+i).get("paymentDetailTotalAmount"));
			Assert.assertEquals(payrollListPartnerNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("partnerName"));
			Assert.assertTrue(oracleList.get((lastPageNum*10-10)+i).get("loanTime").contains(payrollListLoanedTimes.get(i).getText()));
	
		}
		//断言数据库查询总笔数和金额与页面左下角一致
		if(oracleList.size()!=0){
			Assert.assertEquals(getPayrollTotalAmount(), OracleDataFactory.getPayrollCreditLoanedAmount(corpNameCore,corpName,payrollState,pkCredit,
					createDateBegin,createDateEnd,loanTimeBegin,loanTimeEnd));
			Assert.assertEquals(getPayrollTotalCount(), OracleDataFactory.getPayrollCreditLoanedCount(corpNameCore,corpName,payrollState,pkCredit,
					createDateBegin,createDateEnd,loanTimeBegin,loanTimeEnd));
		}
		//恢复到第一页
		firstPage.click();
		Thread.sleep(4000);
		for(int i=0;i<payrollListCorpNameCores.size();i++){
			Assert.assertEquals(payrollListPkCredits.get(i).getText(), oracleList.get(i).get("pkCredit"));
			Assert.assertEquals(payrollListCorpNameCores.get(i).getText(), oracleList.get(i).get("corpNameCore"));
			Assert.assertEquals(payrollListCorpNames.get(i).getText(), oracleList.get(i).get("corpName"));
			Assert.assertEquals(payrollListProductTypeCcbscfs.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get(i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(payrollListPaymentDetailCounts.get(i).getText(), oracleList.get(i).get("payrollDetailCount"));
			Assert.assertEquals(payrollListPaymentDetailTotalAmounts.get(i).getText().replaceAll(",", ""), oracleList.get(i).get("paymentDetailTotalAmount"));
			Assert.assertEquals(payrollListPartnerNames.get(i).getText(), oracleList.get(i).get("partnerName"));
			Assert.assertTrue(oracleList.get(i).get("loanTime").contains(payrollListLoanedTimes.get(i).getText()));
		}
	}
	public static void main(String[] args) throws Exception {
		TestBase.setupBiz();

	}

}
