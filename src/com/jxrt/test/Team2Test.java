package com.jxrt.test;

import java.time.LocalDate;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Team2Test extends TestBase {

	@BeforeClass
	public void setup_Corp(){
		setupBiz();

	}
	
	@AfterClass
	public void tearDown_Corp() {
//		tearDownBiz();
	}
	
	@Test
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
		//断言
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListCore.getText(), corpNameCore);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListLimitSource.getText(), corpNameCore);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListProductType.getText(), "e点通");
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListCorpNameAccept.getText(), corpNameAccept);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListBusiContractCode.getText(), busiContractCode);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListApplyAmount.getText(), applyAmount+".00");
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListMaturityDate.getText(), maturityDate);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListAbstract_.getText(), abstract_);
		Assert.assertNotNull(TestBase.biz.receivableIssuePage().receivableListVerifyPass);
		//上传文件
		try{ 
			Runtime.getRuntime().exec("D:\\autoit\\uploadFile.exe");

			}catch(Exception e){ 
				e.printStackTrace();
			} 
		TestBase.biz.receivableIssuePage().receivableListUploadBtn.click();
		Thread.sleep(8000);
		//断言
		Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(), "上传成功");
		TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();
		Thread.sleep(2000);
		//勾选记录并点击提交
		TestBase.biz.receivableIssuePage().receivableListCheckBox.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",TestBase.biz.receivableIssuePage().receivableListCore);
		TestBase.biz.receivableIssuePage().SubmitBtn.click();
		//断言
		Thread.sleep(2000);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().InstructionResult.getText(), "提交成功！请等待主管审核，您可以在账款查询中查看进度!");
		TestBase.biz.receivableIssuePage().InstructionWindowConfirmBtn.click();
	}
}
