package lang.sel.interfaces;

/**
 * Operation Result
 * <p>
 * Interface used in all classes that represents a type of operation result.
 *
 * @author diegorubin
 */
public interface OperationResult<T> {

  /**
   * Set the content of result
   *
   * @param result the content of operation result.
   */
  void setResult(T result);

  /**
   * Return the content of result
   *
   * @return the content of result
   */
  T getResult();

  /**
   * Convert the result to argument
   *
   * @return operator argument with operation result content
   */
  OperatorArgument toOperatorArgument();
}
