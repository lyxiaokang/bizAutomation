package com.jxrt.biz.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.jxrt.test.TestBase;

/*
 * 平台端-交易管理-账款融资管理-账款新增
 */
public class ReceivableIssuePage extends AbstractPage{

	public ReceivableIssuePage(WebDriver driver) {
		super(driver);
	}
	
	//核心企业显示框
	@FindBy(xpath="//label[contains(text(), '核心企业名称：')]/../descendant::input")
	public WebElement corpNameApplyInput;
	//核心企业查询按钮
	@FindBy(xpath="//label[contains(text(), '核心企业名称：')]/../descendant::button")
	public WebElement corpNameApplySearchBtn;
	//核心企业弹出框
	//核心企业输入框
	@FindBy(xpath="//span[contains(text(), '核心企业：')]/../descendant::input")
	public WebElement corpNameCoreInput;
	//核心企业查询按钮
	@FindBy(xpath="//span[contains(text(), '核心企业：')]/../descendant::button")
	public WebElement corpNameCoreSearchBtn;
	//核心企业列表选择按钮
	@FindBy(xpath="//button/span[contains(text(), '选择')]")
	public List<WebElement> corpNameCoreSelectBtns;
	/*
	 * 查找并选择对应核心企业
	 */
	public void searchAndSelectCorpNameCore(String corpNameCore) throws InterruptedException{
		if(corpNameCoreSelectBtns.get(0).isDisplayed())
		{
			corpNameCoreInput.sendKeys(corpNameCore);
			corpNameCoreSearchBtn.click();
			Thread.sleep(2000);
			corpNameCoreSelectBtns.get(0).click();
		}
	}
	//供应商查询输入框
	@FindBy(xpath="//label[contains(text(), '供应商名称：')]/../descendant::input")
	public WebElement corpNameAcceptInput;	
	//供应商查询按钮
	@FindBy(xpath="//label[contains(text(), '供应商名称：')]/../descendant::button")
	public WebElement corpNameAcceptSearchBtn;
	
	//供应商企业弹出框
	//供应商企业输入框
	@FindBy(xpath="//span[contains(text(), '供应商名称：')]/../descendant::input")
	public WebElement corpNameInput;
	//核心企业查询按钮
	@FindBy(xpath="//span[contains(text(), '供应商名称：')]/../descendant::button")
	public WebElement corpNameSearchBtn;
	//核心企业列表选择按钮
	@FindBy(xpath="//td[6]/div/div/button/span")
	public WebElement corpNameSelectBtn;
	/*
	 * 查找并选择对应企业
	 */
	public void searchAndSelectCorpName(String corpName) throws InterruptedException{
		corpNameInput.sendKeys(corpName);
		corpNameSearchBtn.click();
		Thread.sleep(2000);
		corpNameSelectBtn.click();
	}
	
	//商务合同编号
	@FindBy(xpath="//label[contains(text(), '商务合同编号：')]/../descendant::input")
	public WebElement busiContractCodeInput;
	//申请金额
	@FindBy(xpath="//label[contains(text(), '应付账款金额：')]/../descendant::input")
	public WebElement applyAmountInput;
	//承诺付款日期
	@FindBy(xpath="//label[contains(text(), '应付账款到期日：')]/../descendant::input")
	public WebElement maturityDateInput;
	//摘要
	@FindBy(xpath="//label[contains(text(), '摘要：')]/../descendant::input")
	public WebElement abstractInput;
	
	//单笔新增按钮
	@FindBy(xpath="//button/span[contains(text(), '单笔新增')]")
	public WebElement singleIssueBtn;
	
