A more advanced approach to "separation of the GUI driving from the automation logic".
Makes use of the "page object" concept, in which "PageObject" contains:
* the definition of GUI elements (the "How to do it" logic), but this definition is hidden from any external calling code - see all the protected methods starting with "get" 
* the public methods exposing different automation actions available within a certain context - usually formulated using verbs (e.g. setStartingPoint or readRoutes)

This supports better the idea of "application context" than the "simple approach" (in the src/main/java/com/caffeineowl/googlemaps/automation/simple/ dir), and actually makes possible checks like "Am I in the right place?" - see the "checkContextElements()" method 

In here:
* the PageContext (abstract) base class supports the "context of a page" concept (and implements some generic methods related with finding GUI elements using selenium). Together with the "PageEnum" will be the base on which all the specifc "page objects" will be derived from.
* the <tt>ctxes</tt> directory (package) contains two specific page objects implementations: the HomePage captures the "maps.google.com" page (not supported in full) and the DirectionsPage - (more "meatier" but still not a full support) implementation of the "Search for directions" page of "maps.google.com"
* the App class (the main method) - an example of automation logic (in which a list of routes between two locations are listed)

 