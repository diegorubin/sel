package lang.sel.exceptions;

/**
 * Expression Semantic Exception
 * <p>
 * This exceptions is thrown when wrong data type is passed to an operator or function.
 * <p>
 * Example is when a string is used with NOT operator.
 *
 * @author diegorubin
 */
public class SelSemanticException extends RuntimeException {

  /**
   * Set the message of exception.
   *
   * @param message the message to showed on log
   */
  public SelSemanticException(final String message) {
    super(message);
  }
}