	//账款表单
	@FindBy(xpath="//table/tbody/tr/td[1]")
	public List<WebElement> receivableLists;
	//账款表单checkbox
	@FindBy(xpath="//table/tbody/tr/td[1]/div/label")
	public List<WebElement> receivableListCheckBoxs;
	//账款表单Index
	@FindBy(xpath="//table/tbody/tr/td[2]/div")
	public List<WebElement> receivableListIndexs;
	//账款表单Core
	@FindBy(xpath="//table/tbody/tr/td[3]/div/p/span[1]")
	public List<WebElement> receivableListCores;
	//账款表单LimitSource
	@FindBy(xpath="//table/tbody/tr/td[3]/div/p/span[2]")
	public List<WebElement> receivableListLimitSources;
	//账款表单产品类型
	@FindBy(xpath="//table/tbody/tr/td[4]")
	public List<WebElement> receivableListProductTypes;
	//账款表单供应商名称
	@FindBy(xpath="//table/tbody/tr/td[5]/div/span")
	public List<WebElement> receivableListCorpNameAccepts;
	//账款表单商务合同编号
	@FindBy(xpath="//table/tbody/tr/td[6]/div/span")
	public List<WebElement> receivableListBusiContractCodes;
	//账款表单应付账款金额
	@FindBy(xpath="//table/tbody/tr/td[7]/div/span")
	public List<WebElement> receivableListApplyAmounts;
	//账款表单应付账款到期日
	@FindBy(xpath="//table/tbody/tr/td[8]/div/span")
	public List<WebElement> receivableListMaturityDates;
	//账款表单摘要
	@FindBy(xpath="//table/tbody/tr/td[10]/div/span")
	public List<WebElement> receivableListAbstract_s;
	//账款表单校验结果通过
	@FindBy(xpath="//table/tbody/tr/td[11]/div/span")
	public List<WebElement> receivableListVerifyPasss;
	//账款表单校验结果不通过时
	@FindBy(xpath="//table/tbody/tr/td[11]/div/i")
	public List<WebElement> receivableListVerifyFailValues;
	//账款表单修改按钮
	@FindBy(xpath="//table/tbody/tr/td[12]/descendant::span[contains(text(), '修改')]")
	public List<WebElement> receivableListModifyBtns;
	//账款表单上传按钮
	@FindBy(xpath="//table/tbody/tr/td[12]/descendant::span[contains(text(), '上传')]")
	public List<WebElement> receivableListUploadBtns;
	
	//账款表单下方提交审核按钮
	@FindBy(xpath="//button/span[contains(text(), '提交审核')]")
	public WebElement submitBtn;
	
	/*
	 * 单笔新增账款
	 */
	public void ReceivableIssue(String corpNameCore,String corpNameAccept,
			String busiContractCode,String applyAmount,String maturityDate,
			String abstract_) throws InterruptedException{
		corpNameApplySearchBtn.click();
		searchAndSelectCorpNameCore(corpNameCore);
		Thread.sleep(1000);
		corpNameAcceptSearchBtn.click();
		searchAndSelectCorpName(corpNameAccept);
		Thread.sleep(1000);
		busiContractCodeInput.sendKeys(busiContractCode);
		applyAmountInput.sendKeys(applyAmount);
		maturityDateInput.sendKeys(maturityDate);
		abstractInput.sendKeys(abstract_);
		singleIssueBtn.click();
		
		Thread.sleep(3000);
		//断言
		Assert.assertEquals(receivableListCores.get(0).getText(), corpNameCore);
		Assert.assertEquals(receivableListLimitSources.get(0).getText(), corpNameCore);
		Assert.assertEquals(receivableListProductTypes.get(0).getText(), "e点通");
		Assert.assertEquals(receivableListCorpNameAccepts.get(0).getText(), corpNameAccept);
		Assert.assertEquals(receivableListBusiContractCodes.get(0).getText(), busiContractCode);
		Assert.assertEquals(receivableListApplyAmounts.get(0).getText(), applyAmount+".00");
		Assert.assertEquals(receivableListMaturityDates.get(0).getText(), maturityDate);
		Assert.assertEquals(receivableListAbstract_s.get(0).getText(), abstract_);
//		Assert.assertNotNull(receivableListVerifyPasss.get(0));
	}
	
}
