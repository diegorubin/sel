package lang.sel.exceptions;

/**
 * Expression parser error
 * <p>
 * This exceptions is thrown when expression is invalid.
 *
 * @author diegorubin
 */
public class ExpressionParserException extends RuntimeException {

  /**
   * Set the message of exception.
   *
   * @param message the message to showed on log
   */
  public ExpressionParserException(final String message) {
    super(message);
  }
}
