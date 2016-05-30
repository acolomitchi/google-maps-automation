
/**
 * <p>A more evolved approach: isolate the UI translation code in classes dealing 
 * with the main UI "pages" (with Web applications, they may be pages, even if the
 * use of Ajax may blur the line of distinction between pages quite a lot). 
 * The same are used to expose the "automation logic" as public methods, the "UI translation"
 * logic being implemented as protected ones.</p>
 * <p>The current implementation is incomplete, as there is no implementationto the
 * "UI map/sitemap" class - a class to unify all the pages and transitions between 
 * them under a single roof.<br>This shortcoming has to do with the fact that the 
 * most comprehensive approach - one which would be able to cover a mapping between 
 * "use cases" in the "Functional specification" document and the automation code -
 * should not deal with "pages" 
 * but rather conceptualize all as "state-transition" - but this is beyond the scope of
 * this exercise.</p>
 * 
 * 
 */
package com.caffeineowl.googlemaps.automation.pageobjects;