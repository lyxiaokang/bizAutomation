package com.jxrt.biz.page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.jxrt.test.TestBase;
import com.jxrt.util.ExcelUtil;

import jxl.write.Label;

public class FinanceDataApprovePage extends AbstractPage{
	/*
	 * 平台端-白条融资管理-融资资料审核
	 */
	public FinanceDataApprovePage(WebDriver driver) {
		super(driver);
	}
	
	//融资企业输入框
	@FindBy(xpath="//span[contains(text(), '融资企业：')]/../descendant::input")
	public WebElement financeCorpNameInput;
	
	//核心企业输入框
	@FindBy(xpath="//span[contains(text(), '核心企业：')]/../descendant::input")
	public WebElement corpNameCoreInput;
	
	//资金方输入框
	@FindBy(xpath="//span[contains(text(), '资金方：')]/../descendant::input")
	public WebElement financeNameInput;
	//资金方选择
	@FindBy(xpath="//span[contains(text(), '资金方：')]/../descendant::ul[2]/li")
	public WebElement financeNameChose;
	
	//平台产品输入框
	@FindBy(xpath="//span[contains(text(), '平台产品：')]/../descendant::input")
	public WebElement ccbscfProductInput;
	//平台产品选择
	@FindBy(xpath="//span[contains(text(), '平台产品：')]/../descendant::ul[2]/li")
	public WebElement ccbscfProductChose;
	//提交日期输入框开始
	@FindBy(xpath="//span[contains(text(), '提交日期：')]/../descendant::input[1]")
	public WebElement submitDateBeginInput;
	//提交日期输入框结束
	@FindBy(xpath="//span[contains(text(), '提交日期：')]/../descendant::input[2]")
	public WebElement submitDateEndInput;
	//客服姓名输入框
	@FindBy(xpath="//span[contains(text(), '客服姓名：')]/../descendant::input")
	public WebElement queryNameInput;
	//审核进度输入框
	@FindBy(xpath="//span[contains(text(), '审核进度：')]/../div/div")
	public WebElement workFlowInput;
	//待审核
	@FindBy(xpath="//div/ul/li[text()='待审核']")
	public WebElement workFlowUN_AUDIT;
	//初审审核中
	@FindBy(xpath="//div/ul/li[text()='初审审核中']")
	public WebElement workFlowFIRST_AUDITING;
	//初审审核完成
	@FindBy(xpath="//div/ul/li[text()='初审审核完成']")
	public WebElement workFlowFIRST_AUDITED;
	//复审审核中
	@FindBy(xpath="//div/ul/li[text()='复审审核中']")
	public WebElement workFlowFINAL_AUDITING;
	//复审审核完成
	@FindBy(xpath="//div/ul/li[text()='复审审核完成']")
	public WebElement workFlowFINAL_AUDITED;
	//失效
	@FindBy(xpath="//div/ul/li[text()='失效']")
	public WebElement workFlowINVALID;
	
	//审核结果输入框
	@FindBy(xpath="//span[contains(text(), '审核结果：')]/../descendant::input")
	public WebElement approveResultInput;
	//审核通过
	@FindBy(xpath="//div/ul/li[text()='审核通过']")
	public WebElement approveResultPass;
	//审核不通过
	@FindBy(xpath="//div/ul/li[text()='审核不通过']")
	public WebElement approveResultNoPass;
	
	//初审审核员姓名输入框
	@FindBy(xpath="//span[contains(text(), '初审审核员姓名：')]/../descendant::input")
	public WebElement operatorNameInput;

	//复审审核员姓名输入框
	@FindBy(xpath="//span[contains(text(), '复审审核员姓名：')]/../descendant::input")
	public WebElement managerNameInput;
	
