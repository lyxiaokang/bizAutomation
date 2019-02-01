package com.jxrt.corp.page;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.jxrt.test.TestBase;


public abstract class AbstractPage {
    protected final WebDriver driver;
	//页码
	@FindBy(xpath="//ul[@class='ivu-page mini']/li[@class='ivu-page-item' or @class='ivu-page-item ivu-page-item-active']/a")
	public List<WebElement> pageNums;
	//活动页码
	@FindBy(xpath="//ul[@class='ivu-page mini']/li[@class='ivu-page-item ivu-page-item-active']/a")
	public WebElement activePageNum;
	//上一页按钮
	@FindBy(xpath="//ul[@class='ivu-page mini']/li[contains(@class,'ivu-page-prev')]")
	public WebElement prevPageNum;
	//下一页按钮
	@FindBy(xpath="//ul[@class='ivu-page mini']/li[contains(@class,'ivu-page-next')]")
	public WebElement nextPageNum;
	
	/*
	 * 获取最后一页页码
	 */
	public int getLastPageNum(){
		return Integer.parseInt(pageNums.get(pageNums.size()-1).getText());
	}
	/*
	 * 获取最后一页元素
	 */
	public WebElement getLastPageElement(){
		return pageNums.get(pageNums.size()-1);
	}

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public void closeBrowser(){
    	driver.close();
    }
    /*
     * 通过js脚本设置元素底色和边框
     */
    public void setElementColor(WebElement element){
    	JavascriptExecutor js= (JavascriptExecutor)TestBase.driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",element);
    }
    /*
     * 通过js脚本滑动至元素可见
     */
    public void scrollIntoView(WebElement element){
    	JavascriptExecutor js= (JavascriptExecutor)TestBase.driver;
    	js.executeScript("arguments[0].scrollIntoView(true);",element);
    }
    
	/*
	 * 上传文件
	 */
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
		Thread.sleep(5000);
	}
}