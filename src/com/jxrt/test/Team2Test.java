package com.jxrt.test;

import java.time.LocalDate;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Team2Test extends TestBase {

	@BeforeMethod
	public void setup_Biz(){
		setupBiz();

	}
	
	@AfterMethod
	public void tearDown_Biz() {
		tearDownBiz();
	}
	/*
	 * 平台端账款签发新增（单笔）
	 */
	@Test(enabled=true,priority = 2)
	public void TestReceivableIssue() throws InterruptedException {
		//初始化数据
		String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
		String corpNameAccept=TestBase.corpNameReceivableTeam2;
		int rand=new Random().nextInt(1000);
		String busiContractCode="sw"+rand;
		String applyAmount=String.valueOf(rand);
		LocalDate today=LocalDate.now();
		String maturityDate=today.plusDays(30).toString();
		String abstract_="zy"+rand;
		//登录并新增账款
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableIssuePage();
		Thread.sleep(1000);
		TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore,corpNameAccept,
				busiContractCode,applyAmount,maturityDate,abstract_);
		Thread.sleep(3000);
		//上传文件
		TestBase.biz.receivableIssuePage().uploadFile(TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(0),"uploadFileForGoogle.exe");
		//勾选记录并点击提交
		TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
		TestBase.biz.receivableIssuePage().scrollIntoView(TestBase.biz.receivableIssuePage().receivableListCores.get(0));
		TestBase.biz.receivableIssuePage().submitBtn.click();
		//断言
		Thread.sleep(2000);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(), "提交成功！请等待主管审核，您可以在账款查询中查看进度!");
		TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();
		
		//菜单归位
		Thread.sleep(1000);
		TestBase.biz.homePage().tradeManagementTab.click();
		//进入账款查询
		TestBase.biz.homePage().gotoReceivableSearchPage();
		if(TestBase.biz.receivableSearchPage().receivableListCores.get(0).isDisplayed())
		{
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableSearchPage().searchBtn.click();
		}
		//断言
		Thread.sleep(3000);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListCores.get(0).getText(),corpNameCore);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListCores.get(0).getText(),corpNameCore);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListProductTypes.get(0).getText(),"e点通");
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListCorpNameAccepts.get(0).getText(),corpNameAccept);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListBusiContractCodes.get(0).getText(),busiContractCode);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApplyAmounts.get(0).getText(),applyAmount+".00");
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListMaturityDates.get(0).getText(),maturityDate);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListAbstract_s.get(0).getText(),abstract_);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListSubmitDates.get(0).getText(),today.toString());
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(),"");
	}
	/*
	 * 平台端账款签发审核通过（单条）
	 */
	@Test(enabled=true,priority = 2)
	public void TestReceivableApprovePassSigle() throws InterruptedException {
		//调用签发
		TestReceivableIssue();
		tearDownBiz();
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		//进入账款审核
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableApprovePage();
		String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
		String corpNameAccept=TestBase.corpNameReceivableTeam2;
		TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
		TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
		TestBase.biz.receivableApprovePage().searchBtn.click();
		Thread.sleep(2000);
		String pkCredit=TestBase.biz.receivableApprovePage().receivableListPkCredits.get(0).getText();
		LocalDate today=LocalDate.now();
		String applyAmount=TestBase.biz.receivableApprovePage().receivableListApplyAmounts.get(0).getText();
		TestBase.biz.receivableApprovePage().approvePass(1);
		
		//菜单归位
		Thread.sleep(1000);
		TestBase.biz.homePage().tradeManagementTab.click();
		//进入账款查询
		TestBase.biz.homePage().gotoReceivableSearchPage();
		Thread.sleep(3000);
		TestBase.biz.receivableSearchPage().IssuedTab.click();
		if(TestBase.biz.receivableSearchIssuedTabPage().receivableListCores.get(0).isDisplayed())
		{
			TestBase.biz.receivableSearchIssuedTabPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableSearchIssuedTabPage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableSearchIssuedTabPage().approveDateBeginInput.sendKeys(today.toString());
			TestBase.biz.receivableSearchIssuedTabPage().applyAmountMinInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchIssuedTabPage().applyAmountMaxInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchIssuedTabPage().searchBtn.click();
		}
		Thread.sleep(8000);
		//断言
		Assert.assertEquals(TestBase.biz.receivableSearchIssuedTabPage().receivableListPkCredits.get(0).getText(),pkCredit);
	}
	/*
	 * 平台端账款签发审核不通过（单笔）
	 */
	@Test(enabled=true,priority = 1)
	public void TestReceivableApproveNoPass() throws InterruptedException {
		//调用签发
		TestReceivableIssue();
		tearDownBiz();
		
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,TestBase.operateManagerPasswordTeam2);
		//进入账款审核
		TestBase.biz.homePage().gotoReceivableApprovePage();
		String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
		String corpNameAccept=TestBase.corpNameReceivableTeam2;
		TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
		TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
		TestBase.biz.receivableApprovePage().searchBtn.click();
		Thread.sleep(2000);
		String pkCredit=TestBase.biz.receivableApprovePage().receivableListPkCredits.get(0).getText();
		LocalDate today=LocalDate.now();
		String applyAmount=TestBase.biz.receivableApprovePage().receivableListApplyAmounts.get(0).getText();
		//将审核不通过的白条号和金额写入TestBase中，用于后续审核不通过修改
		ReceivableApproveNoPassPkCredit=pkCredit;
		ReceivableApproveNoPassApplyAmount=applyAmount;
		TestBase.biz.receivableApprovePage().approveNoPass(1);
		
		//菜单归位
		Thread.sleep(1000);
		TestBase.biz.homePage().tradeManagementTab.click();
		//进入账款查询
		TestBase.biz.homePage().gotoReceivableSearchPage();
		if(TestBase.biz.receivableSearchPage().receivableListCores.get(0).isDisplayed())
		{
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableSearchPage().submitDateBeginInput.sendKeys(today.toString());
			TestBase.biz.receivableSearchPage().applyAmountMinInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchPage().applyAmountMaxInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchPage().searchBtn.click();
		}
		Thread.sleep(3000);
		//断言
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListPkCredits.get(0).getText(),pkCredit);
		Thread.sleep(1000);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(),"不通过");

	}
	/*
	 * 平台端账款签发审核不通过修改
	 */
	@Test(enabled=true,priority = 1)
	public void TestReceivableApproveNoPassModify() throws InterruptedException {
		TestReceivableApproveNoPass();
		tearDownBiz();
		
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		//进入账款查询
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableSearchPage();
		if(TestBase.biz.receivableSearchPage().receivableListCores.get(0).isDisplayed())
		{
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(TestBase.corpNameCoreReceivableTeam2);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(TestBase.corpNameReceivableTeam2);
			TestBase.biz.receivableSearchPage().submitDateBeginInput.sendKeys(LocalDate.now().toString());
			TestBase.biz.receivableSearchPage().applyAmountMinInput.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().applyAmountMaxInput.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().searchBtn.click();
		}
		Thread.sleep(3000);
		//断言
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListPkCredits.get(0).getText(),TestBase.ReceivableApproveNoPassPkCredit);
		Thread.sleep(1000);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(),"不通过");
		
		TestBase.biz.receivableSearchPage().uploadFile(TestBase.biz.receivableSearchPage().receivableListReUploadBtns.get(0), "reUploadFileForGoogle.exe");
		TestBase.biz.receivableSearchPage().receivableListModiyBtns.get(0).click();
		Thread.sleep(1000);
		
		TestBase.biz.receivableSearchPage().receivableModify();
	}
	
	/*
	 * 平台端账款签发审核不通过删除
	 */
	@Test(enabled=true,priority = 0)
	public void TestReceivableApproveNoPassDelete() throws InterruptedException {
		TestReceivableApproveNoPass();
		tearDownBiz();
		
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		//进入账款查询
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableSearchPage();
		if(TestBase.biz.receivableSearchPage().receivableListCores.get(0).isDisplayed())
		{
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(TestBase.corpNameCoreReceivableTeam2);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(TestBase.corpNameReceivableTeam2);
			TestBase.biz.receivableSearchPage().submitDateBeginInput.sendKeys(LocalDate.now().toString());
			TestBase.biz.receivableSearchPage().applyAmountMinInput.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().applyAmountMaxInput.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().searchBtn.click();
		}
		Thread.sleep(3000);
		//断言
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListPkCredits.get(0).getText(),TestBase.ReceivableApproveNoPassPkCredit);
		Thread.sleep(1000);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(),"不通过");
		
		TestBase.biz.receivableSearchPage().receivableListDeleteBtns.get(0).click();
		Thread.sleep(2000);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().InstructionResultSencond.getText(), "是否确认删除选中的应付账款信息？");
		TestBase.biz.receivableSearchPage().InstructionWindowConfirmBtnSencond.click();
		Thread.sleep(2000);
		Assert.assertEquals(TestBase.biz.receivableSearchPage().InstructionResult.getText(), "删除成功");
		TestBase.biz.receivableSearchPage().InstructionWindowConfirmBtn.click();
	}
	
	/*
	 * 平台端账款签发新增后进行修改和删除
	 */
	@Test(enabled=true,priority = 0)
	public void TestReceivableIssueModifyDelete() throws InterruptedException {
		//初始化数据
		String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
		String corpNameAccept=TestBase.corpNameReceivableTeam2;
		int rand=new Random().nextInt(1000);
		String busiContractCode="sw"+rand;
		String applyAmount=String.valueOf(rand);
		LocalDate today=LocalDate.now();
		String maturityDate=today.plusDays(30).toString();
		String abstract_="zy"+rand;
		//登录并新增账款
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableIssuePage();
		Thread.sleep(1000);
		TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore,corpNameAccept,
				busiContractCode,applyAmount,maturityDate,abstract_);
		Thread.sleep(3000);
		TestBase.biz.receivableIssuePage().receivableListModifyBtns.get(0).click();
		Thread.sleep(2000);
		TestBase.biz.receivableIssuePage().receivableModify();
		Thread.sleep(2000);
		TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
		TestBase.biz.receivableIssuePage().deleteBtn.click();
		Thread.sleep(2000);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResultSencond.getText(), "是否确认删除选中的应付账款信息？");
		TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtnSencond.click();
		Thread.sleep(2000);
	}
	
	/*
	 * 平台端账款签发批量提交-审核通过
	 */
	@Test(enabled=true,priority = 0)
	public void TestReceivableApprovePassBath() throws InterruptedException {
		//初始化数据
		String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
		String corpNameAccept=TestBase.corpNameReceivableTeam2;
		int rand=new Random().nextInt(1000);
		String busiContractCode="sw"+rand;
		String applyAmount=String.valueOf(rand);
		LocalDate today=LocalDate.now();
		String maturityDate=today.plusDays(30).toString();
		String abstract_="zy"+rand;
		//登录并新增账款
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableIssuePage();
		Thread.sleep(1000);
		TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore,corpNameAccept,
				busiContractCode,applyAmount,maturityDate,abstract_);
		Thread.sleep(3000);
		TestBase.biz.receivableIssuePage().singleIssueBtn.click();
		Thread.sleep(3000);
		//上传文件
		TestBase.biz.receivableIssuePage().uploadFile(TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(0),"uploadFileForGoogle.exe");
		TestBase.biz.receivableIssuePage().uploadFile(TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(1),"uploadFileForGoogle.exe");
		//勾选记录并点击提交
		TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
		TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(1).click();
		TestBase.biz.receivableIssuePage().submitBtn.click();
		//断言
		Thread.sleep(3000);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(), "提交成功！请等待主管审核，您可以在账款查询中查看进度!");
		TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();
		tearDownBiz();
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		//进入账款审核
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableApprovePage();
		TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
		TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
		TestBase.biz.receivableApprovePage().searchBtn.click();
		Thread.sleep(2000);

		TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(0).click();
		TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(1).click();
		Thread.sleep(1000);
		TestBase.biz.receivableApprovePage().approvePassBtn.click();
		Thread.sleep(10000);
		//断言
		Assert.assertEquals(TestBase.biz.receivableApprovePage().InstructionResult.getText(), "审核通过！账款已发送至供应商。");
		TestBase.biz.receivableApprovePage().InstructionWindowConfirmBtn.click();
		}
	
	/*
	 * 平台端账款签发批量提交-审核通过
	 */
	@Test(enabled=true,priority = 0)
	public void TestReceivableApproveNoPassBath() throws InterruptedException {
		//初始化数据
		String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
		String corpNameAccept=TestBase.corpNameReceivableTeam2;
		int rand=new Random().nextInt(1000);
		String busiContractCode="sw"+rand;
		String applyAmount=String.valueOf(rand);
		LocalDate today=LocalDate.now();
		String maturityDate=today.plusDays(30).toString();
		String abstract_="zy"+rand;
		//登录并新增账款
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableIssuePage();
		Thread.sleep(1000);
		TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore,corpNameAccept,
				busiContractCode,applyAmount,maturityDate,abstract_);
		Thread.sleep(3000);
		TestBase.biz.receivableIssuePage().singleIssueBtn.click();
		Thread.sleep(3000);
		//上传文件
		TestBase.biz.receivableIssuePage().uploadFile(TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(0),"uploadFileForGoogle.exe");
		TestBase.biz.receivableIssuePage().uploadFile(TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(1),"uploadFileForGoogle.exe");
		//勾选记录并点击提交
		TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
		TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(1).click();
		TestBase.biz.receivableIssuePage().submitBtn.click();
		//断言
		Thread.sleep(3000);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(), "提交成功！请等待主管审核，您可以在账款查询中查看进度!");
		TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();
		tearDownBiz();
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		//进入账款审核
		Thread.sleep(1000);
		TestBase.biz.homePage().gotoReceivableApprovePage();
		TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
		TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
		TestBase.biz.receivableApprovePage().searchBtn.click();
		Thread.sleep(2000);

		TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(0).click();
		TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(1).click();
		Thread.sleep(1000);

		TestBase.biz.receivableApprovePage().receivableListNotPassReasonInputs.get(0).click();
		Thread.sleep(1000);
		TestBase.biz.receivableApprovePage().receivableListCorpNameCoreWrongs.get(0).click();
		
		TestBase.biz.receivableApprovePage().receivableListNotPassReasonInputs.get(1).click();
		Thread.sleep(1000);
		TestBase.biz.receivableApprovePage().receivableListCorpNameCoreWrongs.get(1).click();
		TestBase.biz.receivableApprovePage().approveNoPassBtn.click();
		Thread.sleep(2000);
		
		//断言
		Assert.assertEquals(TestBase.biz.receivableApprovePage().InstructionResult.getText(), "审核不通过，已退回至经办。");
		TestBase.biz.receivableApprovePage().InstructionWindowConfirmBtn.click();
		}
}

