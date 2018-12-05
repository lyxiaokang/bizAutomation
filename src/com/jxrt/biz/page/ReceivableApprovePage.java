package com.jxrt.biz.page;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.jxrt.test.TestBase;
/*
 * 平台端-白条管理-账款融资管理-账款审核
 */
public class ReceivableApprovePage extends AbstractPage{

	public ReceivableApprovePage(WebDriver driver) {
		super(driver);
	}
	
	//核心企业名称输入框
		@FindBy(xpath="//span[contains(text(), '核心企业名称：')]/../descendant::input")
		public WebElement corpNameCoreInput;
		
		//供应商查询输入框
		@FindBy(xpath="//span[contains(text(), '供应商名称：')]/../descendant::input")
		public WebElement corpNameAcceptInput;
		
		//承诺付款日期开始
		@FindBy(xpath="//span[contains(text(), '应付账款到期日：')]/../descendant::input[1]")
		public WebElement maturityDateBeginInput;
		
		//承诺付款日期结束
		@FindBy(xpath="//span[contains(text(), '应付账款到期日：')]/../descendant::input[2]")
		public WebElement maturityDateEndInput;
		
		//提交日期开始
		@FindBy(xpath="//span[contains(text(), '提交日期：')]/../descendant::input[1]")
		public WebElement SubmitDateBeginInput;
		
		//提交日期结束
		@FindBy(xpath="//span[contains(text(), '提交日期：')]/../descendant::input[2]")
		public WebElement SubmitDateEndInput;
		
		//申请金额最小
		@FindBy(xpath="//span[contains(text(), '应付账款金额：')]/../descendant::input[1]")
		public WebElement applyAmountMinInput;
		//申请金额最小
		@FindBy(xpath="//span[contains(text(), '应付账款金额：')]/../descendant::input[2]")
		public WebElement applyAmountMaxInput;
		
		//产品类型
		@FindBy(xpath="//span[contains(text(), '产品类型：')]/../div/descendant::span[1]")
		public WebElement productType;
		//产品类型全部
		@FindBy(xpath="//div/ul/li[contains(text(), '全部')]")
		public WebElement productTypeAll;
		//产品类型e点通
		@FindBy(xpath="//div/ul/li[contains(text(), 'e点通')]")
		public WebElement productTypeEDT;
		//产品类型e铁通
		@FindBy(xpath="//div/ul/li[contains(text(), 'e铁通')]")
		public WebElement productTypeETT;
		//查询按钮
		@FindBy(xpath="//button/span[contains(text(), '查询')]")
		public WebElement searchBtn;
		
		//账款表单
		@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[5]/div/div/div")
		public WebElement receivableList;
		//账款表单checkbox
		@FindBy(xpath="//table/tbody/tr/td[1]/div/label")
		public List<WebElement> receivableListCheckBoxs;
		//账款表单Index
		@FindBy(xpath="//table/tbody/tr/td[2]")
		public List<WebElement> receivableListIndexs;
		//账款表单白条号
		@FindBy(xpath="//table/tbody/tr/td[3]")
		public List<WebElement> receivableListPkCredits;
		//账款表单Core
		@FindBy(xpath="//table/tbody/tr/td[4]/descendant::span[1]")
		public List<WebElement> receivableListCores;
		//账款表单LimitSource
		@FindBy(xpath="//table/tbody/tr/td[4]/descendant::span[2]")
		public List<WebElement> receivableListLimitSources;
		//账款表单产品类型
		@FindBy(xpath="//table/tbody/tr/td[5]")
		public List<WebElement> receivableListProductTypes;
		//账款表单供应商名称
		@FindBy(xpath="//table/tbody/tr/td[6]")
		public List<WebElement> receivableListCorpNameAccepts;
		//账款表单商务合同编号
		@FindBy(xpath="//table/tbody/tr/td[7]")
		public List<WebElement> receivableListBusiContractCodes;
		//账款表单应付账款金额
		@FindBy(xpath="//table/tbody/tr/td[8]")
		public List<WebElement> receivableListApplyAmounts;
		//账款表单应付账款到期日
		@FindBy(xpath="//table/tbody/tr/td[9]")
		public List<WebElement> receivableListMaturityDates;
		//账款表单自主加价
		@FindBy(xpath="//table/tbody/tr/td[10]/div/span")
		public List<WebElement> receivablePriceAdds;
		//账款表单摘要
		@FindBy(xpath="//table/tbody/tr/td[11]/div/span")
		public List<WebElement> receivableListAbstract_s;
		//账款表单提交日期
		@FindBy(xpath="//table/tbody/tr/td[12]/div/span")
		public List<WebElement> receivableListSubmitDates;
		//账款表单提交人
		@FindBy(xpath="//table/tbody/tr/td[13]/div/span")
		public List<WebElement> receivableListSubmitPersons;
		//账款表单受理人
		@FindBy(xpath="//table/tbody/tr/td[14]/div/span")
		public List<WebElement> receivableListAcceptPersons;
		//账款表单不通过原因
		@FindBy(xpath="//table/tbody/tr/td[15]/div")
		public List<WebElement> receivableListNotPassReasonInputs;
		//账款表单不通过原因核心企业名称有误
		@FindBy(xpath="//table/tbody/tr/td[15]/descendant::li[contains(text(), '核心企业名称有误')]")
		public List<WebElement> receivableListCorpNameCoreWrongs;
		
