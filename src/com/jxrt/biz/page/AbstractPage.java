package com.jxrt.biz.page;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.jxrt.test.TestBase;


public abstract class AbstractPage {
    protected final WebDriver driver;
	//提示框
	@FindBy(className="ivu-modal-content")
	public WebElement InstructionWindow;
	//关闭按钮
	@FindBy(xpath="//div[contains(text(), '提示信息')]/../../a/i")
	public WebElement InstructionWindowCloseBtn;
	//提示框结果
	@FindBy(xpath="//div[contains(text(), '提示信息')]/../../div[2]")
	public WebElement InstructionResult;
	//提示框确认按钮
	@FindBy(xpath="//div[contains(text(), '提示信息')]/../../descendant::button/span[contains(text(), '确认')]")
	public WebElement InstructionWindowConfirmBtn;

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
     * 移动元素实现滑动
     */
    public void moveToElement(WebElement source,WebElement target){
    	Actions action = new Actions(TestBase.driver);
    	action.dragAndDrop(source, target).perform();
    }
    /*
     * 页面输入特殊按钮n次，实现滚动功能
     */
    public void sendKeysToPage(Keys key,int n){
    	Actions action = new Actions(TestBase.driver);
    	for(int i=1;i<=n;i++){
        	action.sendKeys(key);
    	}
    	action.perform();
    }

//  
//	
//    protected void takeScreenShot(){
//        driver.getScreenshotAs(OutputType.BASE64);
//    }
}