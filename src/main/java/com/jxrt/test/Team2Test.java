package com.jxrt.test;

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
		TestBase.biz.bizLoginPage().login("17710253335","111111");
		TestBase.biz.homePage().gotoReceivableIssuePage();
		TestBase.biz.receivableIssuePage().ReceivableIssue();
	}
}