	//新增按钮
	@FindBy(xpath="//button/span[contains(text(), '新增')]")
	public WebElement addBtn;
	//查询按钮
	@FindBy(xpath="//button/span[contains(text(), '查询')]")
	public WebElement searchBtn;
	//新增弹出框
	//融资企业输入框
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '融资企业：')]/../descendant::input")
	public WebElement addFinanceCorpNameInput;
	//融资企业查询按钮
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '融资企业：')]/../descendant::button")
	public WebElement addFinanceCorpNameSearchBtn;
	//融资企业弹出框
	//融资企业输入框
	@FindBy(xpath="//div[contains(text(), '已实名认证企业查询')]/../../descendant::span[contains(text(), '企业名称：')]/../descendant::input")
	public WebElement windowFinanceCorpNameInput;
	//融资企业查询按钮
	@FindBy(xpath="//div[contains(text(), '已实名认证企业查询')]/../../descendant::span[contains(text(), '企业名称：')]/../descendant::button")
	public WebElement windowFinanceCorpNameSearch;
	//融资企业列表选择按钮
	@FindBy(xpath="//button/span[contains(text(), '选择')]")
	public List<WebElement> windowFinanceCorpNameSelectBtns;
	
	//核心企业输入框
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '核心企业：')]/../descendant::input")
	public WebElement addCorpNameCoreInput;
	//核心企业查询按钮
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '核心企业：')]/../descendant::button")
	public WebElement addCorpNameCoreSearchBtn;
	//核心企业弹出框
	//核心企业输入框
	@FindBy(xpath="//div[text()='企业查询']/../../descendant::span[contains(text(), '企业名称：')]/../descendant::input")
	public WebElement windowCorpNameCoreInput;
	//核心企业查询按钮
	@FindBy(xpath="//div[text()='企业查询']/../../descendant::span[contains(text(), '企业名称：')]/../descendant::button")
	public WebElement windowCorpNameCoreSearch;
	//核心企业列表选择按钮
	@FindBy(xpath="//div[text()='企业查询']/../../descendant::button/span[contains(text(), '选择')]")
	public List<WebElement> windowCorpNameCoreSelectBtns;
	
	//资金方
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '资金方：')]/../descendant::span")
	public WebElement addFinanceNameInput;
	//资金方选择
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '资金方：')]/../descendant::ul[2]/li[1]")
	public WebElement addFinanceNameChose;
	
	//平台产品
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '平台产品：')]/../descendant::span")
	public WebElement addCcbscfProductInput;
	//平台产品选择融信
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '平台产品：')]/../descendant::ul[2]/li[1]")
	public WebElement addCreditChose;
	//平台产品选择账款
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '平台产品：')]/../descendant::ul[2]/li[2]")
	public WebElement addReceivableChose;
	//融资金额
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '融资金额：')]/../descendant::input")
	public WebElement addFinanceAmountInput;
	//摘要
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '摘要：')]/../descendant::input")
	public WebElement addAbstractInput;
	//是否加急-是
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '是否加急：')]/../descendant::label[contains(text(), '是') and @class='ivu-radio-wrapper ivu-radio-group-item']")
	public WebElement addIsUrgent;
	//是否加急-否
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '是否加急：')]/../descendant::label[contains(text(), '否') and @class='ivu-radio-wrapper ivu-radio-group-item ivu-radio-wrapper-checked']")
	public WebElement addIsNotUrgent;
	//融资企业是否国企-是
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '融资企业是否国企：')]/../descendant::label[contains(text(), '是') and @class='ivu-radio-wrapper ivu-radio-group-item']")
	public WebElement addIsStateOwned;
	//融资企业是否国企-否
	@FindBy(xpath="//div[contains(text(), '审核记录新增')]/../../descendant::label[contains(text(), '融资企业是否国企：')]/../descendant::label[contains(text(), '否') and @class='ivu-radio-wrapper ivu-radio-group-item ivu-radio-wrapper-checked']")
	public WebElement addIsNotStateOwned;
	//新增按钮
	@FindBy(xpath="//button/span[contains(text(), '下一步')]")
	public WebElement addNextStepBtn;
	//提交按钮
	@FindBy(xpath="//button/span[contains(text(), '提交')]")
	public WebElement addSubmitBtn;
	//保存按钮（修改时）
	@FindBy(xpath="//button/span[contains(text(), '保存')]")
	public WebElement saveBtn;
	//关闭按钮
	@FindBy(xpath="//button/span[contains(text(), '关闭')]")
	public WebElement addCloseBtn;
	//上传按钮群
	//上传按钮
	@FindBy(xpath="//span[contains(text(), '公司章程:')]/../descendant::button/span[contains(text(), '上传')]")
	public WebElement uploadGSZCBtn;
	@FindBy(xpath="//span[contains(text(), '个人征信授权书:')]/../descendant::button/span[contains(text(), '上传')]")
	public WebElement uploadGRZXBtn;
	@FindBy(xpath="//span[contains(text(), '关于同意查询和报送信用信息的函:')]/../descendant::button/span[contains(text(), '上传')]")
	public WebElement uploadQYZXBtn;
	@FindBy(xpath="//span[contains(text(), '商务合同:')]/../descendant::button/span[contains(text(), '上传')]")
	public WebElement uploadSWHTBtn;
	@FindBy(xpath="//span[contains(text(), '发票:')]/../descendant::button/span[contains(text(), '上传')]")
	public WebElement uploadFPBtn;
	@FindBy(xpath="//span[contains(text(), '其它1:')]/../descendant::button/span[contains(text(), '上传')]")
	public WebElement uploadQTBtn;
	@FindBy(xpath="//span[contains(text(), '经营异常证明文件:')]/../descendant::button/span[contains(text(), '上传')]")
	public WebElement uploadZMWJBtn;
	
	//重新上传按钮群
	//重新上传按钮
	@FindBy(xpath="//span[contains(text(), '公司章程:')]/../descendant::button/span[contains(text(), '重新上传')]")
	public WebElement reUploadGSZCBtn;
	@FindBy(xpath="//span[contains(text(), '个人征信授权书:')]/../descendant::button/span[contains(text(), '重新上传')]")
	public WebElement reUploadGRZXBtn;
	@FindBy(xpath="//span[contains(text(), '关于同意查询和报送信用信息的函:')]/../descendant::button/span[contains(text(), '重新上传')]")
	public WebElement reUploadQYZXBtn;
	@FindBy(xpath="//span[contains(text(), '商务合同:')]/../descendant::button/span[contains(text(), '重新上传')]")
	public WebElement reUploadSWHTBtn;
	@FindBy(xpath="//span[contains(text(), '发票:')]/../descendant::button/span[contains(text(), '重新上传')]")
	public WebElement reUploadFPBtn;
	@FindBy(xpath="//span[contains(text(), '其它1:')]/../descendant::button/span[contains(text(), '重新上传')]")
	public WebElement reUploadQTBtn;
	@FindBy(xpath="//span[contains(text(), '经营异常证明文件:')]/../descendant::button/span[contains(text(), '重新上传')]")
	public WebElement reUploadZMWJBtn;
	
	//下载按钮群
	//下载按钮
	@FindBy(xpath="//span[contains(text(), '公司章程:')]/../descendant::button/span[contains(text(), '下载')]")
	public WebElement downloadGSZCBtn;
	@FindBy(xpath="//span[contains(text(), '个人征信授权书:')]/../descendant::button/span[contains(text(), '下载')]")
	public WebElement downloadGRZXBtn;
	@FindBy(xpath="//span[contains(text(), '关于同意查询和报送信用信息的函:')]/../descendant::button/span[contains(text(), '下载')]")
	public WebElement downloadQYZXBtn;
	@FindBy(xpath="//span[contains(text(), '商务合同:')]/../descendant::button/span[contains(text(), '下载')]")
	public WebElement downloadSWHTBtn;
	@FindBy(xpath="//span[contains(text(), '发票:')]/../descendant::button/span[contains(text(), '下载')]")
	public WebElement downloadFPBtn;
	@FindBy(xpath="//span[contains(text(), '其它1:')]/../descendant::button/span[contains(text(), '下载')]")
	public WebElement downloadQTBtn;
	@FindBy(xpath="//span[contains(text(), '经营异常证明文件:')]/../descendant::button/span[contains(text(), '下载')]")
	public WebElement downloadZMWJBtn;
	@FindBy(xpath="/html/body/div[25]/div[2]/div/div/div/div/div[2]/div[2]")
	public WebElement message;
	@FindBy(xpath="/html/body/div[25]/div[2]/div/div/div/div/div[3]/button/span")
	public WebElement messageConfirmBtn;
	
	//页码
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/ul[@class='ivu-page mini']/li[@class='ivu-page-item' or @class='ivu-page-item ivu-page-item-active']/a")
	public List<WebElement> pageNums;
	//活动页码
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/ul[@class='ivu-page mini']/li[@class='ivu-page-item ivu-page-item-active']/a")
	public WebElement activePageNum;
	//上一页
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/ul[@class='ivu-page mini']/li[contains(@class,'ivu-page-prev')]")
	public WebElement prevPageNum;
	//下一页
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/ul[@class='ivu-page mini']/li[contains(@class,'ivu-page-next')]")
	public WebElement nextPageNum;
	
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[1]")
	public List<WebElement> dataListDropDownBtns;
	//序号
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[2]")
	public List<WebElement> dataListIndexs;
	//融资企业
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[3]")
	public List<WebElement> dataListFinanceCorpNames;
	//核心企业
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[4]")
	public List<WebElement> dataListCorpNameCores;
	//金融机构
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[5]")
	public List<WebElement> dataListFinanceNames;
	//平台产品
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[6]")
	public List<WebElement> dataListCcbscfProductTypes;
	//提交日期
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[7]")
	public List<WebElement> dataListSubmitDates;
	//摘要
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[8]")
	public List<WebElement> dataListAbstracts;
	//初审姓名
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[9]")
	public List<WebElement> dataListOperatorNames;
	//终审姓名
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[10]")
	public List<WebElement> dataListManagerNames;
	//客服姓名
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[11]")
	public List<WebElement> dataListQueryNames;
	//进度
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[12]")
	public List<WebElement> dataListWorkFlows;
	//审核结果
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[13]")
	public List<WebElement> dataListApproveResults;
	//修改按钮
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[14]//button/span[contains(text(),'修改')]")
	public List<WebElement> dataLisModifyBtns;
	//失效按钮
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[14]//button/span[contains(text(),'失效')]")
	public List<WebElement> dataLisInvalidBtns;
	
	//失效提示框               
	@FindBy(xpath="/html/body/div[24]/div[2]/div/div/div[2]/div/button[1]/span")
	public WebElement invalidConfirmBtn;
	@FindBy(xpath="/html/body/div[24]/div[2]/div/div/div[2]/div/button[2]/span")
	public WebElement invalidCancelBtn;
	
	//初审审核按钮 
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[14]/div/div/button/span[contains(text(),'审核')]")
	public List<WebElement> operatorApproveBtns;
	//初审审核完成按钮
	@FindBy(xpath="/html/body/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div/div[2]/div/div/div[2]/table/tbody/tr/td[14]/div/div/button/span[contains(text(),'初审完成')]")
	public List<WebElement> operatorApproveDoneBtns;
	//复审审核按钮
	@FindBy(xpath="//table/tbody/tr/td[14]/div/div/button/span[contains(text(),'复审')]")
	public List<WebElement> managerApproveBtns;
	
	//认领任务提示框
	@FindBy(xpath="/html/body/div[23]/div[2]/div/div/div[2]")
	public WebElement operatorClaimTaskMessge;
	@FindBy(xpath="/html/body/div[23]/div[2]/div/div/div[3]/div/button[1]/span")
	public WebElement operatorClaimTaskConfirmBtn;
	@FindBy(xpath="/html/body/div[23]/div[2]/div/div/div[3]/div/button[2]/span")
	public WebElement operatorClaimTaskCancelBtn;
	
	//子Tab
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'公司章程')]")
	public WebElement dataListChildGSZC;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'公司章程')]/../../../td[2]")
	public WebElement dataListChildGSZCOpeResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'公司章程')]/../../../td[3]")
	public WebElement dataListChildGSZCOpeReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'公司章程')]/../../../td[4]")
	public WebElement dataListChildGSZCManResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'公司章程')]/../../../td[5]")
	public WebElement dataListChildGSZCManReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'公司章程')]/../../../td[6]//button/span[contains(text(),'审核')]")
	public WebElement dataListChildGSZCApproveBtn;
	//公司章程审核界面
	@FindBy(xpath="//div[contains(text(),'公司章程')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'提交')]")
	public WebElement opeApproveGSZCSubmitBtn;
	@FindBy(xpath="//div[contains(text(),'公司章程')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'关闭')]")
	public WebElement opeApproveGSZCCloseBtn;
	//不通过原因
	@FindBy(xpath="//span[contains(text(),'企业最新章程，且与融资申请企业名称一致')]/../../..//input")
	public WebElement noPassGSZCBtn;
	
	
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'个人征信授权书')]")
	public WebElement dataListChildGRZX;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'个人征信授权书')]/../../../td[2]")
	public WebElement dataListChildGRZXOpeResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'个人征信授权书')]/../../../td[3]")
	public WebElement dataListChildGRZXOpeReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'个人征信授权书')]/../../../td[4]")
	public WebElement dataListChildGRZXManResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'个人征信授权书')]/../../../td[5]")
	public WebElement dataListChildGRZXManReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'个人征信授权书')]/../../../td[6]//button/span[contains(text(),'审核')]")
	public WebElement dataListChildGRZXApproveBtn;
	//不通过原因
	@FindBy(xpath="//span[contains(text(),'法人签字，国企可不提供')]/../../..//input")
	public WebElement noPassGRZXBtn;
	
	//个人征信审核界面
	@FindBy(xpath="//div[contains(text(),'个人征信授权书')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'提交')]")
	public WebElement opeApproveGRZXSubmitBtn;
	@FindBy(xpath="//div[contains(text(),'个人征信授权书')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'关闭')]")
	public WebElement opeApproveGRZXCloseBtn;
	
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'关于同意查询和报送信用信息的函')]")
	public WebElement dataListChildQYZX;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'关于同意查询和报送信用信息的函')]/../../../td[2]")
	public WebElement dataListChildQYZXOpeResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'关于同意查询和报送信用信息的函')]/../../../td[3]")
	public WebElement dataListChildQYZXOpeReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'关于同意查询和报送信用信息的函')]/../../../td[4]")
	public WebElement dataListChildQYZXManResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'关于同意查询和报送信用信息的函')]/../../../td[5]")
	public WebElement dataListChildQYZXManReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'关于同意查询和报送信用信息的函')]/../../../td[6]//button/span[contains(text(),'审核')]")
	public WebElement dataListChildQYZXApproveBtn;
	//不通过原因
	@FindBy(xpath="//span[contains(text(),'法人签字并加盖公章')]/../../..//input")
	public WebElement noPassQYZXBtn;
	
	//企业征信审核界面
	@FindBy(xpath="//div[contains(text(),'关于同意查询和报送信用信息的函')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'提交')]")
	public WebElement opeApproveQYZXSubmitBtn;
	@FindBy(xpath="//div[contains(text(),'关于同意查询和报送信用信息的函')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'关闭')]")
	public WebElement opeApproveQYZXCloseBtn;
	
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'商务合同')]")
	public WebElement dataListChildSWHT;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'商务合同')]/../../../td[2]")
	public WebElement dataListChildSWHTOpeResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'商务合同')]/../../../td[3]")
	public WebElement dataListChildSWHTOpeReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'商务合同')]/../../../td[4]")
	public WebElement dataListChildSWHTManResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'商务合同')]/../../../td[5]")
	public WebElement dataListChildSWHTManReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'商务合同')]/../../../td[6]//button/span[contains(text(),'审核')]")
	public WebElement dataListChildSWHTApproveBtn;

	//商务合同审核界面
	//交易对手名称查询
	@FindBy(xpath="//div[contains(text(),'商务合同')]/../../div[@class='ivu-modal-body']//form//button/span[contains(text(),'查询')]")
	public WebElement opeApproveSWHTCpSearchBtn;
	//交易对手弹出框
	//交易对手输入框
	@FindBy(xpath="/html/body/div[16]/div[2]/div/div/div[2]/div[1]/div/div/input")
	public WebElement corpNameInput;
	//交易对手查询按钮
	@FindBy(xpath="/html/body/div[16]/div[2]/div/div/div[2]/div[1]/div/div/div/button/span")
	public WebElement corpNameSearchBtn;
	//交易对手列表选择按钮
	@FindBy(xpath="/html/body/div[16]/div[2]/div/div/div[2]/div[2]/div/div/div[2]/table/tbody/tr/td[4]/div/div/button/span")
	public WebElement corpNameSelectBtn;
	/*
	 * 查找并选择对应企业
	 */
	public void searchAndSelectCorpName(String corpName) throws InterruptedException{
		Thread.sleep(2000);
		corpNameInput.sendKeys(corpName);
		corpNameSearchBtn.click();
		Thread.sleep(2000);
		corpNameSelectBtn.click();
		Thread.sleep(2000);
	}
	
	
	
	//商务合同编号输入框
	@FindBy(xpath="//div[contains(text(),'商务合同')]/../../div[@class='ivu-modal-body']//form//label[contains(text(),'商务合同编号：')]/..//input")
	public WebElement opeApproveSWHTBusiContractCodeInput;
	
	@FindBy(xpath="/html/body/div[17]/div[2]/div/div/div[2]/div/div[2]/form/div[11]/div/button[1]/span")
	public WebElement opeApproveSWHTSubmitBtn;
	@FindBy(xpath="/html/body/div[17]/div[2]/div/div/div[2]/div/div[2]/form/div[11]/div/button[2]/span")
	public WebElement opeApproveSWHTCloseBtn;
	
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'发票')]")
	public WebElement dataListChildFP;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'发票')]/../../../td[2]")
	public WebElement dataListChildFPOpeResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'发票')]/../../../td[3]")
	public WebElement dataListChildFPOpeReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'发票')]/../../../td[4]")
	public WebElement dataListChildFPManResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'发票')]/../../../td[5]")
	public WebElement dataListChildFPManReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'发票')]/../../../td[6]//button/span[contains(text(),'审核')]")
	public WebElement dataListChildFPApproveBtn;
