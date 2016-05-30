package com.caffeineowl.googlemaps.automation.pageobjects.ctxes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.caffeineowl.googlemaps.automation.AutomationErrorException;
import com.caffeineowl.googlemaps.automation.AutomationErrorException.ErrorKind;
import com.caffeineowl.googlemaps.automation.pageobjects.PageContext;
import com.caffeineowl.googlemaps.automation.pageobjects.PageEnum;

/**
 * The "automation utility class" for the "maps.google.com" landing page.
 * Contains the (incomplete) UI element locators for the as protected methods and 
 * the "automation logic" as public elements. 
 */
public class HomePage 
extends PageContext {
  static private By dirBtnLocator=By.id("searchbox-directions");

  public HomePage(WebDriver driver) {
    super(driver);
  }

  @Override
  public PageEnum getPage() {
    return PageEnum.HOME_PAGE;
  }
  
  public WebElement getDirectionsBtn()
    throws AutomationErrorException {
      return PageContext.waitForElementClickable(
        this.getDriver(), HomePage.dirBtnLocator, 
        30, "Missing 'Directions' button"
      );
    } 
  
  @Override
  public void checkContextElements() 
  throws AutomationErrorException {
    this.getDirectionsBtn();
  }
  
  public void navigateTo(PageEnum page) 
  throws AutomationErrorException {
    // this is where a SiteMap class may come handy
    // I mean.. rather than returning void, maybe it's the SiteMap class to handle various pages and state/transitions?
    switch(page) {
      case HOME_PAGE:
        break; // we are already here
      case DIRECTIONS_PAGE:
        this.getDirectionsBtn().click();
        break;
      default:
        throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Unknown/unhandled destination page to navigate to");
    }
  }
}
