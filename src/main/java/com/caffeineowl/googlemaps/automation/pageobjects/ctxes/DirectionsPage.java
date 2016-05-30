package com.caffeineowl.googlemaps.automation.pageobjects.ctxes;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.caffeineowl.googlemaps.automation.AutomationErrorException;
import com.caffeineowl.googlemaps.automation.AutomationErrorException.ErrorKind;
import com.caffeineowl.googlemaps.automation.pageobjects.PageContext;
import com.caffeineowl.googlemaps.automation.pageobjects.PageEnum;

/**
 * <p>The "automation utility class" for the "maps.google.com" to cover the "get travel directions"
 * page.</p>
 * <p>Contains the UI element locators for the as protected methods and 
 * the "automation logic" as public elements. Currently still incomplete (but
 * a slightly larger coverage of the elements and logic than the
 * {@link HomePage}:<ol>
 * <li>the main "search scenario" is covered (at the {@linkplain RouteSummary route summary} level only)
 * fir success</li>
 * <li>one limit case is covered as well: of one start/dest point is correct, the other one is a 
 * wrong value (the place does not exists)</li>
 * </ol>
 * Lots of other things still to cover: the details of the listed routes, traveling by way points,
 * limit cases in which both the start/destination are incorrect, destination unreacheable
 * from the source by chosen travel mode, etc.</p>
 */
