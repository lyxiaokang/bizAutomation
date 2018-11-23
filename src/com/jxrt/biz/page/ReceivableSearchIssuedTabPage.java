package com.jxrt.biz.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReceivableSearchIssuedTabPage extends AbstractPage{

	public ReceivableSearchIssuedTabPage(WebDriver driver) {
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
	
	//确认日期开始
	@FindBy(xpath="//span[contains(text(), '确认日期：')]/../descendant::input[1]")
	public WebElement approveDateBeginInput;
	
	//提交日期结束
	@FindBy(xpath="//span[contains(text(), '提交日期：')]/../descendant::input[2]")
	public WebElement approveDateEndInput;
	
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
	//账款表单确认日期
	@FindBy(xpath="//table/tbody/tr/td[10]/div/span[1]")
	public List<WebElement> receivableListIssuedDates;
	//账款表单摘要
	@FindBy(xpath="//table/tbody/tr/td[10]/div/span[2]")
	public List<WebElement> receivableListAbstract_s;
}
