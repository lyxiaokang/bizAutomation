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

import com.jxrt.common.constans.CreditStateEnum;
import com.jxrt.common.constans.ProductTypeCcbscfEnum;
import com.jxrt.dbutil.OracleDataFactory;
import com.jxrt.test.TestBase;

import junit.framework.Assert;
/*
 * 平台端-白条管理-代发工资-代发工资申请查询-融资处理中tab页
 */
public class PayrollCreditApplySearchFinancingTabPage extends AbstractPage {

	public PayrollCreditApplySearchFinancingTabPage(WebDriver driver) {
		super(driver);
	}
	
	final String xpathFinal="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div/div[2]/div[3]";
	//核心企业输入框
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '核心企业：')]/../descendant::input")
	public WebElement corpNameCoreInput;
	
	//链条企业输入框
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '链条企业：')]/../descendant::input")
	public WebElement corpNameInput;
	
	//白条编号输入框
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '白条编号：')]/../descendant::input")
	public WebElement pkCreditInput;
	
	//合作平台输入框
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '合作平台：')]/../descendant::input")
	public WebElement partnerNameInput;
	
	//融资申请日期开始
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '融资申请日期：')]/../descendant::input[1]")
	public WebElement financeApplyDateBeginInput;
	
	//融资申请日期结束
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '融资申请日期：')]/../descendant::input[2]")
	public WebElement financeApplyDateEndInput;
	
	//融资申请状态
	@FindBy(xpath=xpathFinal+"//span[contains(text(), '融资申请状态：')]/../div")
	public WebElement creditState;
	//融资审核中（链条企业）
	@FindBy(xpath=xpathFinal+"//div/ul/li[contains(text(), '融资审核中（链条企业）')]")
	public WebElement creditStateFI1;
	//融资审核中（建信融通）
	@FindBy(xpath=xpathFinal+"//div/ul/li[contains(text(), '融资审核中（建信融通）')]")
	public WebElement creditStateFI2;
	//融资审核中（金融机构）
	@FindBy(xpath=xpathFinal+"//div/ul/li[contains(text(), '融资审核中（金融机构）')]")
	public WebElement creditStateFI3;
	
	//查询按钮
	@FindBy(xpath=xpathFinal+"//button/span[contains(text(), '查询')]")
	public WebElement searchBtn;
	
	
	//融资处理中表单
	//融资处理中Index
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[1]/div")
	public List<WebElement> payrollListIndexs;
	//融资处理中核心企业
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[2]/div/div/p[1]")
	public List<WebElement> payrollListCorpNameCores;
	//融资处理中链条企业
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[2]/div/div/p[2]")
	public List<WebElement> payrollListCorpNames;
	//融资处理中平台产品
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[3]/div")
	public List<WebElement> payrollListProductTypeCcbscfs;
	//融资处理中合作平台
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[4]/div")
	public List<WebElement> payrollListPartnerNames;
	//融资处理中融资申请日期
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[5]/div")
	public List<WebElement> payrollListFinanceApplyDates;
	//融资处理中代发笔数
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[6]/div/div/p[1]")
	public List<WebElement> payrollListPaymentDetailCounts;
	//融资处理中总金额
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[6]/div/div/p[2]")
	public List<WebElement> payrollListPaymentDetailTotalAmounts;
	//融资处理中白条编号
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[7]/div/div/p[1]")
	public List<WebElement> payrollListPkCredits;
	//融资处理中白条状态
	@FindBy(xpath=xpathFinal+"//table/tbody/tr/td[7]/div/div/p[2]")
	public List<WebElement> payrollListCreditStates;
	
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
	
	public void payrollCreditNum(String corpNameCore,String corpName,String pkCredit,String partnerName,
			LocalDate financeApplyDateBegin,LocalDate financeApplyDateEnd,String creditState) throws InterruptedException, SQLException{
		//确认融资处理中页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);
		int lastPageCreditNum=payrollListIndexs.size();
		int CreditCount=lastPageNum*10+lastPageCreditNum-10;
		System.out.println(CreditCount);

		//通过数据库查询融资处理中白条数量和金额
		long oracleCount=OracleDataFactory.getPayrollCreditFinancingCount(corpNameCore,corpName,pkCredit,partnerName,
				financeApplyDateBegin,financeApplyDateEnd,creditState);
		BigDecimal oracleAmount=OracleDataFactory.getPayrollCreditFinancingAmount(corpNameCore,corpName,pkCredit,partnerName,
				financeApplyDateBegin,financeApplyDateEnd,creditState);
		
		Assert.assertEquals(CreditCount, oracleCount);
		Assert.assertEquals(getPayrollTotalCount(), oracleCount);
		Assert.assertEquals(getPayrollTotalAmount(), oracleAmount);
		//恢复到第一页
		firstPage.click();
	}
	
	public void payrollCreditFinancingList(String corpNameCore,String corpName,String pkCredit,String partnerName,
			LocalDate financeApplyDateBegin,LocalDate financeApplyDateEnd,String creditState) throws InterruptedException, SQLException{
		//确认融资处理中页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);

		//通过数据库查询融资处理中白条总数
		List<Map<String, String>> oracleList=OracleDataFactory.getPayrollCreditFinancingList(corpNameCore,corpName,pkCredit,partnerName,
				financeApplyDateBegin,financeApplyDateEnd,creditState);
		for(int i=0;i<payrollListCorpNameCores.size();i++){
			Assert.assertEquals(payrollListCorpNameCores.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpNameCore"));
			Assert.assertEquals(payrollListCorpNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpName"));
			Assert.assertEquals(payrollListProductTypeCcbscfs.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get((lastPageNum*10-10)+i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(payrollListPartnerNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("partnerName"));
			if(oracleList.get((lastPageNum*10-10)+i).get("financeApplyDate")!=null){
				Assert.assertTrue(oracleList.get((lastPageNum*10-10)+i).get("financeApplyDate").contains(payrollListFinanceApplyDates.get(i).getText()));
			}else{
				Assert.assertEquals(payrollListFinanceApplyDates.get(i).getText(),"");
			}
			Assert.assertEquals(payrollListPaymentDetailCounts.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("payrollDetailCount"));
			Assert.assertEquals(payrollListPaymentDetailTotalAmounts.get(i).getText().replaceAll(",", ""), oracleList.get((lastPageNum*10-10)+i).get("paymentDetailTotalAmount"));
			Assert.assertEquals(payrollListPkCredits.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("pkCredit"));
			Assert.assertEquals(payrollListCreditStates.get(i).getText(), CreditStateEnum.valueOfCode(oracleList.get((lastPageNum*10-10)+i).get("creditState")).getName());
	
		}
		//断言数据库查询总笔数和金额与页面左下角一致
		if(oracleList.size()!=0){
			Assert.assertEquals(getPayrollTotalAmount(), OracleDataFactory.getPayrollCreditFinancingAmount(corpNameCore,corpName,pkCredit,partnerName,
					financeApplyDateBegin,financeApplyDateEnd,creditState));
			Assert.assertEquals(getPayrollTotalCount(), OracleDataFactory.getPayrollCreditFinancingCount(corpNameCore,corpName,pkCredit,partnerName,
					financeApplyDateBegin,financeApplyDateEnd,creditState));
		}
		//恢复到第一页
		firstPage.click();
		Thread.sleep(4000);
		for(int i=0;i<payrollListCorpNameCores.size();i++){
			Assert.assertEquals(payrollListCorpNameCores.get(i).getText(), oracleList.get(i).get("corpNameCore"));
			Assert.assertEquals(payrollListCorpNames.get(i).getText(), oracleList.get(i).get("corpName"));
			Assert.assertEquals(payrollListProductTypeCcbscfs.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get(i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(payrollListPartnerNames.get(i).getText(), oracleList.get(i).get("partnerName"));
			if(oracleList.get(i).get("financeApplyDate")!=null){
				Assert.assertTrue(oracleList.get(i).get("financeApplyDate").contains(payrollListFinanceApplyDates.get(i).getText()));
			}else{
				Assert.assertEquals(payrollListFinanceApplyDates.get(i).getText(),"");
			}
			Assert.assertEquals(payrollListPaymentDetailCounts.get(i).getText(), oracleList.get(i).get("payrollDetailCount"));
			Assert.assertEquals(payrollListPaymentDetailTotalAmounts.get(i).getText().replaceAll(",", ""), oracleList.get(i).get("paymentDetailTotalAmount"));
			Assert.assertEquals(payrollListPkCredits.get(i).getText(), oracleList.get(i).get("pkCredit"));
			Assert.assertEquals(payrollListCreditStates.get(i).getText(), CreditStateEnum.valueOfCode(oracleList.get(i).get("creditState")).getName());
		}
	}
	public static void main(String[] args) throws InterruptedException, SQLException {
		TestBase.setupBiz();

	}

}
