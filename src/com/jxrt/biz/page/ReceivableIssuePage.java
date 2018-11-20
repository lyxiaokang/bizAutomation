package com.jxrt.biz.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
	//供应商查询按钮
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
	@FindBy(xpath="//tbody/tr[@class_name='ivu-table-row'][1]")
	public WebElement receivableList;
	//账款表单checkbox
	@FindBy(xpath="//table/tbody/tr[1]/td[1]/div/label")
	public WebElement receivableListCheckBox;
	//账款表单Index
	@FindBy(xpath="//table/tbody/tr[1]/td[2]/div")
	public WebElement receivableListIndex;
	//账款表单Core
	@FindBy(xpath="//tbody/tr[1]/td[3]/div/p/span[1]")
	public WebElement receivableListCore;
	//账款表单LimitSource
	@FindBy(xpath="//tbody/tr[1]/td[3]/div/p/span[2]")
	public WebElement receivableListLimitSource;
	//账款表单产品类型
	@FindBy(xpath="//table/tbody/tr[1]/td[4]/div/span")
	public WebElement receivableListProductType;
	//账款表单供应商名称
	@FindBy(xpath="//table/tbody/tr[1]/td[5]/div/span")
	public WebElement receivableListCorpNameAccept;
	//账款表单商务合同编号
	@FindBy(xpath="//table/tbody/tr[1]/td[6]/div/span")
	public WebElement receivableListBusiContractCode;
	//账款表单应付账款金额
	@FindBy(xpath="//table/tbody/tr[1]/td[7]/div/span")
	public WebElement receivableListApplyAmount;
	//账款表单应付账款到期日
	@FindBy(xpath="//table/tbody/tr[1]/td[8]/div/span")
	public WebElement receivableListMaturityDate;
	//账款表单摘要
	@FindBy(xpath="//table/tbody/tr[1]/td[10]/div/span")
	public WebElement receivableListAbstract_;
	//账款表单校验结果通过
	@FindBy(xpath="//tbody/tr[1]/td[11]/div/span")
	public WebElement receivableListVerifyPass;
	//账款表单校验结果不通过时
	@FindBy(xpath="//tbody/tr[1]/td[11]/div/i")
	public WebElement receivableListVerifyFailValue;
	//账款表单修改按钮
	@FindBy(xpath="//tbody/tr[1]/td[12]/div/div/button/span")
	public WebElement receivableListModifyBtn;
	//账款表单上传按钮
	@FindBy(xpath="//tbody/tr[1]/td[12]/div/div/div/div/button/span")
	public WebElement receivableListUploadBtn;
	
	
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
	}
	public static void main(String[] args) throws InterruptedException{

	}
}
