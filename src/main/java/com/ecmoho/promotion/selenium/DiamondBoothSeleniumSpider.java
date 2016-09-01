package com.ecmoho.promotion.selenium;

import com.ecmoho.base.selenium.SeleniumSpider;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zhangjunrui on 2016/7/8.
 */
@Component("diamondBoothSeleniumSpider")
public class DiamondBoothSeleniumSpider extends SeleniumSpider {
    public static void main(String[] args) {
        DiamondBoothSeleniumSpider diamondBoothSeleniumSpider=new DiamondBoothSeleniumSpider();
        Map<String,String> loginUrlmap=new HashMap<String, String>();
        loginUrlmap.put("login_url","http://zuanshi.taobao.com/index.html");
        loginUrlmap.put("red_url","http://zuanshi.taobao.com/indexdp.html");
        String refferCookie = diamondBoothSeleniumSpider.getCookie(loginUrlmap);
        System.out.println(refferCookie);
    }
    @Override
    public void loginPage(WebDriver webDriver, Map<String, String> spider) {
//        String login_name = "哈药官方旗舰店";
//        String password = "yiheng@hayao%*";
        String login_name = spider.get("login_name");
        String password = spider.get("password");
        String login_name_id = "TPL_username_1";
        String password_id = "TPL_password_1";
        String login_button = "J_SubmitStatic";
        String login_url = spider.get("login_url");
        String red_url = spider.get("red_url");


        try {
            int i=0;
            int times=4;
            webDriver.get(login_url);
            Thread.sleep(10040L);
            while (i<times){
                 if (doesWebElementExistBySelector(webDriver,By.tagName("iframe"))){
                     WebElement frame = webDriver.findElement(By.tagName("iframe"));
                     webDriver.switchTo().frame(frame);
                     WebElement userElement = webDriver.findElement(By.id(login_name_id));
                     userElement.clear();
                     userElement.sendKeys(login_name);
                     userElement.click();
                     Thread.sleep(3040L);
                     WebElement passElement = webDriver.findElement(By.id(password_id));
                     passElement.clear();
                     passElement.sendKeys(password);
                     passElement.click();
                     Thread.sleep(3000L);

                     WebElement moveElement=webDriver.findElement(By.id("nc_1_n1z"));
                     if(doesWebElementExist(moveElement)){
                         Actions action=new Actions(webDriver);
                         // action.dragAndDrop(target,260);
                         action.click(webDriver.findElement(By.id("nc_1_n1z")));
                         action.dragAndDropBy(moveElement,260,0).build().perform();
                         Thread.sleep(15000L);
                     }
                     WebElement ensureElement=webDriver.findElement(By.id("nc_1__btn_2"));
                     if(!doesWebElementExist(ensureElement)){
                         webDriver.findElement(By.id(login_button)).click();
                         Thread.sleep(10000L);
                     }
                     webDriver.switchTo().defaultContent();
//            WebElement successElement=webDriver.findElement(By.id("brix_265"));
                     if(doesWebElementExistBySelector(webDriver,By.className("logout-btn"))){
                         webDriver.get(red_url);
                     }
                     break;
                 }else {
                     if(i<=times){
                         i++;
                         webDriver.get(login_url);
                         Thread.sleep(10040L);
                     }else{
                         break;
                     }
                 }
            }
//            webDriver.switchTo().defaultContent();
        } catch (InterruptedException e1) {
            webDriver.quit();
            e1.printStackTrace();
        }
    }

}
