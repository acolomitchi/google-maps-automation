package com.caffeineowl.googlemaps.automation.simple;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.caffeineowl.googlemaps.automation.AutomationErrorException;
import com.caffeineowl.googlemaps.automation.AutomationErrorException.ErrorKind;

public class WebElemsLocator {
  static private By dirBtnLocator=By.id("searchbox-directions");
  static private By byCarBtnLocator=By.xpath("//div[contains(@class,'travel-mode') and @data-travel_mode='0']/button");
  static private By byTramBtnLocator=By.xpath("//div[contains(@class,'travel-mode') and @data-travel_mode='3']/button");
  static private By byFootBtnLocator=By.xpath("//div[contains(@class,'travel-mode') and @data-travel_mode='2']/button");
  static private By srcInputLocator=By.xpath("//div[@id='directions-searchbox-0']//input[@class='tactile-searchbox-input']");
  static private By destInputLocator=By.xpath("//div[@id='directions-searchbox-1']//input[@class='tactile-searchbox-input']");
  
  static protected WebDriver defaultWebDriver=null;
  
  static public WebDriver getDefaultWebDriver() {
    return WebElemsLocator.defaultWebDriver;
  }
  
  static public void setDefaultWebDriver(WebDriver driver) {
    WebElemsLocator.defaultWebDriver=driver;
  }
  
  static public WebElement waitForElementClickable(
    WebDriver driver, By condition,
    long secsToWait, String msgIfTimeout
  ) 
  throws AutomationErrorException {
    if(null==driver) {
      driver=WebElemsLocator.getDefaultWebDriver();
    }
    if(null==condition) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No condition specified to get a clickable Web element");
    }
    if(null==driver) { // no specific driver provided, no default available
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No WebDriver available");
    }
    if(secsToWait<=0) {
      secsToWait=30; // ... "magic constant", huh?
    }
    WebElement toRet=null;
    WebDriverWait wait= new WebDriverWait(driver, secsToWait);
    wait.pollingEvery(1, TimeUnit.SECONDS);
    try {
      toRet=wait.until(ExpectedConditions.elementToBeClickable(condition));
    }
    catch(TimeoutException e) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, msgIfTimeout, e);
    }
    return toRet;
  }
  

  
  static public WebElement getDirectionsBtn(WebDriver driver)
  throws AutomationErrorException {
    return WebElemsLocator.waitForElementClickable(
      driver, WebElemsLocator.dirBtnLocator, 
      30, "For 'Directions' button, assumed in the context of guard page"
    );
  }
  
  static public WebElement getTravelModeBtn(WebDriver driver, TravelMode mode)
  throws AutomationErrorException {
    if(null==mode) {
      mode=TravelMode.BY_CAR; // or throw a AutomationErrorException?
    }
    String msg="For "+mode+" button, , assumed in the context of 'Directions page";
    By locator=null;
    switch(mode) {
      case BY_CAR:
        locator=WebElemsLocator.byCarBtnLocator;
        break;
      case BY_PUBTRNS:
        locator=WebElemsLocator.byTramBtnLocator;
        break;
      case BY_FOOT:
        locator=WebElemsLocator.byFootBtnLocator;
        break;
      default:
        throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Unhadled transporation mode: "+mode);
    }
    return WebElemsLocator.waitForElementClickable(
      driver, locator, 
      30, msg
    );
  }
  
  static public WebElement getStartingPointInput(WebDriver driver)
  throws AutomationErrorException {
    return WebElemsLocator.waitForElementClickable(
      driver, WebElemsLocator.srcInputLocator, 
      30, "For 'Starting point' input field, assumed in the context of 'Directions' page"
    );
  }
  
  static public WebElement getDestinationInput(WebDriver driver) 
  throws AutomationErrorException  {
    return WebElemsLocator.waitForElementClickable(
      driver, WebElemsLocator.destInputLocator, 
      30, "For 'Destination point' input field, assumed in the context of 'Directions' page"
    );
  }
  
}
