package com.caffeineowl.googlemaps.automation.simple;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.caffeineowl.googlemaps.automation.AutomationErrorException;

public class AutomationActions {
  static public String gMapsStartPoint="https://maps.google.com";
  
  static public void resetToStart(WebDriver drv) {
    drv.navigate().to(AutomationActions.gMapsStartPoint);
  }
  
  static public void getToNavigationDirections(WebDriver driver) 
  throws AutomationErrorException {
    WebElement dirBtn=WebElemsLocator.getDirectionsBtn(driver);
    dirBtn.click();
  }
  
  static public void setTravelMode(WebDriver driver, TravelMode mode) {
    WebElement btn=WebElemsLocator.getTravelModeBtn(driver, mode);
    btn.click();
  }
  
  static public void setStartingPoint(WebDriver driver, String startingPoint)
  throws AutomationErrorException {
    WebElement input=WebElemsLocator.getStartingPointInput(driver);
    input.clear();
    if(null!=startingPoint) {
      input.sendKeys(startingPoint);
    }
  }
  
  static public void setDestination(WebDriver driver, String destination)
  throws AutomationErrorException {
    WebElement input=WebElemsLocator.getDestinationInput(driver);
    input.clear();
    if(null!=destination) {
      input.sendKeys(destination);
    }
  }
}