		//账款表单查看承诺付款函操作按钮
		@FindBy(xpath="//table/tbody/tr/td[16]/div/span")
		public List<WebElement> receivableListCheckFileBtns;
		
		//账款表单下方全选按钮
		@FindBy(xpath="//button/span[contains(text(), '全选')]")
		public WebElement selectAllBtn;
		//账款表单下方审核通过按钮
		@FindBy(xpath="//button/span[contains(text(), '审核通过')]")
		public WebElement approvePassBtn;
		//账款表单下方审核不通过按钮
		@FindBy(xpath="//button/span[contains(text(), '审核不通过')]")
		public WebElement approveNoPassBtn;
		
		/*
		 * 选择第n条记录并审核通过
		 */
		public void approvePass(int n) throws InterruptedException{
			receivableListCheckBoxs.get(n-1).click();
			scrollIntoView(receivableListPkCredits.get(n-1));
			Thread.sleep(1000);
			approvePassBtn.click();
			Thread.sleep(5000);
			//断言
			Assert.assertEquals(InstructionResult.getText(), "审核通过！账款已发送至供应商。");
			InstructionWindowConfirmBtn.click();
		}
		
		/*
		 * 选择第n条记录并审核不通过
		 */
		public void approveNoPass(int n) throws InterruptedException{
			receivableListCheckBoxs.get(n-1).click();

			Thread.sleep(1000);
//			左移，IE无法兼容
//			sendKeysToPage(Keys.RIGHT,15);
			Thread.sleep(1000);
			receivableListNotPassReasonInputs.get(0).click();
			Thread.sleep(1000);
			receivableListCorpNameCoreWrongs.get(0).click();
			scrollIntoView(receivableListPkCredits.get(0));
			approveNoPassBtn.click();
			Thread.sleep(2000);
			//断言
			Assert.assertEquals(InstructionResult.getText(), "审核不通过，已退回至经办。");
			InstructionWindowConfirmBtn.click();
		}
		public static void main(String[] args) throws Exception {
//			TestBase.setupBiz();
//			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,TestBase.operateOperatorPasswordTeam2);
//			TestBase.biz.homePage().gotoReceivableApprovePage();
//			String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
//			String corpNameAccept=TestBase.corpNameReceivableTeam2;
//			TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
//			TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
//			TestBase.biz.receivableApprovePage().searchBtn.click();
//			Thread.sleep(2000);
//			TestBase.biz.receivableApprovePage().receivableListNotPassReasonInputs.get(0).click();
//			Thread.sleep(1000);
//			TestBase.biz.receivableApprovePage().receivableListCorpNameCoreWrong.click();
//			TestBase.biz.receivableApprovePage().receivableListNotPassReasonInputs.get(0).click();
//			Thread.sleep(1000);
////			TestBase.biz.receivableApprovePage().scrollIntoView(TestBase.biz.receivableApprovePage().receivableListPkCredits.get(0));
//			TestBase.biz.receivableApprovePage().approveNoPassBtn.click();	
		
			
//	        System.setProperty("webdriver.ie.driver", ".\\Tools\\IEDriverServer.exe");  
	          
	        
			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
	        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	        dc.setCapability("ignoreProtectedModeSettings", true);
//	        把加载关闭配置加载到IE浏览器

			//初始化一个IE浏览器实例，实例名称叫driver  
	        WebDriver driver = new  InternetExplorerDriver(dc); 
	        //最大化窗口  
	        driver.manage().window().maximize();  
	        //设置隐性等待时间  
	        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);  
	          
	        // get()打开一个站点  
	        driver.get("https://www.baidu.com");  
	        //getTitle()获取当前页面title的值  
	        System.out.println("当前打开页面的标题是： "+ driver.getTitle());  
	          
	        //关闭并退出浏览器  
	        driver.quit();  
	          
		}
}
