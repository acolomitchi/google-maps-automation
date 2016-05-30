/**
 * <p>Simple approach to automation to mitigate the effects of a product with evolving UI:
 * separate the "presentation layer decoding" from the "automation layer".
 * In this simple approach, only two main classes are used
 * ({@link WebElemsLocator} - contains the "UI translation" logic - and 
 * {@link AutomationActions} - contains the automation logic). Granted, this simple
 * approach makes use of static methods only (transforming the UI in
 * a "C-style API/SDK", but with no actual OO elements).</p>
 * <p>Albeit simple, the advantages are immediate:<ol>
 * <li>the UI is allowed to evolve - as long as the "signature of the SDK functions"
 * doesn't change, the upper automation logic stays the same. It is only the
 * "UI translation logic" which will require modifications</li>
 * <li>as the changes in the automation code imposed by the UI evolution are
 * encapsulated in a small number of places, it is in these places where a
 * "mock objects" can be grafted in (and gradually eliminated).
 * Thus, this is one approach which makes Test Driven Development possible
 * (at least, in theory).</li>
 * <li>the calling automation logic is suddenly easier to understand, with methods
 * bearing more "verb-like" names. A step closer to
 * <a href="https://en.wikipedia.org/wiki/Literate_programming">"literate programming"</a> or
 * <a href="https://en.wikipedia.org/wiki/Behavior-driven_development">behaviour driven programming</a>.
 * Read the {@link App#main} method and you'll notice the expected behaviour 
 * becomes almost clear without further documentation.</li>
 * </ol></p>
 * Disadvantages - mainly derived from the simplicity of the approach - all the automation elements are
 * hobbled together:<ol>
 * <li>which complex projects, it is likely the {@link WebElemsLocator} and {@link AutomationActions}
 * will overgrow their utility, imposing longer time to find the relevant methods</li>
 * <li>a small number of classes also puts a limit on the number people able to contribute to
 * the automation code (in spite of "code merging" support offered by the modern code management systems).
 * This become even more upsetting it the product evolves using its own line of versions
 * in a quick succession (e.g. agile development) </li>
 * </ol>
 */
package com.caffeineowl.googlemaps.automation.simple;