public class DirectionsPage 
extends PageContext {
  static private By byCarBtnLocator=By.xpath("//div[contains(@class,'travel-mode') and @data-travel_mode='0']/button");
  static private By byTramBtnLocator=By.xpath("//div[contains(@class,'travel-mode') and @data-travel_mode='3']/button");
  static private By byFootBtnLocator=By.xpath("//div[contains(@class,'travel-mode') and @data-travel_mode='2']/button");
  static private By srcInputLocator=By.xpath("//div[@id='directions-searchbox-0']//input[@class='tactile-searchbox-input']");
  static private By destInputLocator=By.xpath("//div[@id='directions-searchbox-1']//input[@class='tactile-searchbox-input']");
  static private By closeDirectionsLocator=By.xpath("//div[@class='widget-directions-travel-mode-switcher-container']//div[@class='widget-directions-close']");

  static private By routesSimpleDetailsListLocator=By.xpath("//div[@class='widget-pane-section-directions-trip-description']/div[@style!='display:none']");
  static private By routeViaLocator=By.xpath(".//h1[@class='widget-pane-section-directions-trip-title']//span");
  static private By routeDurationLocator=By.xpath(".//div[contains(@class, 'widget-pane-section-directions-trip-duration')]/span[not(@style)]");
  static private By routeDistanceLocator=By.xpath(".//div[contains(@class, 'widget-pane-section-directions-trip-secondary-text')]/div");
  
  static private By routeConfusingWaypoint=By.xpath("//div[@class='widget-directions-suggest-container']//div[@class='widget-directions-waypoint-status' and not(@style)]");
  /**
   * The enum is now internal to the {@link DirectionsPage} object, as it is not used 
   * application wide.
   * @author acolomitchi
   */
  public enum TravelMode {
    BY_CAR,
    BY_PUBTRNS,
    BY_FOOT;
    
    public String toString() {
      String toRet="'Travel by ";
      switch(this) {
        case BY_CAR:
          toRet+="car'";
          break;
        case BY_PUBTRNS:
          toRet+="public transport'";
          break;
        case BY_FOOT:
          toRet+="foot'";
          break;
        default: // or throw a AutomationErrorException?
          toRet+=" ???'";
          break;
      }
      return toRet;
    }
  }
  
  /**
   * POJO class packing the details of a proposed route.
   * @author acolomitchi
   *
   */
  static public class RouteSummary {
    protected String title;
    protected String travelTime;
    protected String distance;

    public RouteSummary(String title, String travelTime, String distance) {
      super();
      this.title = title;
      this.travelTime = travelTime;
      this.distance = distance;
    }

    public String getVia() {
      return this.title;
    }

    protected void setVia(String title) {
      this.title = title;
    }

    public String getTravelTime() {
      return this.travelTime;
    }

    protected void setTravelTime(String travelTime) {
      this.travelTime = travelTime;
    }

    public String getDistance() {
      return this.distance;
    }

    protected void setDistance(String distance) {
      this.distance = distance;
    }
  }
  
  public DirectionsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public PageEnum getPage() {
    return PageEnum.DIRECTIONS_PAGE;
  }

  @Override
  public void checkContextElements() 
  throws AutomationErrorException {
    this.getDestinationInput();
    this.getStartingPointInput();
    this.getTravelModeBtn(TravelMode.BY_CAR);
    this.getTravelModeBtn(TravelMode.BY_PUBTRNS);
    this.getTravelModeBtn(TravelMode.BY_FOOT);
    this.getCloseDirectionsBtn();
  }
  
  protected WebElement getTravelModeBtn(TravelMode mode)
  throws AutomationErrorException {
    if(null==mode) {
      mode=TravelMode.BY_CAR; // or throw a AutomationErrorException?
    }
    String msg="For "+mode+" button";
    By locator=null;
    switch(mode) {
      case BY_CAR:
        locator=DirectionsPage.byCarBtnLocator;
        break;
      case BY_PUBTRNS:
        locator=DirectionsPage.byTramBtnLocator;
        break;
      case BY_FOOT:
        locator=DirectionsPage.byFootBtnLocator;
        break;
      default:
        throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Unhadled transporation mode: "+mode);
    }
    return this.waitForElementClickable(
      locator, 30, msg
    );
  }
  
  protected WebElement getStartingPointInput()
  throws AutomationErrorException {
    return this.waitForElementClickable(
      DirectionsPage.srcInputLocator, 
      30, "For 'Starting point' input field"
    );
  }
  
  protected WebElement getDestinationInput() 
  throws AutomationErrorException  {
    return this.waitForElementClickable(
      DirectionsPage.destInputLocator, 
      30, "For 'Destination point' input field"
    );
  }
  
  protected WebElement getCloseDirectionsBtn() {
    return this.waitForElementClickable(
      DirectionsPage.closeDirectionsLocator, 
      30, "For 'Close directions' button"
    );
  }
  
  public void navigateTo(PageEnum page) {
    switch(page) {
      case HOME_PAGE:
        this.getCloseDirectionsBtn().click();
      case DIRECTIONS_PAGE:  // we are already here
        break;
      default:
        throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Unknown/unhandled destination page to navigate to");
    }
  }

  public void setStartingPoint(String startingPoint)
  throws AutomationErrorException {
    WebElement input=this.getStartingPointInput();
    input.clear();
    if(null!=startingPoint) {
      input.sendKeys(startingPoint);
    }
  }

  public void setDestination(String destination)
  throws AutomationErrorException {
    WebElement input=this.getDestinationInput();
    input.clear();
    if(null!=destination) {
      input.sendKeys(destination);
    }
  }
  
  public void setTravelMode(TravelMode travelMode)
  throws AutomationErrorException {
    WebElement travelModeBtn=this.getTravelModeBtn(travelMode);
    travelModeBtn.click();
  }
  
  public List<RouteSummary> readRoutesSimple() {
    List<WebElement> routeSummaryList=this.waitForElementListVisible(
      DirectionsPage.routesSimpleDetailsListLocator,
      -1, "List of route summaries"
    );
    // Java8 here
    List<RouteSummary> toRet=null;
    if(null!=routeSummaryList) {
      toRet=routeSummaryList.stream().map(
        e-> {
          try {
            String via=e.findElement(DirectionsPage.routeViaLocator).getText();
            String distance=e.findElement(DirectionsPage.routeDistanceLocator).getText();
            String duration=e.findElement(DirectionsPage.routeDurationLocator).getText();
            return new RouteSummary(via, duration, distance);
          }
          catch(NoSuchElementException ex) {
            throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Element not found", ex);
          }
        }
      ).collect(Collectors.toList());
    }
    if(null==toRet) {
      toRet=Collections.emptyList();
    }
    return toRet;
  }
  
  /**
   * TODO Maybe a list of them?
   * @return
   */
  public String readConfusingLocationsMessage() {
    List<WebElement> confusingWaypointList=this.waitForElementListVisible(
      DirectionsPage.routeConfusingWaypoint,
      -1, "List of confusing waypoints"
    );
    final StringBuilder ret=new StringBuilder("");
    confusingWaypointList.stream().forEach(
      e->{ ret.append(e.getText()); }
    );
    return ret.toString();
  }
}
