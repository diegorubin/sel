package lang.sel.commons.results;

import lang.sel.core.Keyword;

/**
 * String Result
 *
 * @author diegorubin
 */
public class StringResult extends TypedResult<String> {

  /**
   * Create a operation result with string as content
   *
   * @param value the string result
   */
  public StringResult(String value) {
    super(value, Keyword.STRING);
  }

}
