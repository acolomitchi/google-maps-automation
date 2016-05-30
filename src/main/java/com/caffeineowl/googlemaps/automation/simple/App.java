package com.caffeineowl.googlemaps.automation.simple;

import java.io.File;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.caffeineowl.googlemaps.automation.AutomationErrorException;

/**
 * Hello world!
 *
 */
public class App 
{
  public static void main( String[] args )
  {
    String home=System.getProperty("user.home");
    File profileDir=new File(home+File.separator+"deleteme"+File.separator+"googlemaps");
    profileDir.mkdirs();
    FirefoxDriver w=new FirefoxDriver(new FirefoxProfile(profileDir));
    try {
      AutomationActions.resetToStart(w);
      AutomationActions.getToNavigationDirections(w);
      AutomationActions.setTravelMode(w, TravelMode.BY_CAR);
      AutomationActions.setStartingPoint(w, "Eynesbury VIC");
      AutomationActions.setDestination(w, "Moonambel VIC\n");
    }
    catch(AutomationErrorException e) {
      // LOG it
      e.printStackTrace(System.err);
    }
  }
}
