package com.jxrt.corp.page;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.jxrt.biz.page.ReceivableApprovePage;
import com.jxrt.test.TestBase;

import net.sf.json.JSONObject;

public class HomePage  extends AbstractPage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	//账款融资tab
	@FindBy(xpath="//span[@class='level-1' and text()='账款融资']")
	private WebElement receivableTab;
	//账款申请tab
	@FindBy(xpath="//dd[@class='menu-element']//a[text()='账款申请']")
	private WebElement receivableApplyTab;
	//账款新增tab
	@FindBy(xpath="//dd[@class='menu-element']//a[text()='账款新增']")
	private WebElement receivableIssueTab;
	//账款审核tab
	@FindBy(xpath="//dd[@class='menu-element']//a[text()='账款审核']")
	private WebElement receivableApproveTab;
	//账款查询tab
	@FindBy(xpath="//dd[@class='menu-element']//a[text()='账款查询']")
	private WebElement receivableSearchTab;
	
	/*
	 * 进入账款签发页面
	 */
	public void gotoReceivableIssueTab() throws InterruptedException{
	    receivableTab.click();
		Thread.sleep(1000);
		receivableIssueTab.click();
		Thread.sleep(2000);
	}
	
	/*
	 * 进入账款申请页面
	 */
	public void gotoReceivableApplyTab() throws InterruptedException{
	    receivableTab.click();
		Thread.sleep(1000);
		receivableApplyTab.click();
		Thread.sleep(2000);
	}
	
	/*
	 * 进入账款审核页面
	 */
	public void gotoReceivableApproveTab() throws InterruptedException{
	    receivableTab.click();
		Thread.sleep(1000);
		receivableApproveTab.click();
		Thread.sleep(2000);
	}
	
	/*
	 * 进入账款查询页面
	 */
	public void gotoReceivableSearchTab() throws InterruptedException{
	    receivableTab.click();
		Thread.sleep(1000);
		receivableSearchTab.click();
		Thread.sleep(2000);
	}
	
	public static void main(String[] args) throws InterruptedException {
		String rs="{\"code\":\"0\",\"message\":\"成功\",\"data\":{\"datalist\":[{\"pkCreditDocDatum\":\"b4767e72-5c74-4ada-bcb3-10f54a29e9bd\",\"fkCorpFinance\":\"f1cfbdc2-78c7-4b84-b2b8-5623cf5ee01f\",\"corpNameFinance\":\"太平链条企业一\",\"fkCorpCore\":\"ff6aed09-4570-4072-bf79-ead6fe76266f\",\"corpNameCore\":\"盛世集团成员二\",\"fkFinance\":\"df599b56-a268-4186-9075-080640d7a5b2\",\"financeName\":\"一二一二测试金融机构零一\",\"fkProductCcbscf\":\"dddb12cb-d4c9-418c-8f46-a798c0a5131d\",\"productTypeCcbscf\":\"CREDIT\",\"financeApplyAmount\":123,\"isUrgent\":false,\"isStateOwned\":false,\"taskSegment\":\"CUSTOMER\",\"fkCreditDocStd\":\"c2f4555c-bd9a-48bd-8dae-f2d395c24bdf\",\"datumApproveState\":\"UN_AUDIT\",\"datumApproveResult\":null,\"managerMail\":null,\"createTime\":1547117488000,\"fkUserCreate\":\"74ddbca1-64b5-490b-8dfd-9d99b6e8939d\",\"userNameCreate\":\"黄云波\",\"updateTime\":1547117488000,\"fkUserUpdate\":\"74ddbca1-64b5-490b-8dfd-9d99b6e8939d\",\"userNameUpdate\":\"黄云波\",\"deleteTime\":null,\"fkUserDelete\":null,\"userNameDelete\":null,\"auditTime\":null,\"fkUserAudit\":null,\"userNameAudit\":null,\"reviewTime\":null,\"fkUserReview\":null,\"userNameReview\":null,\"enableModify\":true,\"enableAudit\":true,\"enableReview\":null,\"enableConfirm\":null,\"enableDownload\":null,\"enableRelation\":null,\"creditDocAuditBOList\":null,\"abstract\":null,\"createTimeString\":\"2019-01-10 18:51:28\",\"auditTimeString\":null,\"reviewTimeString\":null,\"updateTimeString\":\"2019-01-10 18:51:28\"},{\"pkCreditDocDatum\":\"e43494ad-1eaa-4615-be1e-00483d830511\",\"fkCorpFinance\":\"f1cfbdc2-78c7-4b84-b2b8-5623cf5ee01f\",\"corpNameFinance\":\"太平链条企业一\",\"fkCorpCore\":\"ff6aed09-4570-4072-bf79-ead6fe76266f\",\"corpNameCore\":\"盛世集团成员二\",\"fkFinance\":\"df599b56-a268-4186-9075-080640d7a5b2\",\"financeName\":\"一二一二测试金融机构零一\",\"fkProductCcbscf\":\"dddb12cb-d4c9-418c-8f46-a798c0a5131d\",\"productTypeCcbscf\":\"CREDIT\",\"financeApplyAmount\":123,\"isUrgent\":false,\"isStateOwned\":false,\"taskSegment\":\"CUSTOMER\",\"fkCreditDocStd\":\"c2f4555c-bd9a-48bd-8dae-f2d395c24bdf\",\"datumApproveState\":\"UN_AUDIT\",\"datumApproveResult\":null,\"managerMail\":null,\"createTime\":1547117502000,\"fkUserCreate\":\"74ddbca1-64b5-490b-8dfd-9d99b6e8939d\",\"userNameCreate\":\"黄云波\",\"updateTime\":1547117502000,\"fkUserUpdate\":\"74ddbca1-64b5-490b-8dfd-9d99b6e8939d\",\"userNameUpdate\":\"黄云波\",\"deleteTime\":null,\"fkUserDelete\":null,\"userNameDelete\":null,\"auditTime\":null,\"fkUserAudit\":null,\"userNameAudit\":null,\"reviewTime\":null,\"fkUserReview\":null,\"userNameReview\":null,\"enableModify\":true,\"enableAudit\":true,\"enableReview\":null,\"enableConfirm\":null,\"enableDownload\":null,\"enableRelation\":null,\"creditDocAuditBOList\":null,\"abstract\":null,\"createTimeString\":\"2019-01-10 18:51:42\",\"auditTimeString\":null,\"reviewTimeString\":null,\"updateTimeString\":\"2019-01-10 18:51:42\"},{\"pkCreditDocDatum\":\"1a3dfd3b-8cf5-4de4-a9df-16fad6e0b65e\",\"fkCorpFinance\":\"8e68894d-b264-4ece-ab94-cab9c02e9784\",\"corpNameFinance\":\"贵州绿色高粱厂\",\"fkCorpCore\":\"b4fb12f7-77d6-4247-90ff-ba5b06ff39c7\",\"corpNameCore\":\"大鲨鱼\",\"fkFinance\":\"5cedecfb-2a35-44db-9e30-e34753ad039b\",\"financeName\":\"1024银行\",\"fkProductCcbscf\":\"dddb12cb-d4c9-418c-8f46-a798c0a5131d\",\"productTypeCcbscf\":\"CREDIT\",\"financeApplyAmount\":null,\"isUrgent\":false,\"isStateOwned\":false,\"taskSegment\":\"CUSTOMER\",\"fkCreditDocStd\":\"1b0a6537-5d2d-41ab-9021-0177d392054f\",\"datumApproveState\":\"UN_AUDIT\",\"datumApproveResult\":null,\"managerMail\":null,\"createTime\":1547118452000,\"fkUserCreate\":\"a7a0a6d0-bcc6-48ca-99d1-31dfa5dee1e3\",\"userNameCreate\":\"王芸\",\"updateTime\":1547118452000,\"fkUserUpdate\":\"a7a0a6d0-bcc6-48ca-99d1-31dfa5dee1e3\",\"userNameUpdate\":\"王芸\",\"deleteTime\":null,\"fkUserDelete\":null,\"userNameDelete\":null,\"auditTime\":null,\"fkUserAudit\":null,\"userNameAudit\":null,\"reviewTime\":null,\"fkUserReview\":null,\"userNameReview\":null,\"enableModify\":null,\"enableAudit\":true,\"enableReview\":null,\"enableConfirm\":null,\"enableDownload\":null,\"enableRelation\":null,\"creditDocAuditBOList\":null,\"abstract\":null,\"createTimeString\":\"2019-01-10 19:07:32\",\"auditTimeString\":null,\"reviewTimeString\":null,\"updateTimeString\":\"2019-01-10 19:07:32\"},{\"pkCreditDocDatum\":\"7a3d8403-1897-4273-b43b-51f842edb99c\",\"fkCorpFinance\":\"f1cfbdc2-78c7-4b84-b2b8-5623cf5ee01f\",\"corpNameFinance\":\"太平链条企业一\",\"fkCorpCore\":\"ff6aed09-4570-4072-bf79-ead6fe76266f\",\"corpNameCore\":\"盛世集团成员二\",\"fkFinance\":\"df599b56-a268-4186-9075-080640d7a5b2\",\"financeName\":\"一二一二测试金融机构零一\",\"fkProductCcbscf\":\"dddb12cb-d4c9-418c-8f46-a798c0a5131d\",\"productTypeCcbscf\":\"CREDIT\",\"financeApplyAmount\":123,\"isUrgent\":false,\"isStateOwned\":false,\"taskSegment\":\"CUSTOMER\",\"fkCreditDocStd\":\"c2f4555c-bd9a-48bd-8dae-f2d395c24bdf\",\"datumApproveState\":\"UN_AUDIT\",\"datumApproveResult\":null,\"managerMail\":null,\"createTime\":1547118582000,\"fkUserCreate\":\"74ddbca1-64b5-490b-8dfd-9d99b6e8939d\",\"userNameCreate\":\"黄云波\",\"updateTime\":1547118582000,\"fkUserUpdate\":\"74ddbca1-64b5-490b-8dfd-9d99b6e8939d\",\"userNameUpdate\":\"黄云波\",\"deleteTime\":null,\"fkUserDelete\":null,\"userNameDelete\":null,\"auditTime\":null,\"fkUserAudit\":null,\"userNameAudit\":null,\"reviewTime\":null,\"fkUserReview\":null,\"userNameReview\":null,\"enableModify\":true,\"enableAudit\":true,\"enableReview\":null,\"enableConfirm\":null,\"enableDownload\":null,\"enableRelation\":null,\"creditDocAuditBOList\":null,\"abstract\":null,\"createTimeString\":\"2019-01-10 19:09:42\",\"auditTimeString\":null,\"reviewTimeString\":null,\"updateTimeString\":\"2019-01-10 19:09:42\"}],\"pagecond\":{\"page\":12,\"pageSize\":10,\"totalPages\":12,\"count\":114,\"needCount\":true},\"subTotal\":[],\"grandTotal\":[],\"sumPage\":null,\"sumTotal\":0,\"loanAmountPage\":null,\"loanAmountTotal\":null,\"loanClearAmountPage\":null,\"loanClearAmountTotal\":null,\"unCheckNum\":null}}";
		String response=rs;
		//System.out.println(response);
		JSONObject jsonObject=JSONObject.fromObject(response);
		JSONObject data=jsonObject.getJSONObject("data");
//		System.out.println(data);
		List datalist=data.getJSONArray("datalist");

		List pkCreditDocDatumList=new ArrayList();
		String pkCreditDocDatum=null;
		for(int i=0;i<datalist.size();i++){
			pkCreditDocDatum=((JSONObject) datalist.get(i)).getString("pkCreditDocDatum");
			pkCreditDocDatumList.add(pkCreditDocDatum);
		}
		System.out.println((JSONObject) datalist.get(datalist.size()-1));
		List creditDocAuditBOList=((JSONObject) datalist.get(datalist.size()-1)).getJSONArray("creditDocAuditBOList");
		List pkCreditDocAuditList=new ArrayList();
		List creditDocCategoryNameList=new ArrayList();
		String pkCreditDocAudit=null;
		String creditDocCategoryName=null;
		for(int i=0;i<creditDocAuditBOList.size();i++){
			pkCreditDocAudit=((JSONObject) creditDocAuditBOList.get(i)).getString("pkCreditDocAudit");
			creditDocCategoryName=((JSONObject) creditDocAuditBOList.get(i)).getString("creditDocCategoryName");
			pkCreditDocAuditList.add(pkCreditDocAudit);
			creditDocCategoryNameList.add(creditDocCategoryName);
			if(creditDocCategoryName.equals("公司章程")){
				//vars.put("pkCreditDocAuditGSZC",pkCreditDocAudit.toString());
				System.out.println(pkCreditDocAudit.toString());
			}
			if(creditDocCategoryName.equals("个人征信授权书")){
				//vars.put("pkCreditDocAuditGRZX",pkCreditDocAudit.toString());
				System.out.println(pkCreditDocAudit.toString());
			}
			if(creditDocCategoryName.equals("关于同意查询和报送信用信息的函")){
				//vars.put("pkCreditDocAuditQYZX",pkCreditDocAudit.toString());
				System.out.println(pkCreditDocAudit.toString());
			}
			if(creditDocCategoryName.equals("商务合同")){
				//vars.put("pkCreditDocAuditSWHT",pkCreditDocAudit.toString());
				System.out.println(pkCreditDocAudit.toString());
			}
			if(creditDocCategoryName.equals("发票")){
				//vars.put("pkCreditDocAuditFP",pkCreditDocAudit.toString());
				System.out.println(pkCreditDocAudit.toString());
			}
			if(creditDocCategoryName.equals("其它1")){
				//vars.put("pkCreditDocAuditQT",pkCreditDocAudit.toString());
				System.out.println(pkCreditDocAudit.toString());
			}
			if(creditDocCategoryName.equals("经营异常证明文件")){
				//vars.put("pkCreditDocAuditJYYC",pkCreditDocAudit.toString());
				System.out.println(pkCreditDocAudit.toString());
			}
		}
		
	}
}
