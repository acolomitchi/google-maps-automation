package com.caffeineowl.googlemaps.automation;

/**
 * "Junction point" in which all the automation errors "flow" - attempting a
 * unification of all error condition to be communicated to the calling code.
 * @author acolomitchi
 *
 */
public class AutomationErrorException
extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public enum ErrorKind {
    /** Automation task did not finish on time */
    TIMEOUT,
    /** 
     * Unexpected conditions by the test logic (exceptions being thrown unexpectedly)
     * From failing automation logic assumption, to missing preconditions, to
     * unavailable needed infrastructure (config files, servers, etc).
     */
    AUTOMATION_LOGIC,
    /**
     * Test case "positive" failure - the test case logic detected some
     * misbehaviour in the product
     */
    PRODUCT_LOGIC
  }
  
  protected ErrorKind errorType;
  
  public AutomationErrorException(String message) {
    this(ErrorKind.PRODUCT_LOGIC, message, null);
  }
  public AutomationErrorException(ErrorKind kind, String message) {
    this(kind, message, null);
  }

  public AutomationErrorException(String message, Throwable cause) {
    this(ErrorKind.AUTOMATION_LOGIC, message, cause);
  }
  public AutomationErrorException(ErrorKind kind, String message, Throwable cause) {
    super(message, cause);
    if(null==kind) {
      kind=ErrorKind.AUTOMATION_LOGIC;
    }
    this.errorType=kind;
  }

  public ErrorKind getErrorType() {
    return this.errorType;
  }
  
  
}
