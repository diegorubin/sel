package lang.sel.exceptions;

/**
 * Expression parser error
 * <p>
 * This exceptions is thrown when expression is invalid.
 *
 * @author diegorubin
 */
public class SelParserException extends RuntimeException {

  /**
   * Set the message of exception.
   *
   * @param message the message to showed on log
   */
  public SelParserException(final String message) {
    super(message);
  }
}
