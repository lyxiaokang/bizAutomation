package com.jxrt.biz.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.jxrt.test.TestBase;
/*
 * 平台端-交易管理-账款融资管理-账款查询
 */
public class ReceivableSearchPage extends AbstractPage{

	public ReceivableSearchPage(WebDriver driver) {
		super(driver);
	}
	
	//新增中tab页
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '新增中')]")
	public WebElement IssuingTab;
	//新增中tab页中笔数
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '新增中')]/../span[2]")
	public WebElement IssuingCount;
	//已确认tab页
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '已确认')]")
	public WebElement IssuedTab;
	//已确认tab页中笔数
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '已确认')]/../span[2]")
	public WebElement IssuedCount;
	//付款中tab页
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '付款中')]")
	public WebElement RedeemingTab;
	//付款中tab页中笔数
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '付款中')]/../span[2]")
	public WebElement RedeemingCount;
	//付款中tab页
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '已付款')]")
	public WebElement RedeemedTab;
	//付款中tab页中笔数
	@FindBy(xpath="//span[contains(text(), '所处环节：')]/../descendant::span[contains(text(), '已付款')]/../span[2]")
	public WebElement RedeemedCount;
	
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
	public WebElement submitDateBeginInput;
	
	//提交日期结束
	@FindBy(xpath="//span[contains(text(), '提交日期：')]/../descendant::input[2]")
	public WebElement submitDateEndInput;
	
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
	//账款表单Index
	@FindBy(xpath="//table/tbody/tr/td[1]")
	public List<WebElement> receivableListIndexs;
	//账款表单白条号
	@FindBy(xpath="//table/tbody/tr/td[2]")
	public List<WebElement> receivableListPkCredits;
	//账款表单Core
	@FindBy(xpath="//table/tbody/tr/td[3]/descendant::span[1]")
	public List<WebElement> receivableListCores;
	//账款表单LimitSource
	@FindBy(xpath="//table/tbody/tr/td[3]/descendant::span[2]")
	public List<WebElement> receivableListLimitSources;
	//账款表单产品类型
	@FindBy(xpath="//table/tbody/tr/td[4]")
	public List<WebElement> receivableListProductTypes;
	//账款表单供应商名称
	@FindBy(xpath="//table/tbody/tr/td[5]")
	public List<WebElement> receivableListCorpNameAccepts;
	//账款表单商务合同编号
	@FindBy(xpath="//table/tbody/tr/td[6]")
	public List<WebElement> receivableListBusiContractCodes;
	//账款表单应付账款金额
	@FindBy(xpath="//table/tbody/tr/td[7]")
	public List<WebElement> receivableListApplyAmounts;
	//账款表单应付账款到期日
	@FindBy(xpath="//table/tbody/tr/td[8]")
	public List<WebElement> receivableListMaturityDates;
	//账款表单自主加价
	@FindBy(xpath="//table/tbody/tr/td[9]/div/span")
	public List<WebElement> receivablePriceAdds;
	//账款表单提交日期
	@FindBy(xpath="//table/tbody/tr/td[10]/div/span")
	public List<WebElement> receivableListSubmitDates;
	//账款表单摘要
	@FindBy(xpath="//table/tbody/tr/td[11]/div/span")
	public List<WebElement> receivableListAbstract_s;
	//账款表单审核结果
	@FindBy(xpath="//table/tbody/tr/td[12]/div/span")
	public List<WebElement> receivableListApproveResults;
	//操作按钮
	//修改按钮
	@FindBy(xpath="//table/tbody/tr/td[13]/descendant::span[contains(text(), '修改')]")
	public List<WebElement> receivableListModiyBtns;
	//重新上传按钮
	@FindBy(xpath="//table/tbody/tr/td[13]/descendant::span[contains(text(), '重新上传')]")
	public List<WebElement> receivableListReUploadBtns;
	//修改按钮
	@FindBy(xpath="//table/tbody/tr/td[13]/descendant::span[contains(text(), '进度')]")
	public List<WebElement> receivableListWorkflowBtns;
	//修改按钮
	@FindBy(xpath="//table/tbody/tr/td[13]/descendant::span[contains(text(), '查看付款承诺函')]")
	public List<WebElement> receivableListCheckFileBtns;
	
	//账款修改弹出框
	//核心企业名称
	@FindBy(xpath="//form/div[1]/descendant::input")
	public WebElement receivableModifyCorpNameCoreInput;
	//产品类型
	@FindBy(xpath="//form/div[2]/descendant::input")
	public WebElement receivableModifyProductType;
	//额度占用方
	@FindBy(xpath="//form/div[3]/descendant::input")
	public WebElement receivableModifyLimitSource;
	//供应商名称
	@FindBy(xpath="//form/div[4]/descendant::input")
	public WebElement receivableModifyCorpNameInput;
	//商务合同编号
	@FindBy(xpath="//form/div[5]/descendant::input")
	public WebElement receivableModifyBusiContractCodeInput;
	//应付账款金额
	@FindBy(xpath="//form/div[6]/descendant::input")
	public WebElement receivableModifyApplyAmount;
	//应付账款到期日
	@FindBy(xpath="//form/div[7]/descendant::input")
	public WebElement receivableModifyMaturityDateInput;
	//自主定价
	@FindBy(xpath="//form/div[8]/descendant::input")
	public WebElement receivableModifyPriceAddInput;
	//摘要
	@FindBy(xpath="//form/div[9]/descendant::input")
	public WebElement receivableModifyAbstract_Input;
	//账款修改弹出框提交审核按钮
	@FindBy(xpath="/html/body/div[3]/div[2]/div/div/div[3]/div/button[1]/span")
	public WebElement submitBtn;
	//账款修改弹出框关闭按钮
	@FindBy(xpath="/html/body/div[3]/div[2]/div/div/div[3]/div/button[2]/span")
	public WebElement closeBtn;
	
	public static void main(String[] args) throws Exception {

	}
}
