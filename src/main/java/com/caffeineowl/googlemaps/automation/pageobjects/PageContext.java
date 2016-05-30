package com.caffeineowl.googlemaps.automation.pageobjects;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.caffeineowl.googlemaps.automation.AutomationErrorException;
import com.caffeineowl.googlemaps.automation.AutomationErrorException.ErrorKind;

/**
 * <p>Abstract class to pack the idea of "encapsulating and protecting the UI elements
 * and exposing only the automation logic".</p>
 * <p>Shortcomings:<ol>
 * <li> only a "wrapping" against the {@link WebDriver}, with a minimalistic
 * identification of the "context" (by the use of a {@link PageEnum}
 * {@linkplain #getPage() method} to designate it).</li>
 * <li>missing the "site map" concept to handle the navigation between pages.
 * This is on purpose (focus was to explore the specific of 
 * "page objects" rather than go full throttle in a limited time), however the
 * "place holder for extensions" (and their need) is signaled by the
 * {@link #navigateTo(PageEnum)} method.</li>
 * </ol>
 * The class also provides utility methods for the general techniques of finding
 * UI elements (the protected {@link #waitForElementClickable(By, long, String)},
 * {@link #waitForElementListVisible(By, long, String)} instance methods and
 * the static {@link #waitForElementClickable(WebDriver, By, long, String)}
 * {@link #waitForElementListVisible(WebDriver, By, long, String)} ones).
 * 
 * @author acolomitchi
 *
 */
abstract public class PageContext {
  static private final int maxWaitSecs=30;
  
  static protected WebDriver defaultWebDriver=null;
  
  static public WebDriver getDefaultWebDriver() {
    return PageContext.defaultWebDriver;
  }
  
  static public void setDefaultWebDriver(WebDriver driver) {
    PageContext.defaultWebDriver=driver;
  }

  protected WebDriver driver;
  
  protected PageContext(WebDriver driver) {
    this.driver=driver;
  }
  
  protected WebDriver getDriver()
  throws AutomationErrorException {
    return PageContext.driver(this.driver);
  }
  
  static protected WebDriver driver(WebDriver driver) {
    WebDriver toRet=driver;
    if(null==toRet) {
      toRet=PageContext.getDefaultWebDriver();
    }
    if(null==driver) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No WebDriver available");
    }
    return toRet;
  }
  
  /**
   * Simple (simplistic) handling of the context of a "UI page".
   * @return
   */
  abstract public PageEnum getPage();
  
  /**
   * Needs to implement a verification for "am I in the correct context? 
   * Are all the UI elements that I need presetnt?"
   * @throws AutomationErrorException
   */
  abstract public void checkContextElements()
  throws AutomationErrorException;
  
  /**
   * <p>This is where a SiteMap class may come handy;
   * I mean&hellip; rather than returning void, maybe it's the SiteMap class to handle various pages via
   * a method called <tt>attachPage</tt> which would automatically {@link checkContextElements()}</p>
   * But this may be a suboptimal design! 
   * Rationale: we are not handling simple pages, but states/transitions here;
   * a proper approach would be to describe the states and possible transitions.
   * Then, ask the test environ to travel all and any edge of the graph for 100%
   * coverage. Eh, one can only dream!
   */
  abstract public void navigateTo(PageEnum page);

  /**
   * TODO doc me
   * @param condition
   * @param secsToWait
   * @param msgIfTimeout
   * @return
   * @throws AutomationErrorException
   */
  protected WebElement waitForElementClickable(
    By condition,
    long secsToWait, String msgIfTimeout
  ) 
  throws AutomationErrorException {
    return PageContext.waitForElementClickable(
      this.getDriver(), condition, 
      secsToWait, msgIfTimeout+" (in the context of a "+this.getPage()+" page)"
    );
  }
  /**
   * TODO doc me
   * @param driver
   * @param condition
   * @param secsToWait
   * @param msgIfTimeout
   * @return
   * @throws AutomationErrorException
   */
  static protected WebElement waitForElementClickable(
    WebDriver driver, By condition,
    long secsToWait, String msgIfTimeout
  ) 
  throws AutomationErrorException {
    driver=PageContext.driver(driver);
    if(null==condition) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No condition specified to get a clickable Web element");
    }
    if(secsToWait<=0) {
      secsToWait=PageContext.maxWaitSecs;
    }
    WebElement toRet=null;
    WebDriverWait wait= new WebDriverWait(driver, secsToWait);
    wait.pollingEvery(1, TimeUnit.SECONDS);
    try {
      toRet=wait.until(ExpectedConditions.elementToBeClickable(condition));
    }
    catch(TimeoutException | NoSuchElementException e) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, msgIfTimeout, e);
    }
    return toRet;
  }

  /** 
   * TODO doc me
   * @param condition
   * @param secsToWait
   * @param msgIfTimeout
   * @return
   */
  protected List<WebElement> waitForElementListVisible(
    By condition,
    long secsToWait, String msgIfTimeout
  ) {
    return PageContext.waitForElementListVisible(
      this.getDriver(), 
      condition, secsToWait,
      msgIfTimeout+" (in the context of a "+this.getPage()+" page)"
    );
  }
  
  /**
   * TODO doc me
   * @param driver
   * @param condition
   * @param secsToWait
   * @param msgIfTimeout
   * @return
   * @throws AutomationErrorException
   */
  static protected List<WebElement> waitForElementListVisible(
    WebDriver driver, By condition,
    long secsToWait, String msgIfTimeout
  ) 
  throws AutomationErrorException {
    driver=PageContext.driver(driver);
    if(null==condition) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No condition specified to get a clickable Web element");
    }
    if(secsToWait<=0) {
      secsToWait=PageContext.maxWaitSecs; // ... "magic constant", huh?
    }
    List<WebElement> toRet=null;
    WebDriverWait wait= new WebDriverWait(driver, secsToWait);
    wait.pollingEvery(1, TimeUnit.SECONDS);
    try {
      toRet=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(condition));
    }
    catch(TimeoutException | NoSuchElementException e) {
      // FIXME as there are heaps of other selenium exception, perhaps matching the
      // exception className against "org.openqa.selenium" prefix would be a better
      // way to go
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, msgIfTimeout, e);
    }
    return toRet;
  }
}
