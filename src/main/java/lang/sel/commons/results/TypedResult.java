package lang.sel.commons.results;

import lang.sel.commons.arguments.CommonOperatorArgument;
import lang.sel.core.Keyword;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * Typed Result
 * <p>
 * Common logic to simple typed results, like boolean, integer, string, etc...
 *
 * @author diegorubin
 */
public abstract class TypedResult<T> implements OperationResult<T> {

  protected T result;
  protected Keyword type;

  /**
   * When a typed result is created it is necessary enter the type
   *
   * @param value the content
   * @param type  the type of content
   */
  public TypedResult(final T value, final Keyword type) {
    result = value;
    this.type = type;
  }

  /**
   * Set content of operator result
   *
   * @param result the content of operation result.
   */
  @Override
  public void setResult(final T result) {
    this.result = result;
  }

  /**
   * Return the content of operation result
   *
   * @return the content
   */
  @Override
  public T getResult() {
    return result;
  }

  /**
   * Convert typed result to common operator argument.
   *
   * @return result in common operator argument
   */
  @Override
  public OperatorArgument toOperatorArgument() {
    final OperatorArgument argument = new CommonOperatorArgument(type);
    argument.setContent(getResult());
    return argument;
  }
}
