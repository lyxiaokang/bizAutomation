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
	//生成通知书按钮
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
	//付款白条已付款金额
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
	public WebElement prevPage;
	//下一页
	@FindBy(xpath="//ul[@class='mb120 ivu-page mini']/li[contains(@class,'ivu-page-next')]")
	public WebElement nextPage;	
	//第一页
	@FindBy(xpath="//ul[@class='mb120 ivu-page mini']/li[@class='ivu-page-item' or @class='ivu-page-item ivu-page-item-active']/a[text()='1']")
	public WebElement firstPage;	
	
	//选择收款账号弹出框
	//核心企业名称
	@FindBy(xpath="//label[contains(text(), '核心企业名称：')]/../descendant::span")
	public WebElement selectAcountWindowCorpNameCore;
	//应付款总额
	@FindBy(xpath="//label[contains(text(), '应付款总额：')]/../descendant::span")
	public WebElement selectAcountWindowRedeemAmount;
	//通知书编号
	@FindBy(xpath="//label[contains(text(), '通知书编号：')]/../descendant::input")
	public WebElement selectAcountWindowNoticeNum;
	
	//1208收款账号
	@FindBy(xpath="//label[contains(@class, 'ivu-radio-wrapper ivu-radio-group-item')]/descendant::span[text()='1208收款账户']")
	public WebElement selectAcountWindow1208Acount;
	
	//026收款账号
	@FindBy(xpath="//label[contains(@class, 'ivu-radio-wrapper ivu-radio-group-item')]/descendant::span[text()='链条企业026回款专户']")
	public WebElement selectAcountWindow026Acount;
	
	//确认按钮
	@FindBy(xpath="//button/span[contains(text(), '确认')]")
	public WebElement selectAcountWindowConfirmBtn;
	
	//查看附件按钮
	@FindBy(xpath="//button/span[contains(text(), '查看')]")
	public WebElement checkBtn;
	//发送邮件按钮
	@FindBy(xpath="//button/span[contains(text(), '发送邮件')]")
	public WebElement sendEmailBtn;
	//提示框结果
	@FindBy(xpath="/html/body/div[6]/div[2]/div/div/div/div/div[2]/div[2]")
	public WebElement InstructionResult;
	//提示框确认按钮
	@FindBy(xpath="/html/body/div[6]/div[2]/div/div/div/div/div[3]/button/span")
	public WebElement InstructionWindowConfirmBtn;
	
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
	 * 获取某一张条的序号
	 */
	public int getIndexByPkCreditOnePage(String pkCredit,int pageSize){
		for(int i=0;i<pageSize;i++){
			if(redeemListPkCredits.get(i).getText().equals(pkCredit)){
				return i;
			}
		}
//		TestBase.biz.redeemNoticePage()
		return -1;
	}
	
	public void redeemNoticeCreditNum(String corpNameCore,String corpName,
			String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws InterruptedException, SQLException{
		//确认付款通知书页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);
		int lastPageCreditNum=redeemListIndexs.size();
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
		firstPage.click();
	}
	
	public void redeemNoticeCreditList(String corpNameCore,String corpName,
			String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws InterruptedException, SQLException{
		//确认付款通知书页面白条总数与元素
		WebElement lastPageElement=getLastPageElement();
		int lastPageNum=getLastPageNum();
		
		//翻到最后一页，获取该页条数
		lastPageElement.click();
		Thread.sleep(4000);

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
//			Assert.assertEquals(redeemListRedeemAmounts.get(i).getText().replaceAll(",", ""), oracleList.get((lastPageNum*10-10)+i).get("redeemAmount"));
//			TODO(自持条部分需要去除)
			Assert.assertEquals(redeemListCreditStates.get(i).getText(), CreditStateEnum.valueOfCode(oracleList.get((lastPageNum*10-10)+i).get("creditState")).getName());
			
		}
		//恢复到第一页
		
		firstPage.click();
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
//			Assert.assertEquals(redeemListRedeemAmounts.get(i).getText().replaceAll(",", ""), oracleList.get(i).get("redeemAmount"));
//			TODO(自持条部分需要去除)
			Assert.assertEquals(redeemListCreditStates.get(i).getText(), CreditStateEnum.valueOfCode(oracleList.get(i).get("creditState")).getName());
			
		}
	}
	public static void main(String[] args) throws InterruptedException, SQLException {
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
		TestBase.biz.homePage().gotoRedeemNoticePage();
		Thread.sleep(4000);
		//搜索一条账款企业
		TestBase.biz.redeemNoticePage().corpNameCoreInput.sendKeys(TestBase.corpNameCoreReceivableTeam2);
		TestBase.biz.redeemNoticePage().corpNameInput.sendKeys(TestBase.corpNameReceivableTeam2);
		TestBase.biz.redeemNoticePage().productType.click();
		Thread.sleep(1000);
		TestBase.biz.redeemNoticePage().productTypeReceivable.click();
		TestBase.biz.redeemNoticePage().searchBtn.click();
		Thread.sleep(4000);
		TestBase.biz.redeemNoticePage().redeemListCheckBoxs.get(0).click();
		TestBase.biz.redeemNoticePage().generateNoticeBtn.click();
		Thread.sleep(2000);
		
		//通过数据库查询付款列表中白条总数
		ArrayList<String> creditList=new ArrayList<String>();
		creditList.add("ISD");
		creditList.add("RD0");
		creditList.add("RD1");
		creditList.add("RD9");
		List<Map<String, String>> oracleList=OracleDataFactory.listRedeemCredit(creditList,TestBase.corpNameCoreReceivableTeam2,TestBase.corpNameReceivableTeam2,"RECEIVABLE",null, null);

		Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowCorpNameCore.getText(), TestBase.corpNameCoreReceivableTeam2);
		Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowRedeemAmount.getText().replaceAll(",", ""), oracleList.get(0).get("redeemAmount"));
		TestBase.biz.redeemNoticePage().selectAcountWindowConfirmBtn.click();
		Thread.sleep(2000);
		TestBase.biz.redeemNoticePage().sendEmailBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(TestBase.biz.redeemNoticePage().InstructionResult.getText(),"邮件已发送，请查收");
		TestBase.biz.redeemNoticePage().InstructionWindowConfirmBtn.click();
		
	}

}
