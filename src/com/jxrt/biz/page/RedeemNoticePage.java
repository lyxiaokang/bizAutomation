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
	//平台产品(选择产品后)
	@FindBy(xpath="//span[contains(text(), '平台产品：')]/../div/descendant::span[2]")
	public WebElement productTypeAfterChosed;
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
	public List<WebElement> redeemListRedeemDates;
	//付款白已付款金额
	@FindBy(xpath="//table/tbody/tr/td[10]/div")
	public List<WebElement> redeemListRedeemedAmounts;
	//付款白条待付款金额
	@FindBy(xpath="//table/tbody/tr/td[11]/div")
	public List<WebElement> redeemListRedeemAmounts;
	//付款白条白条状态
	@FindBy(xpath="//table/tbody/tr/td[12]/div")
	public List<WebElement> redeemListCreditStates;

	//页码
	@FindBy(xpath="//ul[@class='mb120 ivu-page mini']/li[@class='ivu-page-item' or @class='ivu-page-item ivu-page-item-active']/a")
	public List<WebElement> pageNums;
	//活动页码
	@FindBy(xpath="//ul[@class='mb120 ivu-page mini']/li[@class='ivu-page-item ivu-page-item-active']/a")
	public WebElement activePageNum;
	//上一页
	@FindBy(xpath="//ul[@class='mb120 ivu-page mini']/li[contains(@class,'ivu-page-prev')]")
	public WebElement prevPageNum;
	//下一页
	@FindBy(xpath="//ul[@class='mb120 ivu-page mini']/li[contains(@class,'ivu-page-next')]")
	public WebElement nextPageNum;	
	//第一页
	@FindBy(xpath="//ul[@class='mb120 ivu-page mini']/li[@class='ivu-page-item' or @class='ivu-page-item ivu-page-item-active']/a[text()='1']")
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
	
	public void redeemNoticeCreditNum(String corpNameCore,String corpName,
			String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws InterruptedException, SQLException{
		//确认付款通知书页面白条总数与元素
		WebElement lastPageElement=TestBase.biz.redeemNoticePage().getLastPageElement();
		int lastPageNum=TestBase.biz.redeemNoticePage().getLastPageNum();
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);
		int lastPageCreditNum=TestBase.biz.redeemNoticePage().redeemListIndexs.size();
		int CreditCount=lastPageNum*10+lastPageCreditNum-10;
		System.out.println(CreditCount);

		//通过数据库查询付款列表中白条总数
		ArrayList<String> creditList=new ArrayList<String>();
		creditList.add("ISD");
		creditList.add("RD0");
		creditList.add("RD1");
		creditList.add("RD9");
		int oracleCount=OracleDataFactory.countCredit(creditList,corpNameCore,corpName,productTypeCcbscf,reddemDateBegin, reddemDateEnd);
		System.out.println(oracleCount);
		
		Assert.assertEquals(CreditCount, oracleCount);
		//恢复到第一页
		TestBase.biz.redeemNoticePage().firstPage.click();
	}
	
	public void redeemNoticeCreditList(String corpNameCore,String corpName,
			String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws InterruptedException, SQLException{
		//确认付款通知书页面白条总数与元素
		WebElement lastPageElement=TestBase.biz.redeemNoticePage().getLastPageElement();
		int lastPageNum=TestBase.biz.redeemNoticePage().getLastPageNum();
		
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);
		int lastPageCreditNum=TestBase.biz.redeemNoticePage().redeemListIndexs.size();

		//通过数据库查询付款列表中白条总数
		ArrayList<String> creditList=new ArrayList<String>();
		creditList.add("ISD");
		creditList.add("RD0");
		creditList.add("RD1");
		creditList.add("RD9");
		List<Map<String, String>> oracleList=OracleDataFactory.listRedeemCredit(creditList,corpNameCore,corpName,productTypeCcbscf,reddemDateBegin, reddemDateEnd);
		for(int i=0;i<redeemListCorpNameCores.size();i++){
			Assert.assertEquals(redeemListPkCredits.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("pkCredit"));
			Assert.assertEquals(redeemListCorpNameCores.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpNameCore"));
			Assert.assertEquals(redeemListCorpNames.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("corpName"));
			Assert.assertEquals(redeemListProductTypes.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get((lastPageNum*10-10)+i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(redeemListIssueDates.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("issueTime"));
			Assert.assertEquals(redeemListMaturityAmounts.get(i).getText().replaceAll(",", ""), oracleList.get((lastPageNum*10-10)+i).get("maturityAmount"));
			Assert.assertEquals(redeemListRedeemDates.get(i).getText(), oracleList.get((lastPageNum*10-10)+i).get("redeemDate"));
			Assert.assertEquals(redeemListRedeemedAmounts.get(i).getText().replaceAll(",", ""), oracleList.get((lastPageNum*10-10)+i).get("redeemedAmount"));
			Assert.assertEquals(redeemListRedeemAmounts.get(i).getText().replaceAll(",", ""), oracleList.get((lastPageNum*10-10)+i).get("redeemAmount"));
			Assert.assertEquals(redeemListCreditStates.get(i).getText(), CreditStateEnum.valueOfCode(oracleList.get((lastPageNum*10-10)+i).get("creditState")).getName());
			
		}
		//恢复到第一页
		
		TestBase.biz.redeemNoticePage().firstPage.click();
		Thread.sleep(4000);
		for(int i=0;i<redeemListCorpNameCores.size();i++){
			Assert.assertEquals(redeemListPkCredits.get(i).getText(), oracleList.get(i).get("pkCredit"));
			Assert.assertEquals(redeemListCorpNameCores.get(i).getText(), oracleList.get(i).get("corpNameCore"));
			Assert.assertEquals(redeemListCorpNames.get(i).getText(), oracleList.get(i).get("corpName"));
			Assert.assertEquals(redeemListProductTypes.get(i).getText(), ProductTypeCcbscfEnum.valueOfCode(oracleList.get(i).get("productTypeCcbscf")).getName());
			Assert.assertEquals(redeemListIssueDates.get(i).getText(), oracleList.get(i).get("issueTime"));
			Assert.assertEquals(redeemListMaturityAmounts.get(i).getText().replaceAll(",", ""), oracleList.get(i).get("maturityAmount"));
			Assert.assertEquals(redeemListRedeemDates.get(i).getText(), oracleList.get(i).get("redeemDate"));
			Assert.assertEquals(redeemListRedeemedAmounts.get(i).getText().replaceAll(",", ""), oracleList.get(i).get("redeemedAmount"));
			Assert.assertEquals(redeemListRedeemAmounts.get(i).getText().replaceAll(",", ""), oracleList.get(i).get("redeemAmount"));
			Assert.assertEquals(redeemListCreditStates.get(i).getText(), CreditStateEnum.valueOfCode(oracleList.get(i).get("creditState")).getName());
			
		}
	}
	public static void main(String[] args) throws InterruptedException, SQLException {
		
		System.out.println(ProductTypeCcbscfEnum.valueOfName("CREDIT"));

	}

}
