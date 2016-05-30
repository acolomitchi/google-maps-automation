package com.caffeineowl.googlemaps.automation.simple;

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