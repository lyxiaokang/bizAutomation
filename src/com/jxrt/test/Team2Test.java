package com.jxrt.test;

import java.time.LocalDate;
import java.util.Random;

import org.openqa.selenium.Keys;
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
	
	@Test(enabled=false,priority = 2)
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
		TestBase.biz.homePage().gotoReceivableIssuePage();
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
	
	@Test(enabled=true,priority = 2)
	public void TestReceivableApprovePass() throws InterruptedException {
		//调用签发
		TestReceivableIssue();
		tearDownBiz();
		TestBase.setupBiz();
		TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,TestBase.operateOperatorPasswordTeam2);
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
	
	@Test(enabled=true,priority = 0)
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
	
	@Test(enabled=true,priority = 1)
	public void TestReceivableApproveNoPassModify() throws InterruptedException {
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		//进入账款查询
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
		TestBase.biz.receivableSearchPage().receivableListModiyBtns.get(0).click();
		Thread.sleep(1000);
		
		int rand=new Random().nextInt(1000);
		String busiContractCode="sw"+rand+"xg";
		String applyAmount=String.valueOf(rand);
		LocalDate today=LocalDate.now();
		String maturityDate=today.plusDays(31).toString();
		String abstract_="zy"+rand+"xg";
		TestBase.biz.receivableSearchPage().receivableModifyBusiContractCodeInput.clear();
		TestBase.biz.receivableSearchPage().receivableModifyBusiContractCodeInput.sendKeys(busiContractCode);
		TestBase.biz.receivableSearchPage().receivableModifyApplyAmount.clear();
		TestBase.biz.receivableSearchPage().receivableModifyApplyAmount.sendKeys(applyAmount);
		TestBase.biz.receivableSearchPage().receivableModifyMaturityDateInput.clear();
		TestBase.biz.receivableSearchPage().receivableModifyMaturityDateInput.sendKeys(maturityDate);
		TestBase.biz.receivableSearchPage().receivableModifyAbstract_Input.clear();
		TestBase.biz.receivableSearchPage().receivableModifyAbstract_Input.sendKeys(abstract_);
		Thread.sleep(2000);
		TestBase.biz.receivableSearchPage().submitBtn.click();
		//断言
		Thread.sleep(5000);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(), "提交成功！请等待主管审核，您可以在账款查询中查看进度!");
		Thread.sleep(2000);
	}
	
}

