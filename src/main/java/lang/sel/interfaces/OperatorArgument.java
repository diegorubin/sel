package lang.sel.interfaces;

import lang.sel.core.Keyword;

/**
 * Operator Argument
 * <p>
 * All functions and operators arguments should be implements this interface.
 *
 * @author diegorubin
 */
public interface OperatorArgument<T> {

  /**
   * Set the content of argument.
   *
   * @param content the content of argument
   */
  void setContent(T content);

  /**
   * Return the content of argument
   *
   * @return the content of argument
   */
  T getContent();

  /**
   * Return the semantic type of argument
   *
   * @return the type of argument
   */
  Keyword getArgumentType();

}
