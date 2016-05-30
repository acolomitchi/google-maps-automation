package com.caffeineowl.googlemaps.automation.pageobjects;

import java.io.File;
import java.util.List;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.caffeineowl.googlemaps.automation.AutomationErrorException;
import com.caffeineowl.googlemaps.automation.pageobjects.ctxes.DirectionsPage;
import com.caffeineowl.googlemaps.automation.pageobjects.ctxes.HomePage;
import com.caffeineowl.googlemaps.automation.pageobjects.ctxes.DirectionsPage.RouteSummary;
import com.caffeineowl.googlemaps.automation.pageobjects.ctxes.DirectionsPage.TravelMode;

public class App {
  static public void main(String[] args) {
    String home=System.getProperty("user.home");
    File profileDir=new File(home+File.separator+"deleteme"+File.separator+"googlemaps");
    profileDir.mkdirs();
    FirefoxDriver w=new FirefoxDriver(new FirefoxProfile(profileDir));
    
    w.get("https://google.com.au/maps");
    PageContext page=new HomePage(w);
    page.checkContextElements();
    page.navigateTo(PageEnum.DIRECTIONS_PAGE);
    
    // this doesn't look right - it's an element of the UI automation code
    // jumping out of something that should be a "behavior driven"
    // discourse expressed only in terms of "automation verbs"
    // (an incomplete mask showing fragments of the testing technology)
    // This is where a "SiteMap" class would be needed.
    DirectionsPage directions=new DirectionsPage(w);
    directions.checkContextElements();
    directions.setTravelMode(TravelMode.BY_CAR);
    directions.setStartingPoint("Eynesbury VIC");
    directions.setDestination("Moonambel VIC\n"); // \n will trigger the search
    List<RouteSummary> routes=null;
    try {
      routes=directions.readRoutesSimple();
      System.out.println("#routes: "+routes.size());
      routes.stream().forEach(e->{
        System.out.println("via:'"+e.getVia()+ "' dist:"+e.getDistance()+" time:"+e.getTravelTime());;
      });
    }
    catch(AutomationErrorException e) {
      e.printStackTrace(System.err);
      System.exit(1);
    }
    // expect a confusing location error
    directions.setStartingPoint("Eynesbury VIC");
    directions.setDestination("Moonambel NSW\n"); // \n will trigger the search
    System.out.println(directions.readConfusingLocationsMessage());
    // else some other type of content
  }
}
