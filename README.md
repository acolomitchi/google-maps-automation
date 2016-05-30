# google-maps-automation
Brief exercise in google maps automation using selenium

The com.caffeineowl.googlemaps.automation.simple package - a 
(rather simplistic) approach of decoupling the decoding of the presentation layer (UI) from the automation logic.
Static methods only, but the results become visible - the automation logic (the "what to do") needs to know very little of the
UI driving logic (the "how to do it"). Brings us one step close to "automating an API/SDK".

The com.caffeineowl.googlemaps.automation.pageobjects - a "pageobjects" approach. The "UI decoding" is
encapsulated by specific "Page" objects, which hide the "UI decoding" and expose only the
"automation logic".
