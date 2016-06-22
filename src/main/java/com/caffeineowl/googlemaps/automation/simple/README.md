The principle - split driving the UI into two parts:
a. a part that depends on the UI (and/or other elements that pertain of the "How to do" or "How it looks" - like localization/i18n)
b. a part that expose the "possible actions logic" as an API/SDK - the "what to do"
them make you automation logic call only in the "What to do" layer.

Advantages: 
* whenever GUI (or language support, or whatever way of interacting with the application) changes, one will need to modify the "How to do it" layer, possibly the "What to do" logic (in the size interfacing/using with the "How to do it"), but the Automation logic (supposed to be the biggest part of your code, like your test suite) will remain intact
* if the "how to do it" is subject to language variability, one can implement multiple "faces" in separate classes/libraries and load (at runtime) the one that's appropriate to the specific language.

Disadvantages:
* simple/simplistic for complex products - globing all the GUI elements in single class and the "actions logic" in just another will result in ballooning classes
* the actual problem: no support for the idea of the "context" - putting it in simple words, no way of getting an answer to "Is the application in a state in which I could use to do want I want"?

The files:
- the WebElemsLocator class contains the GUI locators ("How to do it") - should never be use as such by the automation code
- the AutomationActions class contains the "application SDK" - the automation code should call into it to implement the "sequence of functional operations"
- the App class (the main method) implements an example of the "automation code"