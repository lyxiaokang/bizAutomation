package com.jxrt.biz.page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
	final String xpathFinal="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[2]/div[1]";
	//tab页
	//代发申请中Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '代发申请中')]")
	public WebElement payrollApplyTab;
	//代发申请中数值
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '代发申请中')]/../span[2]")
	public WebElement payrollApplyTabCount;
	
	//签发中Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '签发中')]")
	public WebElement issuingTab;
	//签发中数值
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '签发中')]/../span[2]")
	public WebElement issuingTabCount;
	
	//融资处理中Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '融资处理中')]")
	public WebElement finacingTab;
	//融资处理中数值
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '融资处理中')]/../span[2]")
	public WebElement finacingTabCount;
	
	//已放款Tab
	@FindBy(xpath="//div[contains(@class,'ivu-tabs-tab')]/div/span[contains(text(), '已放款')]")
	public WebElement loanedTab;
	//已放款数值
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
	public WebElement createDateBeginInput;
	
	//创建时间结束
	@FindBy(xpath="//span[contains(text(), '创建时间：')]/../descendant::input[2]")
	public WebElement createDateEndInput;
	
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
	//查询按钮
	@FindBy(xpath="//button/span[contains(text(), '查询')]")
	public WebElement searchBtn;
	
	
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
	@FindBy(xpath="//table/tbody/tr/td[8]/div/div/p[1]")
	public List<WebElement> payrollListPaymentDetailCounts;
	//代发申请中总金额
	@FindBy(xpath="//table/tbody/tr/td[8]/div/div/p[2]")
	public List<WebElement> payrollListPaymentDetailTotalAmounts;
	//代发申请中创建时间
	@FindBy(xpath="//table/tbody/tr/td[9]/div")
	public List<WebElement> payrollListCreatTimes;
	//代发申请中状态
	@FindBy(xpath="//table/tbody/tr/td[10]/div")
	public List<WebElement> payrollListStates;
	
	//总计：
	@FindBy(xpath="//span[contains(text(), '总计：')]")
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
	 * 切换tab页
	 */
	public void changeTab(WebElement element) throws InterruptedException{
		element.click();
		Thread.sleep(2000);
	}
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
			BigDecimal payrollTotalAmount=new BigDecimal(matcher.group(1).replaceAll(",", ""));
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
	
	public void payrollCreditNum(String corpNameCore,String corpName,String partnerName,
			LocalDate createTimeBegin,LocalDate createTimeEnd,String state) throws InterruptedException, SQLException{
		//确认代发申请中页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);
		int lastPageCreditNum=payrollListIndexs.size();
		int CreditCount=lastPageNum*10+lastPageCreditNum-10;
		System.out.println(CreditCount);

		//通过数据库查询代发申请中白条数量和金额
		long oracleCount=OracleDataFactory.getPayrollCreditCount(corpNameCore,corpName,partnerName,
				createTimeBegin,createTimeEnd,state);
		BigDecimal oracleAmount=OracleDataFactory.getPayrollCreditAmount(corpNameCore,corpName,partnerName,
				createTimeBegin,createTimeEnd,state);
		
		Assert.assertEquals(CreditCount, oracleCount);
		Assert.assertEquals(getPayrollTotalCount(), oracleCount);
		Assert.assertEquals(getPayrollTotalAmount(), oracleAmount);
		//恢复到第一页
		firstPage.click();
	}
	
	public void payrollCreditList(String corpNameCore,String corpName,String partnerName,
			LocalDate createTimeBegin,LocalDate createTimeEnd,String state) throws InterruptedException, SQLException{
		//确认代发申请中页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);

		//通过数据库查询代发申请中白条总数
		List<Map<String, String>> oracleList=OracleDataFactory.getPayrollCreditList(corpNameCore,corpName,partnerName,
				createTimeBegin,createTimeEnd,state);
		for(int i=0;i<payrollListCorpNameCores.size();i++){
			Assert.assertEquals(payrollListCorpNameCores.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpNameCore"));
			Assert.assertEquals(payrollListCorpNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpName"));
			Assert.assertEquals(payrollListProductTypeCcbscfs.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get((lastPageNum*10-10)+i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(payrollListPartnerNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("partnerName"));
			Assert.assertEquals(payrollListProjectNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("projectName"));
			Assert.assertEquals(payrollListBatchTeamGroup.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("batchTeamGroup"));
			Assert.assertEquals(payrollListPayrollDurings.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("payrollDuring"));
			Assert.assertEquals(payrollListPaymentDetailCounts.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("payrollDetailCount"));
			Assert.assertEquals(payrollListPaymentDetailTotalAmounts.get(i).getText().replaceAll(",", ""), oracleList.get((lastPageNum*10-10)+i).get("paymentDetailTotalAmount"));
			Assert.assertTrue(oracleList.get((lastPageNum*10-10)+i).get("createTime").contains(payrollListCreatTimes.get(i).getText()));
			
		}
		//断言数据库查询总笔数和金额与页面左下角一致
		if(oracleList.size()!=0){
			Assert.assertEquals(getPayrollTotalAmount(), OracleDataFactory.getPayrollCreditAmount(corpNameCore, corpName, partnerName, createTimeBegin, createTimeEnd, state));
			Assert.assertEquals(getPayrollTotalCount(), OracleDataFactory.getPayrollCreditCount(corpNameCore, corpName, partnerName, createTimeBegin, createTimeEnd, state));
		}
		//恢复到第一页
		firstPage.click();
		Thread.sleep(4000);
		for(int i=0;i<payrollListCorpNameCores.size();i++){
			Assert.assertEquals(payrollListCorpNameCores.get(i).getText(), oracleList.get(i).get("corpNameCore"));
			Assert.assertEquals(payrollListCorpNames.get(i).getText(), oracleList.get(i).get("corpName"));
			Assert.assertEquals(payrollListProductTypeCcbscfs.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get(i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(payrollListPartnerNames.get(i).getText(), oracleList.get(i).get("partnerName"));
			Assert.assertEquals(payrollListProjectNames.get(i).getText(), oracleList.get(i).get("projectName"));
			Assert.assertEquals(payrollListBatchTeamGroup.get(i).getText(), oracleList.get(i).get("batchTeamGroup"));
			Assert.assertEquals(payrollListPayrollDurings.get(i).getText(), oracleList.get(i).get("payrollDuring"));
			Assert.assertEquals(payrollListPaymentDetailCounts.get(i).getText(), oracleList.get(i).get("payrollDetailCount"));
			Assert.assertEquals(payrollListPaymentDetailTotalAmounts.get(i).getText().replaceAll(",", ""), oracleList.get(i).get("paymentDetailTotalAmount"));
			Assert.assertTrue(oracleList.get(i).get("createTime").contains(payrollListCreatTimes.get(i).getText()));
		}
	}
	public static void main(String[] args) throws InterruptedException, SQLException {
		TestBase.setupBiz();

	}

}
