# google-maps-automation
Brief exercise in google maps automation using selenium

The com.caffeineowl.googlemaps.automation.simple package - a 
(rather simplistic) approach of decoupling the decoding of the presentation layer (UI) from the automation logic.
Static methods only, but the results become visible - the driving logic (the "what to do") needs to know very little of the
automation logic

The com.caffeineowl.googlemaps.automation.pageobjects - a "pageobjects" approach. The "UI decoding" is
encapsulated by specific "Page" objects, which hide the "UI decoding" and expose only the
"automation logic".
