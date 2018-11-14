package com.jxrt.biz.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.jxrt.test.TestBase;

public abstract class AbstractPage {
    protected final WebDriver driver;

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
	
//    protected void takeScreenShot(){
//        driver.getScreenshotAs(OutputType.BASE64);
//    }
}