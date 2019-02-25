package com.jxrt.test;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.jxrt.dbutil.OracleDataFactory;

public class Team2Test extends TestBase {
	/*
	 * 每次运行前清空下载文件列表
	 */
	@BeforeSuite(alwaysRun = true)
	public void clearDownloadsPath() {
		File downloadsFiles=new File(TestBase.downloadsPath);
		for(File file:downloadsFiles.listFiles()){
			file.delete();
		}
	}
	@BeforeMethod(alwaysRun = true)
	public void setup_Biz() {
		setupBiz();

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown_Biz() {
		tearDownBrowser();
	}

	/*
	 * 平台端账款签发新增（单笔）
	 */
	@Test(groups = "ReceivableIssue-test",enabled = false, priority = 0)
	public void TestReceivableIssue() throws InterruptedException {
		try {
			// 初始化数据
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpNameAccept = TestBase.corpNameReceivableTeam2;
			int rand = new Random().nextInt(1000);
			String busiContractCode = "sw" + rand;
			String applyAmount = String.valueOf(rand);
			LocalDate today = LocalDate.now();
			String maturityDate = today.plusDays(30).toString();
			String abstract_ = "zy" + rand;
			// 登录并新增账款
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableIssuePage();
			Thread.sleep(2000);
			TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore, corpNameAccept, busiContractCode,
					applyAmount, maturityDate, abstract_);
			Thread.sleep(3000);
			// 上传文件
			TestBase.biz.receivableIssuePage().uploadFile(
					TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(0), "uploadFileForGoogle.exe");
			// 勾选记录并点击提交
			TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
			TestBase.biz.receivableIssuePage()
					.scrollIntoView(TestBase.biz.receivableIssuePage().receivableListCores.get(0));
			TestBase.biz.receivableIssuePage().submitBtn.click();
			// 断言
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(),
					"提交成功！请等待主管审核，您可以在账款查询中查看进度!");
			TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();

			// 菜单归位
			Thread.sleep(2000);
			TestBase.biz.homePage().creditManagementTab.click();
			// 进入账款查询
			TestBase.biz.homePage().gotoReceivableSearchPage();
			Thread.sleep(2000);
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableSearchPage().searchBtn.click();
			// 断言
			Thread.sleep(3000);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListCores.get(0).getText(), corpNameCore);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListProductTypes.get(0).getText(), "e点通");
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListCorpNameAccepts.get(0).getText(),
					corpNameAccept);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListBusiContractCodes.get(0).getText(),
					busiContractCode);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApplyAmounts.get(0).getText(),
					applyAmount + ".00");
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListMaturityDates.get(0).getText(),
					maturityDate);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListAbstract_s.get(0).getText(),
					abstract_);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListSubmitDates.get(0).getText(),
					today.toString());
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(), "");
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端账款签发审核通过（单条）
	 */
	@Test(groups = "ReceivableIssue-test",enabled = true, priority = 0)
	public void TestReceivableApprovePass() throws InterruptedException {
		try {
			// 调用签发
			TestReceivableIssue();
			tearDownBrowser();
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			// 进入账款审核
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableApprovePage();
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpNameAccept = TestBase.corpNameReceivableTeam2;
			TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableApprovePage().searchBtn.click();
			Thread.sleep(2000);
			String pkCredit = TestBase.biz.receivableApprovePage().receivableListPkCredits.get(0).getText();
			LocalDate today = LocalDate.now();
			String applyAmount = TestBase.biz.receivableApprovePage().receivableListApplyAmounts.get(0).getText();
			TestBase.biz.receivableApprovePage().approvePass(1);

			// 菜单归位
			Thread.sleep(2000);
			TestBase.biz.homePage().creditManagementTab.click();
			// 进入账款查询
			TestBase.biz.homePage().gotoReceivableSearchPage();
			Thread.sleep(3000);
			TestBase.biz.receivableSearchPage().IssuedTab.click();
			Thread.sleep(5000);
			TestBase.biz.receivableSearchIssuedTabPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableSearchIssuedTabPage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableSearchIssuedTabPage().approveDateBeginInput.sendKeys(today.toString());
			TestBase.biz.receivableSearchIssuedTabPage().applyAmountMinInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchIssuedTabPage().applyAmountMaxInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchIssuedTabPage().searchBtn.click();
			Thread.sleep(5000);
			// 断言
			Assert.assertEquals(TestBase.biz.receivableSearchIssuedTabPage().receivableListPkCredits.get(0).getText(),
					pkCredit);
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端账款签发审核不通过（单笔）
	 */
	@Test(groups = "ReceivableIssue-test",enabled = true, priority = 0)
	public void TestReceivableApproveNoPass() throws InterruptedException {
		try {
			// 调用签发
			TestReceivableIssue();
			tearDownBrowser();

			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2, TestBase.operateManagerPasswordTeam2);
			// 进入账款审核
			TestBase.biz.homePage().gotoReceivableApprovePage();
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpNameAccept = TestBase.corpNameReceivableTeam2;
			TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableApprovePage().searchBtn.click();
			Thread.sleep(2000);
			String pkCredit = TestBase.biz.receivableApprovePage().receivableListPkCredits.get(0).getText();
			LocalDate today = LocalDate.now();
			String applyAmount = TestBase.biz.receivableApprovePage().receivableListApplyAmounts.get(0).getText();
			// 将审核不通过的白条号和金额写入TestBase中，用于后续审核不通过修改
			ReceivableApproveNoPassPkCredit = pkCredit;
			ReceivableApproveNoPassApplyAmount = applyAmount;
			TestBase.biz.receivableApprovePage().approveNoPass(1);

			// 菜单归位
			Thread.sleep(2000);
			TestBase.biz.homePage().creditManagementTab.click();
			// 进入账款查询
			TestBase.biz.homePage().gotoReceivableSearchPage();
			Thread.sleep(2000);
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableSearchPage().submitDateBeginInput.sendKeys(today.toString());
			TestBase.biz.receivableSearchPage().applyAmountMinInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchPage().applyAmountMaxInput.sendKeys(applyAmount);
			TestBase.biz.receivableSearchPage().searchBtn.click();
			Thread.sleep(3000);
			// 断言
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListPkCredits.get(0).getText(), pkCredit);
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(),
					"不通过");

		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端账款签发审核不通过修改
	 */
	@Test(groups = "ReceivableIssue-test",enabled = true, priority = 0)
	public void TestReceivableApproveNoPassModify() throws InterruptedException {
		try {
			TestReceivableApproveNoPass();
			tearDownBrowser();

			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			// 进入账款查询
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableSearchPage();
			Thread.sleep(2000);
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(TestBase.corpNameCoreReceivableTeam2);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(TestBase.corpNameReceivableTeam2);
			TestBase.biz.receivableSearchPage().submitDateBeginInput.sendKeys(LocalDate.now().toString());
			TestBase.biz.receivableSearchPage().applyAmountMinInput
					.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().applyAmountMaxInput
					.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().searchBtn.click();
			Thread.sleep(3000);
			// 断言
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListPkCredits.get(0).getText(),
					TestBase.ReceivableApproveNoPassPkCredit);
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(),
					"不通过");

			TestBase.biz.receivableSearchPage().uploadFile(
					TestBase.biz.receivableSearchPage().receivableListReUploadBtns.get(0), "reUploadFileForGoogle.exe");
			TestBase.biz.receivableSearchPage().receivableListModiyBtns.get(0).click();
			Thread.sleep(2000);

			TestBase.biz.receivableSearchPage().receivableModify();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端账款签发审核不通过删除
	 */
	@Test(groups = "ReceivableIssue-test",enabled = true, priority = 0)
	public void TestReceivableApproveNoPassDelete() throws InterruptedException {
		try {
			TestReceivableApproveNoPass();
			tearDownBrowser();

			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			// 进入账款查询
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableSearchPage();
			Thread.sleep(2000);
			TestBase.biz.receivableSearchPage().corpNameCoreInput.sendKeys(TestBase.corpNameCoreReceivableTeam2);
			TestBase.biz.receivableSearchPage().corpNameAcceptInput.sendKeys(TestBase.corpNameReceivableTeam2);
			TestBase.biz.receivableSearchPage().submitDateBeginInput.sendKeys(LocalDate.now().toString());
			TestBase.biz.receivableSearchPage().applyAmountMinInput
					.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().applyAmountMaxInput
					.sendKeys(TestBase.ReceivableApproveNoPassApplyAmount);
			TestBase.biz.receivableSearchPage().searchBtn.click();
			Thread.sleep(3000);
			// 断言
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListPkCredits.get(0).getText(),
					TestBase.ReceivableApproveNoPassPkCredit);
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().receivableListApproveResults.get(0).getText(),
					"不通过");

			TestBase.biz.receivableSearchPage().receivableListDeleteBtns.get(0).click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().InstructionResultSencond.getText(),
					"是否确认删除选中的应付账款信息？");
			TestBase.biz.receivableSearchPage().InstructionWindowConfirmBtnSencond.click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.receivableSearchPage().InstructionResult.getText(), "删除成功");
			TestBase.biz.receivableSearchPage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端账款签发新增后进行修改和删除
	 */
	@Test(groups = "ReceivableIssue-test",enabled = true, priority = 0)
	public void TestReceivableIssueModifyDelete() throws InterruptedException {
		try {
			// 初始化数据
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpNameAccept = TestBase.corpNameReceivableTeam2;
			int rand = new Random().nextInt(1000);
			String busiContractCode = "sw" + rand;
			String applyAmount = String.valueOf(rand);
			LocalDate today = LocalDate.now();
			String maturityDate = today.plusDays(30).toString();
			String abstract_ = "zy" + rand;
			// 登录并新增账款
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableIssuePage();
			Thread.sleep(2000);
			TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore, corpNameAccept, busiContractCode,
					applyAmount, maturityDate, abstract_);
			Thread.sleep(3000);
			TestBase.biz.receivableIssuePage().receivableListModifyBtns.get(0).click();
			Thread.sleep(2000);
			TestBase.biz.receivableIssuePage().receivableModify();
			Thread.sleep(2000);
			TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
			TestBase.biz.receivableIssuePage().deleteBtn.click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResultSencond.getText(),
					"是否确认删除选中的应付账款信息？");
			TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtnSencond.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端账款签发批量提交-审核通过
	 */
	@Test(groups = "ReceivableIssue-test",enabled = true, priority = 0)
	public void TestReceivableApprovePassBath() throws InterruptedException {
		try {
			// 初始化数据
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpNameAccept = TestBase.corpNameReceivableTeam2;
			int rand = new Random().nextInt(1000);
			String busiContractCode = "sw" + rand;
			String applyAmount = String.valueOf(rand);
			LocalDate today = LocalDate.now();
			String maturityDate = today.plusDays(30).toString();
			String abstract_ = "zy" + rand;
			// 登录并新增账款
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableIssuePage();
			Thread.sleep(2000);
			TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore, corpNameAccept, busiContractCode,
					applyAmount, maturityDate, abstract_);
			Thread.sleep(3000);
			TestBase.biz.receivableIssuePage().singleIssueBtn.click();
			Thread.sleep(3000);
			// 上传文件
			TestBase.biz.receivableIssuePage().uploadFile(
					TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(0), "uploadFileForGoogle.exe");
			TestBase.biz.receivableIssuePage().uploadFile(
					TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(1), "uploadFileForGoogle.exe");
			// 勾选记录并点击提交
			TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
			TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(1).click();
			TestBase.biz.receivableIssuePage().submitBtn.click();
			// 断言
			Thread.sleep(3000);
			Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(),
					"提交成功！请等待主管审核，您可以在账款查询中查看进度!");
			TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();
			tearDownBrowser();
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			// 进入账款审核
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableApprovePage();
			TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableApprovePage().searchBtn.click();
			Thread.sleep(2000);

			TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(0).click();
			TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(1).click();
			Thread.sleep(2000);
			TestBase.biz.receivableApprovePage().approvePassBtn.click();
			Thread.sleep(10000);
			// 断言
			Assert.assertEquals(TestBase.biz.receivableApprovePage().InstructionResult.getText(), "审核通过！账款已发送至供应商。");
			TestBase.biz.receivableApprovePage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端账款签发批量提交-审核通过
	 */
	@Test(groups = "ReceivableIssue-test",enabled = true, priority = 0)
	public void TestReceivableApproveNoPassBath() throws InterruptedException {
		try {
			// 初始化数据
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpNameAccept = TestBase.corpNameReceivableTeam2;
			int rand = new Random().nextInt(1000);
			String busiContractCode = "sw" + rand;
			String applyAmount = String.valueOf(rand);
			LocalDate today = LocalDate.now();
			String maturityDate = today.plusDays(30).toString();
			String abstract_ = "zy" + rand;
			// 登录并新增账款
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableIssuePage();
			Thread.sleep(2000);
			TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore, corpNameAccept, busiContractCode,
					applyAmount, maturityDate, abstract_);
			Thread.sleep(3000);
			TestBase.biz.receivableIssuePage().singleIssueBtn.click();
			Thread.sleep(3000);
			// 上传文件
			TestBase.biz.receivableIssuePage().uploadFile(
					TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(0), "uploadFileForGoogle.exe");
			TestBase.biz.receivableIssuePage().uploadFile(
					TestBase.biz.receivableIssuePage().receivableListUploadBtns.get(1), "uploadFileForGoogle.exe");
			// 勾选记录并点击提交
			TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(0).click();
			TestBase.biz.receivableIssuePage().receivableListCheckBoxs.get(1).click();
			TestBase.biz.receivableIssuePage().submitBtn.click();
			// 断言
			Thread.sleep(3000);
			Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(),
					"提交成功！请等待主管审核，您可以在账款查询中查看进度!");
			TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();
			tearDownBrowser();
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			// 进入账款审核
			Thread.sleep(2000);
			TestBase.biz.homePage().gotoReceivableApprovePage();
			TestBase.biz.receivableApprovePage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.receivableApprovePage().corpNameAcceptInput.sendKeys(corpNameAccept);
			TestBase.biz.receivableApprovePage().searchBtn.click();
			Thread.sleep(2000);

			TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(0).click();
			TestBase.biz.receivableApprovePage().receivableListCheckBoxs.get(1).click();
			Thread.sleep(2000);

			TestBase.biz.receivableApprovePage().receivableListNotPassReasonInputs.get(0).click();
			Thread.sleep(2000);
			TestBase.biz.receivableApprovePage().receivableListCorpNameCoreWrongs.get(0).click();

			TestBase.biz.receivableApprovePage().receivableListNotPassReasonInputs.get(1).click();
			Thread.sleep(2000);
			TestBase.biz.receivableApprovePage().receivableListCorpNameCoreWrongs.get(1).click();
			TestBase.biz.receivableApprovePage().approveNoPassBtn.click();
			Thread.sleep(2000);

			// 断言
			Assert.assertEquals(TestBase.biz.receivableApprovePage().InstructionResult.getText(), "审核不通过，已退回至经办。");
			TestBase.biz.receivableApprovePage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端融资资料审核添加
	 */
	@Test(groups = "FinanceDataApprove-test",enabled = true, priority = 1)
	public void TestFinanceDataAdd() throws InterruptedException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			TestBase.biz.financeDataApprove().financeDataAdd();
			Thread.sleep(3000);
			
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);
			
			int num = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListFinanceCorpNames.get(num).getText(),
					TestBase.FinanceDataApproveCorpName);
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListCorpNameCores.get(num).getText(),
					TestBase.FinanceDataApproveCorpNameCore);
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListQueryNames.get(num).getText(),
					TestBase.operateQueryNameTeam2);
			Assert.assertTrue(TestBase.biz.financeDataApprove().dataListSubmitDates.get(num).getText()
					.contains(LocalDate.now().toString()));
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListAbstracts.get(num).getText(),
					"摘要");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListWorkFlows.get(num).getText(), "待审核");
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端融资资料审核-修改和失效
	 */
	@Test(groups = "FinanceDataApprove-test",enabled = true, priority = 1)
	public void TestFinanceDataModifyAndInvalid() throws InterruptedException {
		try {

			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			TestBase.biz.financeDataApprove().financeDataAdd();
			Thread.sleep(3000);
			
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);
			
			int modifyNum = TestBase.biz.financeDataApprove().dataLisModifyBtns.size() - 1;
			TestBase.biz.financeDataApprove().dataLisModifyBtns.get(modifyNum).click();
			TestBase.biz.financeDataApprove().financeDataModify();
			int invalidNum = TestBase.biz.financeDataApprove().dataLisInvalidBtns.size() - 1;
			TestBase.biz.financeDataApprove().dataLisInvalidBtns.get(invalidNum).click();
			TestBase.biz.financeDataApprove().financeDataInvalid();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端融资资料审核-初审领取任务并审核通过
	 */
	@Test(groups = "FinanceDataApprove-test",enabled = true, priority = 1)
	public void TestFinanceDataOpeApprovePass() throws Exception {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			TestBase.biz.financeDataApprove().financeDataAdd();
			TestBase.tearDownBrowser();

			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);
			
			int num = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 领取任务
			int operatorApproveBtnNum = TestBase.biz.financeDataApprove().operatorApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorGetTask(operatorApproveBtnNum);
			
			//搜索状态为初审审核中的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITING);
			
			num = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListOperatorNames.get(num).getText(),
					TestBase.operateOperatorNameTeam2);
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListWorkFlows.get(num).getText(), "初审审核中");
			
			// 点击下拉框对记录进行审核
			TestBase.biz.financeDataApprove().dataListDropDownBtns.get(num).click();
			Thread.sleep(2000);
			TestBase.biz.financeDataApprove().operatorApproveGSZCPass();
			TestBase.biz.financeDataApprove().operatorApproveGRZXPass();
			TestBase.biz.financeDataApprove().operatorApproveQYZXPass();
			TestBase.biz.financeDataApprove().operatorApproveSWHTPass();
			TestBase.biz.financeDataApprove().operatorApproveFPPass();
			TestBase.biz.financeDataApprove().operatorApproveQTPass();
			TestBase.biz.financeDataApprove().operatorApproveZMWJPass();
			Thread.sleep(2000);
			// 确认初审完成
			int operatorApproveDoneBtnNum = TestBase.biz.financeDataApprove().operatorApproveDoneBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorApproveDoneBtns.get(operatorApproveDoneBtnNum).click();
			Thread.sleep(2000);
			
			Assert.assertEquals(TestBase.biz.financeDataApprove().message.getText(), "初审完成");
			TestBase.biz.financeDataApprove().messageConfirmBtn.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端融资资料审核-复审退回经办
	 */
	@Test(groups = "FinanceDataApprove-test",enabled = true, priority = 1)
	public void TestFinanceDataBackToOpe() throws Exception {
		try {
			// 客服新增
			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			TestBase.biz.financeDataApprove().financeDataAdd();
			TestBase.tearDownBrowser();

			// 初审审核
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);

			int opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 领取任务
			int opeApproveBtnNum = TestBase.biz.financeDataApprove().operatorApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorGetTask(opeApproveBtnNum);
			//搜索初审审核中数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITING);
			opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 点击下拉框对记录进行审核
			TestBase.biz.financeDataApprove().dataListDropDownBtns.get(opeNum).click();
			Thread.sleep(2000);
			TestBase.biz.financeDataApprove().operatorApproveGSZCPass();
			TestBase.biz.financeDataApprove().operatorApproveGRZXPass();
			TestBase.biz.financeDataApprove().operatorApproveQYZXPass();
			TestBase.biz.financeDataApprove().operatorApproveSWHTPass();
			TestBase.biz.financeDataApprove().operatorApproveFPPass();
			TestBase.biz.financeDataApprove().operatorApproveQTPass();
			TestBase.biz.financeDataApprove().operatorApproveZMWJPass();
			Thread.sleep(2000);
			// 确认初审完成
			int opeApproveDoneBtnNum = TestBase.biz.financeDataApprove().operatorApproveDoneBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorApproveDoneBtns.get(opeApproveDoneBtnNum).click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.financeDataApprove().message.getText(), "初审完成");
			TestBase.biz.financeDataApprove().messageConfirmBtn.click();
			Thread.sleep(3000);
			TestBase.tearDownBrowser();

			// 复审审核
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2, TestBase.operateManagerPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			//搜索状态为初审审核完成的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITED);

			// 领取任务
			int manApproveBtnNum = TestBase.biz.financeDataApprove().managerApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().managerGetTask(manApproveBtnNum);
			TestBase.biz.financeDataApprove().managerApproveGSZCNoPass();
			Thread.sleep(1000);
			TestBase.biz.financeDataApprove().manApproveBackToOpeBtn.click();
			Thread.sleep(1000);
			TestBase.tearDownBrowser();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
	
	/*
	 * 平台端融资资料审核-复审通过
	 */
	@Test(groups = "FinanceDataApprove-test",enabled = true, priority = 1)
	public void TestFinanceDataManApprovePass() throws Exception {
		try {
			// 客服新增
			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			TestBase.biz.financeDataApprove().financeDataAdd();
			TestBase.tearDownBrowser();

			// 初审审核
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);

			int opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 领取任务
			int opeApproveBtnNum = TestBase.biz.financeDataApprove().operatorApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorGetTask(opeApproveBtnNum);
			//搜索初审审核中数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITING);
			opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 点击下拉框对记录进行审核
			TestBase.biz.financeDataApprove().dataListDropDownBtns.get(opeNum).click();
			Thread.sleep(2000);
			TestBase.biz.financeDataApprove().operatorApproveGSZCPass();
			TestBase.biz.financeDataApprove().operatorApproveGRZXPass();
			TestBase.biz.financeDataApprove().operatorApproveQYZXPass();
			TestBase.biz.financeDataApprove().operatorApproveSWHTPass();
			TestBase.biz.financeDataApprove().operatorApproveFPPass();
			TestBase.biz.financeDataApprove().operatorApproveQTPass();
			TestBase.biz.financeDataApprove().operatorApproveZMWJPass();
			Thread.sleep(2000);
			// 确认初审完成
			int opeApproveDoneBtnNum = TestBase.biz.financeDataApprove().operatorApproveDoneBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorApproveDoneBtns.get(opeApproveDoneBtnNum).click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.financeDataApprove().message.getText(), "初审完成");
			TestBase.biz.financeDataApprove().messageConfirmBtn.click();
			Thread.sleep(3000);
			TestBase.tearDownBrowser();

			// 复审审核
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2, TestBase.operateManagerPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			//搜索状态为初审审核完成的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITED);

			// 领取任务
			int manApproveBtnNum = TestBase.biz.financeDataApprove().managerApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().managerGetTask(manApproveBtnNum);
			Thread.sleep(1000);
			TestBase.biz.financeDataApprove().manApproveSubtmitBtn.click();
			Thread.sleep(3000);
			//搜索复审审核完成数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFINAL_AUDITED);
			int manNumNew = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListWorkFlows.get(manNumNew).getText(), "复审审核完成");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListApproveResults.get(manNumNew).getText(),
					"审核通过");
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}


	/*
	 * 平台端融资资料审核-复审审核不通过
	 */
	@Test(groups = "FinanceDataApprove-test",enabled = true, priority = 1)
	public void TestFinanceDataManApproveNoPass() throws Exception {
		try {
			// 客服新增
			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			TestBase.biz.financeDataApprove().financeDataAdd();
			TestBase.tearDownBrowser();

			// 初审审核
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);

			int opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 领取任务
			int opeApproveBtnNum = TestBase.biz.financeDataApprove().operatorApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorGetTask(opeApproveBtnNum);
			//搜索初审审核中数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITING);
			opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 点击下拉框对记录进行审核
			TestBase.biz.financeDataApprove().dataListDropDownBtns.get(opeNum).click();
			Thread.sleep(2000);
			TestBase.biz.financeDataApprove().operatorApproveGSZCPass();
			TestBase.biz.financeDataApprove().operatorApproveGRZXPass();
			TestBase.biz.financeDataApprove().operatorApproveQYZXPass();
			TestBase.biz.financeDataApprove().operatorApproveSWHTPass();
			TestBase.biz.financeDataApprove().operatorApproveFPPass();
			TestBase.biz.financeDataApprove().operatorApproveQTPass();
			TestBase.biz.financeDataApprove().operatorApproveZMWJPass();
			Thread.sleep(2000);
			// 确认初审完成
			int opeApproveDoneBtnNum = TestBase.biz.financeDataApprove().operatorApproveDoneBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorApproveDoneBtns.get(opeApproveDoneBtnNum).click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.financeDataApprove().message.getText(), "初审完成");
			TestBase.biz.financeDataApprove().messageConfirmBtn.click();
			Thread.sleep(3000);
			TestBase.tearDownBrowser();

			// 复审审核
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateManagerMobileTeam2, TestBase.operateManagerPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			//搜索状态为初审审核通过的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITED);

			// 领取任务
			int manApproveBtnNum = TestBase.biz.financeDataApprove().managerApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().managerGetTask(manApproveBtnNum);
			TestBase.biz.financeDataApprove().managerApproveGSZCNoPass();
			TestBase.biz.financeDataApprove().managerApproveGRZXNoPass();
			TestBase.biz.financeDataApprove().managerApproveQYZXNoPass();
			TestBase.biz.financeDataApprove().managerApproveQTNoPass();
			TestBase.biz.financeDataApprove().managerApproveZMWJNoPass();
			Thread.sleep(1000);
			TestBase.biz.financeDataApprove().manApproveSubtmitBtn.click();
			Thread.sleep(3000);
			
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端融资资料审核-初审审核不通过-客服修改
	 */
	@Test(groups = "FinanceDataApprove-test",enabled = true, priority = 1)
	public void TestFinanceDataOpeApproveNoPass() throws Exception {
		try {
			// 客服新增
			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			TestBase.biz.financeDataApprove().financeDataAdd();
			TestBase.tearDownBrowser();

			// 初审审核
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,
					TestBase.operateOperatorPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);

			int opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 领取任务
			int opeApproveBtnNum = TestBase.biz.financeDataApprove().operatorApproveBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorGetTask(opeApproveBtnNum);
			//搜索初审审核中数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowFIRST_AUDITING);
			opeNum = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			// 点击下拉框对记录进行审核
			TestBase.biz.financeDataApprove().dataListDropDownBtns.get(opeNum).click();
			Thread.sleep(2000);
			TestBase.biz.financeDataApprove().operatorApproveGSZCNoPass();
			TestBase.biz.financeDataApprove().operatorApproveGRZXNoPass();
			TestBase.biz.financeDataApprove().operatorApproveQYZXNoPass();
			TestBase.biz.financeDataApprove().operatorApproveSWHTPass();
			TestBase.biz.financeDataApprove().operatorApproveFPPass();
			TestBase.biz.financeDataApprove().operatorApproveQTNoPass();
			TestBase.biz.financeDataApprove().operatorApproveZMWJNoPass();
			Thread.sleep(2000);
			// 确认初审完成
			int opeApproveDoneBtnNum = TestBase.biz.financeDataApprove().operatorApproveDoneBtns.size() - 1;
			TestBase.biz.financeDataApprove().operatorApproveDoneBtns.get(opeApproveDoneBtnNum).click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.financeDataApprove().message.getText(), "初审完成");
			TestBase.biz.financeDataApprove().messageConfirmBtn.click();
			Thread.sleep(3000);
			TestBase.tearDownBrowser();
			// 客服修改
			TestBase.setupBiz();
			TestBase.biz.bizLoginPage().login(TestBase.operateQueryMobileTeam2, TestBase.operateQueryPasswordTeam2);
			TestBase.biz.homePage().gotoFinanceDataApprovePage();
			Thread.sleep(3000);
			//搜索状态为待审核的数据
			TestBase.biz.financeDataApprove().getDataByWorkFlow(TestBase.biz.financeDataApprove().workFlowUN_AUDIT);

			int num = TestBase.biz.financeDataApprove().dataListIndexs.size() - 1;
			int modifyNum = TestBase.biz.financeDataApprove().dataLisModifyBtns.size() - 1;
			TestBase.biz.financeDataApprove().dataLisModifyBtns.get(modifyNum).click();
			TestBase.biz.financeDataApprove().financeDataModify();
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListWorkFlows.get(num).getText(), "待审核");
			TestBase.biz.financeDataApprove().dataListDropDownBtns.get(num).click();
			// 重新上传后置空审核结果和不通过原因
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildGSZCOpeResult.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildGSZCOpeReason.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildGRZXOpeResult.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildGRZXOpeReason.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildQYZXOpeResult.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildQYZXOpeReason.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildSWHTOpeResult.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildSWHTOpeReason.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildFPOpeResult.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildFPOpeReason.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildQTOpeResult.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildQTOpeReason.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildZMWJOpeResult.getText(), "");
			Assert.assertEquals(TestBase.biz.financeDataApprove().dataListChildZMWJOpeReason.getText(), "");
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端付款通知书页面白条数量
	 */
	@Test(groups = "RedeemNotice-test",enabled = true, priority = 2)
	public void TestRedeemNoticeCreditNum() throws InterruptedException, SQLException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
			TestBase.biz.homePage().gotoRedeemNoticePage();
			Thread.sleep(4000);
			// 测试全量的白条数量
			TestBase.biz.redeemNoticePage().redeemNoticeCreditNum(null, null, null, null, null);
			Thread.sleep(2000);

			LocalDate today = LocalDate.now();
			LocalDate redeemDateBegin = today.minusDays(20);
			LocalDate redeemDateEnd = today.plusDays(20);
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpName = TestBase.corpNameReceivableTeam2;

			// 测试搜索核心企业的白条数量
			TestBase.biz.redeemNoticePage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditNum(corpNameCore, null, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);

			// 测试搜索链条企业的白条数量
			TestBase.biz.redeemNoticePage().corpNameInput.sendKeys(corpName);
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditNum(null, corpName, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);

			// 测试搜索CREDIT类型白条数量
			TestBase.biz.redeemNoticePage().productType.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().productTypeCredit.click();
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditNum(null, null, "CREDIT", null, null);
			Thread.sleep(2000);

			// 测试搜索RECEIVABLE类型白条数量
			TestBase.biz.redeemNoticePage().productTypeAfterChosed.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().productTypeReceivable.click();
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditNum(null, null, "RECEIVABLE", null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);

			// 测试付款到期日范围内的白条数量
			TestBase.biz.redeemNoticePage().redeemDateBeginInput.sendKeys(redeemDateBegin.toString());
			TestBase.biz.redeemNoticePage().redeemDateEndInput.sendKeys(redeemDateEnd.toString());
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditNum(null, null, null, redeemDateBegin, redeemDateEnd);
			Thread.sleep(2000);
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}

	/*
	 * 平台端付款通知书页面白条list
	 */
	@Test(groups = "RedeemNotice-test",enabled = true, priority = 2)
	public void TestRedeemNoticeCreditList() throws InterruptedException, SQLException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
			TestBase.biz.homePage().gotoRedeemNoticePage();
			Thread.sleep(4000);
			// 测试全量的白条list
			TestBase.biz.redeemNoticePage().redeemNoticeCreditList(null, null, null, null, null);
			Thread.sleep(2000);

			LocalDate today = LocalDate.now();
			LocalDate redeemDateBegin = today.minusDays(25);
			LocalDate redeemDateEnd = today.minusDays(5);
			String corpNameCore = TestBase.corpNameCoreReceivableTeam2;
			String corpName = TestBase.corpNameReceivableTeam2;

			// 测试搜索核心企业的白条list
			TestBase.biz.redeemNoticePage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditList(corpNameCore, null, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);

			// 测试搜索链条企业的白条list
			TestBase.biz.redeemNoticePage().corpNameInput.sendKeys(corpName);
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditList(null, corpName, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);

			// 测试搜索CREDIT类型白条list
			TestBase.biz.redeemNoticePage().productType.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().productTypeCredit.click();
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditList(null, null, "CREDIT", null, null);
			Thread.sleep(2000);

			// 测试搜索RECEIVABLE类型白条list
			TestBase.biz.redeemNoticePage().productTypeAfterChosed.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().productTypeReceivable.click();
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditList(null, null, "RECEIVABLE", null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);

			// 测试付款到期日范围内的白条list
			TestBase.biz.redeemNoticePage().redeemDateBeginInput.sendKeys(redeemDateBegin.toString());
			TestBase.biz.redeemNoticePage().redeemDateEndInput.sendKeys(redeemDateEnd.toString());
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.redeemNoticePage().redeemNoticeCreditList(null, null, null, redeemDateBegin, redeemDateEnd);
			Thread.sleep(2000);
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
	
	/*
	 * 平台端付款通知书生成1208模板并发送邮件（账款）
	 */
	@Test(groups = "RedeemNotice-test",enabled = true, priority = 2)
	public void TestRedeemNotice1208SendEmailReceivable() throws InterruptedException, SQLException {
		try {
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
			TestBase.biz.redeemNoticePage().checkBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().sendEmailBtn.click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().InstructionResult.getText(),"邮件已发送，请查收");
			TestBase.biz.redeemNoticePage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
	
	/*
	 * 平台端付款通知书生成1208模板并发送邮件（融信）
	 */
	@Test(groups = "RedeemNotice-test",enabled = true, priority = 2)
	public void TestRedeemNotice1208SendEmailCredit() throws InterruptedException, SQLException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
			TestBase.biz.homePage().gotoRedeemNoticePage();
			Thread.sleep(4000);
			//搜索一条融信企业
			String corpNameCoreCredit="盛世集团成员二";
			String corpNameCredit="太平链条企业一";
			TestBase.biz.redeemNoticePage().corpNameCoreInput.sendKeys(corpNameCoreCredit);
			TestBase.biz.redeemNoticePage().corpNameInput.sendKeys(corpNameCredit);
			TestBase.biz.redeemNoticePage().productType.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().productTypeCredit.click();
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
			List<Map<String, String>> oracleList=OracleDataFactory.listRedeemCredit(creditList,corpNameCoreCredit,corpNameCredit,"CREDIT",null, null);

			Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowCorpNameCore.getText(), corpNameCoreCredit);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowRedeemAmount.getText().replaceAll(",", ""), oracleList.get(0).get("redeemAmount"));
			TestBase.biz.redeemNoticePage().selectAcountWindowConfirmBtn.click();
			Thread.sleep(3000);
			TestBase.biz.redeemNoticePage().checkBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().sendEmailBtn.click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().InstructionResult.getText(),"邮件已发送，请查收");
			TestBase.biz.redeemNoticePage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
	
	/*
	 * 平台端付款通知书生成1208模板并发送邮件（批量）
	 */
	@Test(groups = "RedeemNotice-test",enabled = true, priority = 2)
	public void TestRedeemNotice1208SendEmailBatch() throws InterruptedException, SQLException {
		try {
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
			TestBase.biz.redeemNoticePage().redeemListCheckBoxs.get(1).click();
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
			Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowRedeemAmount.getText().replaceAll(",", ""), new BigDecimal(oracleList.get(0).get("redeemAmount")).add(new BigDecimal(oracleList.get(1).get("redeemAmount"))).toString());
			TestBase.biz.redeemNoticePage().selectAcountWindowConfirmBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().checkBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().sendEmailBtn.click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().InstructionResult.getText(),"邮件未发送，请查收");
			TestBase.biz.redeemNoticePage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
	
	/*
	 * 平台端付款通知书生成026模板并发送邮件(账款)
	 */
	@Test(groups = "RedeemNotice-test",enabled = true, priority = 2)
	public void TestRedeemNotice206SendEmailReceivable() throws InterruptedException, SQLException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
			TestBase.biz.homePage().gotoRedeemNoticePage();
			Thread.sleep(4000);
			ArrayList<String> creditList=new ArrayList<String>();
			creditList.add("ISD");
			creditList.add("RD0");
			creditList.add("RD1");
			creditList.add("RD9");
			//获取第一级子条是已融资状态的条列表
			List<Map<String, String>> oracleList=OracleDataFactory.listRedeemCreditFinanced(creditList,TestBase.corpNameCoreReceivableTeam2,TestBase.corpNameReceivableTeam2,"RECEIVABLE",null, null);

			//搜索一条账款企业
			TestBase.biz.redeemNoticePage().corpNameCoreInput.sendKeys(TestBase.corpNameCoreReceivableTeam2);
			TestBase.biz.redeemNoticePage().corpNameInput.sendKeys(TestBase.corpNameReceivableTeam2);
			TestBase.biz.redeemNoticePage().productType.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().productTypeReceivable.click();
			TestBase.biz.redeemNoticePage().redeemDateBeginInput.sendKeys(oracleList.get(0).get("redeemDate"));
			TestBase.biz.redeemNoticePage().redeemDateEndInput.sendKeys(oracleList.get(0).get("redeemDate"));
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			
			//获取有一级融资条的根条并执行选择
			int index=-1;
			for(int i=0;i<TestBase.biz.redeemNoticePage().getLastPageNum();i++){
				index=TestBase.biz.redeemNoticePage().getIndexByPkCreditOnePage(oracleList.get(0).get("pkCredit"),10);
				if(TestBase.biz.redeemNoticePage().getIndexByPkCreditOnePage(oracleList.get(0).get("pkCredit"),10)==-1){
					TestBase.biz.redeemNoticePage().nextPage.click();
					Thread.sleep(4000);
				}
			}
			TestBase.biz.redeemNoticePage().redeemListCheckBoxs.get(index).click();
			TestBase.biz.redeemNoticePage().generateNoticeBtn.click();
			Thread.sleep(2000);

			Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowCorpNameCore.getText(), TestBase.corpNameCoreReceivableTeam2);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowRedeemAmount.getText().replaceAll(",", ""), oracleList.get(0).get("redeemAmount"));
			TestBase.biz.redeemNoticePage().selectAcountWindow026Acount.click();
			TestBase.biz.redeemNoticePage().selectAcountWindowConfirmBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().checkBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().sendEmailBtn.click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().InstructionResult.getText(),"邮件未发送，请查收");
			TestBase.biz.redeemNoticePage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
	
	/*
	 * 平台端付款通知书生成026模板并发送邮件(融信)
	 */
	@Test(groups = "RedeemNotice-test",enabled = true, priority = 2)
	public void TestRedeemNotice206SendEmailCredit() throws InterruptedException, SQLException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.productManagerMobileTeam2, TestBase.productManagerPasswordTeam2);
			TestBase.biz.homePage().gotoRedeemNoticePage();
			Thread.sleep(4000);
			String corpNameCoreCredit="盛世集团成员二";
			String corpNameCredit="太平链条企业一";
			
			ArrayList<String> creditList=new ArrayList<String>();
			creditList.add("ISD");
			creditList.add("RD0");
			creditList.add("RD1");
			creditList.add("RD9");
			
			//获取第一级子条是已融资状态的条列表
			List<Map<String, String>> oracleList=OracleDataFactory.listRedeemCreditFinanced(creditList,corpNameCoreCredit,corpNameCredit,"CREDIT",null, null);

			//搜索一条融信企业
			TestBase.biz.redeemNoticePage().corpNameCoreInput.sendKeys(corpNameCoreCredit);
			TestBase.biz.redeemNoticePage().corpNameInput.sendKeys(corpNameCredit);
			TestBase.biz.redeemNoticePage().productType.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().productTypeCredit.click();
			Thread.sleep(1000);
			TestBase.biz.redeemNoticePage().redeemDateBeginInput.sendKeys(oracleList.get(0).get("redeemDate"));
			TestBase.biz.redeemNoticePage().redeemDateEndInput.sendKeys(oracleList.get(0).get("redeemDate"));
			TestBase.biz.redeemNoticePage().searchBtn.click();
			Thread.sleep(4000);
			
			//获取有一级融资条的根条并执行选择
			int index=-1;
			for(int i=0;i<TestBase.biz.redeemNoticePage().getLastPageNum();i++){
				index=TestBase.biz.redeemNoticePage().getIndexByPkCreditOnePage(oracleList.get(0).get("pkCredit"),10);
				if(TestBase.biz.redeemNoticePage().getIndexByPkCreditOnePage(oracleList.get(0).get("pkCredit"),10)==-1){
					TestBase.biz.redeemNoticePage().nextPage.click();
					Thread.sleep(4000);
				}
			}
			TestBase.biz.redeemNoticePage().redeemListCheckBoxs.get(index).click();
			TestBase.biz.redeemNoticePage().generateNoticeBtn.click();
			Thread.sleep(2000);

			Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowCorpNameCore.getText(), corpNameCoreCredit);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().selectAcountWindowRedeemAmount.getText().replaceAll(",", ""), oracleList.get(0).get("redeemAmount"));
			TestBase.biz.redeemNoticePage().selectAcountWindow026Acount.click();
			TestBase.biz.redeemNoticePage().selectAcountWindowConfirmBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().checkBtn.click();
			Thread.sleep(2000);
			TestBase.biz.redeemNoticePage().sendEmailBtn.click();
			Thread.sleep(2000);
			Assert.assertEquals(TestBase.biz.redeemNoticePage().InstructionResult.getText(),"邮件已发送，请查收");
			TestBase.biz.redeemNoticePage().InstructionWindowConfirmBtn.click();
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
		
	/*
	 * 代发工资--代发工资申请代发申请中查询
	 */
	@Test(groups = "PayrollCreditApply-test",enabled = true, priority = 2)
	public void TestPayrollCreditApplySearchPage() throws InterruptedException, SQLException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2, TestBase.operateManagerPasswordTeam2);
			TestBase.biz.homePage().gotoPayrollCreditApplySearchPage();
			Thread.sleep(4000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, null, null, null, null);
			
			String corpNameCore="盛世集团成员二";
			String corpName="太平链条企业一";
			LocalDate today = LocalDate.now();
			LocalDate createDateBegin = today.minusDays(25);
			LocalDate createDateEnd = today.minusDays(5);
			//核心企业筛选
			TestBase.biz.payrollCreditApplySearchPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(corpNameCore, null, null, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			//链条企业筛选
			TestBase.biz.payrollCreditApplySearchPage().corpNameInput.sendKeys(corpName);
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, corpName, null, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			//测试合作平台筛选
			TestBase.biz.payrollCreditApplySearchPage().partnerNameInput.sendKeys("建信融通");
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, "建信融通", null, null, null);
			TestBase.biz.payrollCreditApplySearchPage().partnerNameInput.clear();
			TestBase.biz.payrollCreditApplySearchPage().partnerNameInput.sendKeys("开太平");
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, "开太平", null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			//创建开始时间，结束时间筛选
			TestBase.biz.payrollCreditApplySearchPage().createDateBeginInput.sendKeys(createDateBegin.toString());
			TestBase.biz.payrollCreditApplySearchPage().createDateEndInput.sendKeys(createDateEnd.toString());
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, null, createDateBegin, createDateEnd, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			
			//状态筛选
			TestBase.biz.payrollCreditApplySearchPage().state.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().stateINCREASING.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, null, null, null, "新增中");
			Thread.sleep(2000);
			
			TestBase.biz.payrollCreditApplySearchPage().state.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().stateINCREASE_FAIL.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, null, null, null, "新增失败");
			Thread.sleep(2000);
			
			TestBase.biz.payrollCreditApplySearchPage().state.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().stateSUBMITTING.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, null, null, null, "待提交");
			Thread.sleep(2000);
			
			TestBase.biz.payrollCreditApplySearchPage().state.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().stateCOMMITTING.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, null, null, null, "待确认");
			Thread.sleep(2000);
			
			TestBase.biz.payrollCreditApplySearchPage().state.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().stateAPPROVE_FAIL.click();
			Thread.sleep(1000);
			TestBase.biz.payrollCreditApplySearchPage().searchBtn.click();
			Thread.sleep(4000);
			TestBase.biz.payrollCreditApplySearchPage().payrollCreditList(null, null, null, null, null, "核心企业审核不通过");
			Thread.sleep(2000);
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}	
	}
	
	/*
	 * 代发工资--代发工资申请签发中查询
	 */
	@Test(groups = "PayrollCreditApply-test",enabled = true, priority = 2)
	public void TestPayrollCreditApplySearchIssuingTabPage() throws InterruptedException, SQLException {
		try {
			TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2, TestBase.operateManagerPasswordTeam2);
			TestBase.biz.homePage().gotoPayrollCreditApplySearchPage();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().issuingTab);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollCreditIssuingList(null, null, null, null, null, null);
			
			String corpNameCore="盛世集团成员二";
			String corpName="太平链条企业一";
			LocalDate today = LocalDate.now();
			LocalDate submitTimeBegin = today.minusDays(30);
			LocalDate submitTimeEnd = today;
			//核心企业筛选
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().corpNameCoreInput.sendKeys(corpNameCore);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollCreditIssuingList(corpNameCore, null, null, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().issuingTab);

			//链条企业筛选
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().corpNameInput.sendKeys(corpName);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollCreditIssuingList(null, corpName, null, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().issuingTab);

			//白条编号筛选
			String pkCredit=TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollListPkCredits.get(1).getText();
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().pkCreditInput.sendKeys(pkCredit);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollCreditIssuingList(null, null, pkCredit, null, null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().issuingTab);

			//测试合作平台筛选
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().partnerNameInput.sendKeys("建信融通");
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollCreditIssuingList(null, null, null, "建信融通", null, null);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().partnerNameInput.clear();
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().partnerNameInput.sendKeys("开太平");
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollCreditIssuingList(null, null, null, "开太平", null, null);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().issuingTab);

			//提交申请开始时间，结束时间筛选
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().submitTimeBeginInput.sendKeys(submitTimeBegin.toString());
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().submitTimeEndInput.sendKeys(submitTimeEnd.toString());
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().searchBtn.click();
			Thread.sleep(2000);
			TestBase.biz.payrollCreditApplySearchIssuingTabPage().payrollCreditIssuingList(null, null, null,null, submitTimeBegin, submitTimeEnd);
			TestBase.driver.navigate().refresh();
			Thread.sleep(2000);
			
		} catch (Exception e) {
			String cls = this.getClass().getName();
			String method = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(cls + "--" + method);
			TestBase.getScreenShot(cls, method);
			Thread.sleep(1000);
			throw e;
		}
	}
		
		/*
		 * 代发工资--代发工资申请融资处理中查询
		 */
		@Test(groups = "PayrollCreditApply-test",enabled = true, priority = 2)
		public void TestPayrollCreditApplySearchFinancingTabPage() throws InterruptedException, SQLException {
			try {
				TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2, TestBase.operateManagerPasswordTeam2);
				TestBase.biz.homePage().gotoPayrollCreditApplySearchPage();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().financingTab);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, null, null, null, null,null);

				String corpNameCore="代发工资核心企业杨桢桢";
				String corpName="代发工资链条企业杨桢桢";
				LocalDate today = LocalDate.now();
				LocalDate financeApplyDateBegin = today.minusDays(30);
				LocalDate financeApplyDateEnd = today;
				//核心企业筛选
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().corpNameCoreInput.sendKeys(corpNameCore);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(corpNameCore, null, null, null, null, null,null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().financingTab);

				//链条企业筛选
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().corpNameInput.sendKeys(corpName);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, corpName, null, null, null, null,null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().financingTab);

				//白条编号筛选
				String pkCredit=TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollListPkCredits.get(1).getText();
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().pkCreditInput.sendKeys(pkCredit);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, pkCredit, null, null, null,null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().financingTab);

				//测试合作平台筛选
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().partnerNameInput.sendKeys("建信融通");
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, null, "建信融通", null, null,null);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().partnerNameInput.clear();
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().partnerNameInput.sendKeys("开太平");
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, null, "开太平", null, null,null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().financingTab);

				//融资申请申请开始时间，结束时间筛选
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().financeApplyDateBeginInput.sendKeys(financeApplyDateBegin.toString());
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().financeApplyDateEndInput.sendKeys(financeApplyDateEnd.toString());
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, null,null, financeApplyDateBegin, financeApplyDateEnd,null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().financingTab);

				//状态筛选
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().creditState.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().creditStateFI1.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(4000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, null, null, null, null,"FI1");
				Thread.sleep(2000);
				
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().creditState.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().creditStateFI2.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(4000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, null, null, null, null,"FI2");
				Thread.sleep(2000);
				
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().creditState.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().creditStateFI3.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().searchBtn.click();
				Thread.sleep(4000);
				TestBase.biz.payrollCreditApplySearchFinancingTabPage().payrollCreditFinancingList(null, null, null, null, null, null,"FI3");
				Thread.sleep(2000);
				
			} catch (Exception e) {
				String cls = this.getClass().getName();
				String method = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println(cls + "--" + method);
				TestBase.getScreenShot(cls, method);
				Thread.sleep(1000);
				throw e;
			}	
	}
		
		/*
		 * 代发工资--代发工资申请已放款查询
		 */
		@Test(groups = "PayrollCreditApply-test",enabled = true, priority = 2)
		public void TestPayrollCreditApplySearchLoanedTabPage() throws Exception {
			try {
				TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2, TestBase.operateManagerPasswordTeam2);
				TestBase.biz.homePage().gotoPayrollCreditApplySearchPage();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null, null, null, null, null,null, null);

				String corpNameCore="代发工资核心企业杨桢桢";
				String corpName="代发工资链条企业杨桢桢";
				LocalDate today = LocalDate.now();
				LocalDate createDateBegin = today.minusDays(30);
				LocalDate createDateEnd = today.minusDays(5);
				LocalDate loanTimeBegin = today.minusDays(25);
				LocalDate loanTimeEnd = today;
				//核心企业筛选
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().corpNameCoreInput.sendKeys(corpNameCore);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(corpNameCore, null, null, null, null, null,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);

				//链条企业筛选
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().corpNameInput.sendKeys(corpName);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, corpName, null, null, null, null,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);

				//状态筛选
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollState.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollStatePass.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(4000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null,"成功", null, null, null,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);
				
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollState.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollStateFail.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(4000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null,"失败", null, null, null,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);

				
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollState.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollStateDoing.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(4000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null,"处理中", null, null, null,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);
				
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollState.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollStateUndo.click();
				Thread.sleep(1000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(4000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null,"待处理", null, null, null,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);

				
				//白条编号筛选
				String pkCredit=TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollListPkCredits.get(1).getText();
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().pkCreditInput.sendKeys(pkCredit);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null, null, pkCredit, null, null,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);


				//开始时间，结束时间筛选
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().createDateBeginInput.sendKeys(createDateBegin.toString());
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().createDateEndInput.sendKeys(createDateEnd.toString());
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null, null,null, createDateBegin, createDateEnd,null, null);
				TestBase.driver.navigate().refresh();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchPage().changeTab(TestBase.biz.payrollCreditApplySearchPage().loanedTab);

				//开始时间，结束时间筛选
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().loanTimeBeginInput.sendKeys(loanTimeBegin.toString());
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().loanTimeEndInput.sendKeys(loanTimeEnd.toString());
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().searchBtn.click();
				Thread.sleep(2000);
				TestBase.biz.payrollCreditApplySearchLoanedTabPage().payrollCreditLoanedList(null, null, null,null, null, null,loanTimeBegin, loanTimeEnd);
				
			} catch (Exception e) {
				String cls = this.getClass().getName();
				String method = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println(cls + "--" + method);
				TestBase.getScreenShot(cls, method);
				Thread.sleep(1000);
				throw e;
			}	
	}
}