//	发票
	//导入发票格式化数据按钮
	@FindBy(xpath="/html/body/div[10]/div[2]/div/div/div[2]/div[2]/div[1]/div/div/button")
	public WebElement opeApproveFPImportBtn;
	//批量删除发票按钮
	@FindBy(xpath="/html/body/div[10]/div[2]/div/div/div[2]/div[5]/div[1]/button/span")
	public WebElement opeApproveFPDeleteAllBtn;
	//提交按钮                   
	@FindBy(xpath="/html/body/div[10]/div[2]/div/div/div[3]/div/button[1]/span")
	public WebElement opeApproveFPSubmitBtn;
	@FindBy(xpath="/html/body/div[10]/div[2]/div/div/div[3]/div/button[2]/span")
	public WebElement opeApproveFPCloseBtn;
//	其它
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'其它1')]")
	public WebElement dataListChildQT;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'其它1')]/../../../td[2]")
	public WebElement dataListChildQTOpeResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'其它1')]/../../../td[3]")
	public WebElement dataListChildQTOpeReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'其它1')]/../../../td[4]")
	public WebElement dataListChildQTManResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'其它1')]/../../../td[5]")
	public WebElement dataListChildQTManReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'其它1')]/../../../td[6]//button/span[contains(text(),'审核')]")
	public WebElement dataListChildQTApproveBtn;
	//不通过原因
	@FindBy(xpath="//span[contains(text(),'其它1描述1')]/../../..//input")
	public WebElement noPassQTBtn;
	
	//其它审核界面
	@FindBy(xpath="//div[contains(text(),'其它1')]/../../div[@class='ivu-modal-footer']//button/span[contains(text(),'提交')]")
	public WebElement opeApproveQTSubmitBtn;
	@FindBy(xpath="//div[contains(text(),'其它1')]/../../div[@class='ivu-modal-footer']//button/span[contains(text(),'关闭')]")
	public WebElement opeApproveQTCloseBtn;
	
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'经营异常证明文件')]")
	public WebElement dataListChildZMWJ;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'经营异常证明文件')]/../../../td[2]")
	public WebElement dataListChildZMWJOpeResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'经营异常证明文件')]/../../../td[3]")
	public WebElement dataListChildZMWJOpeReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'经营异常证明文件')]/../../../td[4]")
	public WebElement dataListChildZMWJManResult;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'经营异常证明文件')]/../../../td[5]")
	public WebElement dataListChildZMWJManReason;
	@FindBy(xpath="//table/tbody/tr/td/div/span[contains(text(),'经营异常证明文件')]/../../../td[6]//button/span[contains(text(),'审核')]")
	public WebElement dataListChildZMWJApproveBtn;
	//经营异常证明文件审核界面
	@FindBy(xpath="//span[contains(text(),'工商、人法、失信截图pdf文件：')]/..//button/span[contains(text(),'上传')]")
	public WebElement opeApproveZMWJUploadBtn;
	@FindBy(xpath="//div[contains(text(),'经营异常证明文件')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'提交')]")
	public WebElement opeApproveZMWJSubmitBtn;
	@FindBy(xpath="//div[contains(text(),'经营异常证明文件')]/../../div[@class='ivu-modal-body']//button/span[contains(text(),'关闭')]")
	public WebElement opeApproveZMWJCloseBtn;
	//不通过原因
	@FindBy(xpath="//span[contains(text(),'工商网信息无异常')]/../../..//input")
	public WebElement noPassZMWJBtn;
	
	//复审弹出框
	//公司章程部分
	@FindBy(xpath="//div[contains(text(),'复审审核')]/../..//div[contains(@class,'ivu-tabs-tab') and text()=' 公司章程 ']")
	public WebElement managerApproveGSZCTab;
	//个人征信授权书部分
	@FindBy(xpath="//div[contains(text(),'复审审核')]/../..//div[contains(@class,'ivu-tabs-tab') and text()=' 个人征信授权书 ']")
	public WebElement managerApproveGRZXTab;
	//关于同意查询和报送信用信息的函部分
	@FindBy(xpath="//div[contains(text(),'复审审核')]/../..//div[contains(@class,'ivu-tabs-tab') and text()=' 关于同意查询和报送信用信息的函 ']")
	public WebElement managerApproveQYZXTab;
	//商务合同部分
	@FindBy(xpath="//div[contains(text(),'复审审核')]/../..//div[contains(@class,'ivu-tabs-tab') and text()=' 商务合同 ']")
	public WebElement managerApproveSWHTTab;
	//其它部分
	@FindBy(xpath="//div[contains(text(),'复审审核')]/../..//div[contains(@class,'ivu-tabs-tab') and text()=' 其它1 ']")
	public WebElement managerApproveQTTab;
	//经营异常证明文件部分
	@FindBy(xpath="//div[contains(text(),'复审审核')]/../..//div[contains(@class,'ivu-tabs-tab') and text()=' 经营异常证明文件 ']")
	public WebElement managerApproveZMWJTab;
	
	//退回初审按钮
	@FindBy(xpath="/html/body/div[22]/div[2]/div/div/div[2]/div/div/div/div[2]/div/div/div[2]/div[8]/div//button/span[contains(text(),'退回初审')]")
	public WebElement manApproveBackToOpeBtn;
	//复审提交
	@FindBy(xpath="/html/body/div[22]/div[2]/div/div/div[2]/div/div/div/div[2]/div/div/div[2]/div[7]/div//button/span[contains(text(),'复审提交')]")
	public WebElement manApproveSubtmitBtn;
	public static void writeExcelImportInvoice() throws Exception {
		Random rand=new Random();
		Integer rand8=rand.nextInt(90000000)+10000000;
		List<Label> labelList = new ArrayList<Label>();
		// 添加表头
		labelList.add(new Label(0, 0, "供应商名称"));
		labelList.add(new Label(1, 0, "交易对手名称"));
		labelList.add(new Label(2, 0, "发票类型"));
		labelList.add(new Label(3, 0, "发票代码"));
		labelList.add(new Label(4, 0, "发票号码"));
		labelList.add(new Label(5, 0, "发票日期"));
		labelList.add(new Label(6, 0, "发票金额"));
		labelList.add(new Label(7, 0, "校验码"));
		labelList.add(new Label(8, 0, "价税合计"));
		labelList.add(new Label(9, 0, "外部批次号"));
		// 添加内容
		labelList.add(new Label(0, 1, TestBase.FinanceDataApproveCorpName));
		labelList.add(new Label(1, 1, TestBase.FinanceDataApproveCorpNameCore));
		labelList.add(new Label(2, 1, "增值税专用发票"));
		labelList.add(new Label(3, 1, rand8.toString()+"01"));
		labelList.add(new Label(4, 1, rand8.toString()));
		labelList.add(new Label(5, 1, LocalDate.now().toString()));
		labelList.add(new Label(6, 1, "9999"));
		labelList.add(new Label(7, 1, "12345678901234567890"));
		labelList.add(new Label(8, 1, "9999"));
		labelList.add(new Label(9, 1, "123"));
		ExcelUtil.writeExcel("D:\\autoit\\融资资料审核发票导入明细.xlsx", labelList);
	}
	
	/*
	 * 上传文件
	 */
	@Override
	public void uploadFile(WebElement element,String exeFileName) throws InterruptedException{
		String replacement=null;
		String exeFileNameNew=null;
		switch (TestBase.browserType) {
		case "Chrome":
			replacement = "ForGoogle";
			exeFileNameNew=exeFileName.replaceAll("ForIE", replacement);
			break;
		case "IE":
			replacement = "ForIE";
			exeFileNameNew=exeFileName.replaceAll("ForGoogle", replacement);
			break;
		default:
			try {
				throw new Exception("非法浏览器配置！");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		
		try{ 
			Runtime.getRuntime().exec("D:\\autoit\\"+exeFileNameNew);

			}catch(Exception e){ 
				e.printStackTrace();
			} 
		element.click();
		Thread.sleep(6000);
	}
	/*
	 * 添加融资资料
	 */
	public void financeDataAdd() throws InterruptedException{
		addBtn.click();
		Thread.sleep(2000);
		addFinanceCorpNameSearchBtn.click();
		Thread.sleep(2000);
		windowFinanceCorpNameInput.sendKeys(TestBase.FinanceDataApproveCorpName);
		windowFinanceCorpNameSearch.click();
		Thread.sleep(2000);
		windowFinanceCorpNameSelectBtns.get(0).click();
		Thread.sleep(2000);
		
		addCorpNameCoreSearchBtn.click();
		Thread.sleep(2000);
		windowCorpNameCoreInput.sendKeys(TestBase.FinanceDataApproveCorpNameCore);
		windowCorpNameCoreSearch.click();
		Thread.sleep(2000);
		windowCorpNameCoreSelectBtns.get(0).click();
		Thread.sleep(2000);
		
		addFinanceNameInput.click();
		addFinanceNameChose.click();
		
		addCcbscfProductInput.click();
		addCreditChose.click();
		
		addFinanceAmountInput.sendKeys("100");
		addAbstractInput.sendKeys("摘要");
		
		addNextStepBtn.click();
		
		uploadFile(uploadGSZCBtn, "uploadFileForGoogle.exe");
		uploadFile(uploadGRZXBtn, "uploadFileForGoogle.exe");
		uploadFile(uploadQYZXBtn, "uploadFileForGoogle.exe");
		uploadFile(uploadSWHTBtn, "uploadFileForGoogle.exe");
		uploadFile(uploadFPBtn, "uploadFileForGoogle.exe");
		uploadFile(uploadQTBtn, "uploadFileForGoogle.exe");
		uploadFile(uploadZMWJBtn, "uploadFileForGoogle.exe");
		
		addSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "融资资料审核记录新增成功");
		messageConfirmBtn.click();
	}
	
	/*
	 * 修改融资资料
	 */
	public void financeDataModify() throws InterruptedException{
		Thread.sleep(2000);
		Assert.assertNotNull(downloadGSZCBtn);
		Assert.assertNotNull(downloadGRZXBtn);
		Assert.assertNotNull(downloadQYZXBtn);
		Assert.assertNotNull(downloadSWHTBtn);
		Assert.assertNotNull(downloadFPBtn);
		Assert.assertNotNull(downloadQTBtn);
		Assert.assertNotNull(downloadZMWJBtn);
		uploadFile(reUploadGSZCBtn, "reUploadFileForGoogle.exe");
		uploadFile(reUploadGRZXBtn, "reUploadFileForGoogle.exe");
		uploadFile(reUploadQYZXBtn, "reUploadFileForGoogle.exe");
		uploadFile(reUploadSWHTBtn, "reUploadFileForGoogle.exe");
		uploadFile(reUploadFPBtn, "reUploadFileForGoogle.exe");
		uploadFile(reUploadQTBtn, "reUploadFileForGoogle.exe");
		uploadFile(reUploadZMWJBtn, "reUploadFileForGoogle.exe");
		
		saveBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "融资资料审核记录修改成功");
		messageConfirmBtn.click();
		Thread.sleep(5000);
	}
	
	/*
	 * 失效记录
	 */
	public void financeDataInvalid() throws InterruptedException{
		Thread.sleep(2000);
		invalidConfirmBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "失效完成");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
	
	/*
	 * 搜索某种状态记录,并翻到最后一页
	 */
	public void getDataByWorkFlow(WebElement element) throws InterruptedException{
		workFlowInput.click();
		Thread.sleep(1000);
		element.click();
		searchBtn.click();
		Thread.sleep(5000);
		//如果最后一页页码不为1，则翻页
		if(!(pageNums.get(pageNums.size() - 1)).getText().equals("1")){
			pageNums.get(pageNums.size() - 1).click();
			Thread.sleep(5000);
		}
	}
	/*
	 * 初审领取任务
	 */
	public void operatorGetTask(int operatorApproveBtnNum) throws InterruptedException{
		operatorApproveBtns.get(operatorApproveBtnNum).click();
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("融资企业："+TestBase.FinanceDataApproveCorpName));
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("核心企业："+TestBase.FinanceDataApproveCorpNameCore));
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("资金方："));
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("平台产品：融信"));
		Thread.sleep(2000);
		operatorClaimTaskConfirmBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核任务认领成功");
		messageConfirmBtn.click();
		Thread.sleep(5000);
	}
	/*
	 * 复审领取任务
	 */
	public void managerGetTask(int manApproveBtnNum) throws InterruptedException{
		managerApproveBtns.get(manApproveBtnNum).click();
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("融资企业："+TestBase.FinanceDataApproveCorpName));
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("核心企业："+TestBase.FinanceDataApproveCorpNameCore));
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("资金方："));
		Assert.assertTrue(operatorClaimTaskMessge.getText().contains("平台产品：融信"));
		operatorClaimTaskConfirmBtn.click();
		Thread.sleep(2000);
	}
	/*
	 * 初审审核公司章程通过
	 */
	public void operatorApproveGSZCPass() throws InterruptedException{
		dataListChildGSZCApproveBtn.click();
		Thread.sleep(2000);
		opeApproveGSZCSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
	
	/*
	 * 初审审核公司章程不通过
	 */
	public void operatorApproveGSZCNoPass() throws InterruptedException{
		dataListChildGSZCApproveBtn.click();
		Thread.sleep(2000);
		noPassGSZCBtn.click();
		opeApproveGSZCSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(dataListChildGSZCOpeResult.getText(), "审核不通过");
		Assert.assertEquals(dataListChildGSZCOpeReason.getText(), "1. 企业最新章程，且与融资申请企业名称一致");
	}
	
	/*
	 * 复审审核公司章程不通过
	 */
	public void managerApproveGSZCNoPass() throws InterruptedException{
		managerApproveGSZCTab.click();
		Thread.sleep(2000);
		noPassGSZCBtn.click();
	}
	/*
	 * 初审审核个人征信通过
	 */
	public void operatorApproveGRZXPass() throws InterruptedException{
		dataListChildGRZXApproveBtn.click();
		Thread.sleep(2000);
		opeApproveGRZXSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
	/*
	 * 初审审核个人征信不通过
	 */
	public void operatorApproveGRZXNoPass() throws InterruptedException{
		dataListChildGRZXApproveBtn.click();
		Thread.sleep(2000);
		noPassGRZXBtn.click();
		opeApproveGRZXSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(dataListChildGRZXOpeResult.getText(), "审核不通过");
		Assert.assertEquals(dataListChildGRZXOpeReason.getText(), "1. 法人签字，国企可不提供");
	}
	/*
	 * 复审审核个人征信不通过
	 */
	public void managerApproveGRZXNoPass() throws InterruptedException{
		managerApproveGRZXTab.click();
		Thread.sleep(2000);
		noPassGRZXBtn.click();
	}
	/*
	 * 初审审核企业征信通过
	 */
	public void operatorApproveQYZXPass() throws InterruptedException{
		dataListChildQYZXApproveBtn.click();
		Thread.sleep(2000);
		opeApproveQYZXSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
	/*
	 * 初审审核企业征信不通过
	 */
	public void operatorApproveQYZXNoPass() throws InterruptedException{
		dataListChildQYZXApproveBtn.click();
		Thread.sleep(2000);
		noPassQYZXBtn.click();
		opeApproveQYZXSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(dataListChildQYZXOpeResult.getText(), "审核不通过");
		Assert.assertEquals(dataListChildQYZXOpeReason.getText(), "1. 法人签字并加盖公章");
	}
	/*
	 * 复审审核企业征信不通过
	 */
	public void managerApproveQYZXNoPass() throws InterruptedException{
		managerApproveQYZXTab.click();
		Thread.sleep(2000);
		noPassQYZXBtn.click();
	}
	/*
	 * 初审审核商务合同通过
	 */
	public void operatorApproveSWHTPass() throws InterruptedException{
		dataListChildSWHTApproveBtn.click();
		Thread.sleep(2000);
		opeApproveSWHTBusiContractCodeInput.sendKeys("123");
		
		scrollIntoView(opeApproveSWHTBusiContractCodeInput);
		opeApproveSWHTCpSearchBtn.click();
		searchAndSelectCorpName(TestBase.FinanceDataApproveCorpNameCore);
		opeApproveSWHTSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
	/*
	 * 初审审核发票通过
	 */
	public void operatorApproveFPPass() throws Exception{
		dataListChildFPApproveBtn.click();
		Thread.sleep(2000);
		writeExcelImportInvoice();
		uploadFile(opeApproveFPImportBtn, "importInvoiceForGoogle.exe");
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "导入发票格式化数据成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
		scrollIntoView(opeApproveFPDeleteAllBtn);
		opeApproveFPSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
//	/*
//	 * 复审审核商务合同不通过
//	 */
//	public void managerApproveSWHTNoPass() throws InterruptedException{
//		managerApproveSWHTTab.click();
//		Thread.sleep(2000);
//		noPassSWHTBtn.click();
//	}
	/*
	 * 初审审核其它通过
	 */
	public void operatorApproveQTPass() throws InterruptedException{
		dataListChildQTApproveBtn.click();
		Thread.sleep(2000);
		opeApproveQTSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
	/*
	 * 初审审核其它不通过
	 */
	public void operatorApproveQTNoPass() throws InterruptedException{
		dataListChildQTApproveBtn.click();
		Thread.sleep(2000);
		noPassQTBtn.click();
		opeApproveQTSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(dataListChildQTOpeResult.getText(), "审核不通过");
		Assert.assertEquals(dataListChildQTOpeReason.getText(), "1. 其它1描述1");
	}
	/*
	 * 复审审核其它不通过
	 */
	public void managerApproveQTNoPass() throws InterruptedException{
		managerApproveQTTab.click();
		Thread.sleep(2000);
		noPassQTBtn.click();
	}
	/*
	 * 初审审核经营异常证明文件通过
	 */
	public void operatorApproveZMWJPass() throws InterruptedException{
		dataListChildZMWJApproveBtn.click();
		Thread.sleep(2000);
		uploadFile(opeApproveZMWJUploadBtn, "uploadFileForGoogle.exe");
		opeApproveZMWJSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
	}
	/*
	 * 初审审核经营异常证明文件不通过
	 */
	public void operatorApproveZMWJNoPass() throws InterruptedException{
		dataListChildZMWJApproveBtn.click();
		Thread.sleep(2000);
		noPassZMWJBtn.click();
		opeApproveZMWJSubmitBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(message.getText(), "审核成功");
		messageConfirmBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(dataListChildZMWJOpeResult.getText(), "审核不通过");
		Assert.assertEquals(dataListChildZMWJOpeReason.getText(), "1. 工商网信息无异常");
	}
	/*
	 * 复审审核经营异常证明文件不通过
	 */
	public void managerApproveZMWJNoPass() throws InterruptedException{
		managerApproveZMWJTab.click();
		Thread.sleep(2000);
		noPassZMWJBtn.click();
	}
	public static void main(String[] args) throws InterruptedException{
		// 复审审核
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2, TestBase.operateManagerPasswordTeam2);
		TestBase.biz.homePage().gotoFinanceDataApprovePage();
		Thread.sleep(5000);
		//搜索状态为初审审核通过的数据
		TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFINAL_AUDITING);

		int manApproveNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
		// 领取任务
		int manApproveBtnNum = TestBase.biz.financeDataApprove().managerApproveBtns.size() - 1;
		TestBase.biz.financeDataApprove().managerApproveBtns.get(manApproveBtnNum).click();
		Thread.sleep(2000);
		TestBase.biz.financeDataApprove().managerApproveGSZCNoPass();
		TestBase.biz.financeDataApprove().managerApproveGRZXNoPass();
		TestBase.biz.financeDataApprove().managerApproveQYZXNoPass();
		TestBase.biz.financeDataApprove().managerApproveQTNoPass();
		TestBase.biz.financeDataApprove().managerApproveZMWJNoPass();
		TestBase.biz.financeDataApprove().manApproveSubtmitBtn.click();
		Thread.sleep(3000);
		
		TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFINAL_AUDITED);
		manApproveNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
		Assert.assertEquals(TestBase.biz.financeDataApprove().dataListWorkFlows.get(manApproveNum).getText(),
				"复审审核完成");
		Assert.assertEquals(TestBase.biz.financeDataApprove().dataListApproveResults.get(manApproveNum).getText(),
				"审核不通过");
	}

}
