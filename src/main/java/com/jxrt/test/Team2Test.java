package com.jxrt.test;

import java.time.LocalDate;
import java.util.Random;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jxrt.biz.page.ReceivableIssuePage;

import junit.framework.Assert;

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
		TestBase.biz.bizLoginPage().login(TestBase.operateOperatorMobileTeam2,TestBase.operateOperatorPasswordTeam2);
		TestBase.biz.homePage().gotoReceivableIssuePage();
		String corpNameCore=TestBase.corpNameCoreReceivableTeam2;
		String corpNameAccept=TestBase.corpNameReceivableTeam2;
		int rand=new Random().nextInt(1000);
		String busiContractCode="sw"+rand;
		String applyAmount=String.valueOf(rand);
		LocalDate today=LocalDate.now();
		String maturityDate=today.plusDays(30).toString();
		String abstract_="zy"+rand;
		
		TestBase.biz.receivableIssuePage().ReceivableIssue(corpNameCore,corpNameAccept,
				busiContractCode,applyAmount,maturityDate,abstract_);
		Thread.sleep(3000);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListCore.getText(), corpNameCore);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListLimitSource.getText(), corpNameCore);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListProductType.getText(), "e点通");
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListCorpNameAccept.getText(), corpNameAccept);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListBusiContractCode.getText(), busiContractCode);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListApplyAmount.getText(), applyAmount+".00");
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListMaturityDate.getText(), maturityDate);
		Assert.assertEquals(TestBase.biz.receivableIssuePage().receivableListAbstract_.getText(), abstract_);
		Assert.assertNotNull(TestBase.biz.receivableIssuePage().receivableListVerifyPass);
	}
}
