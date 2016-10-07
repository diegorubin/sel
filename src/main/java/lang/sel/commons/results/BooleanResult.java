package lang.sel.commons.results;

import lang.sel.core.Keyword;

/**
 * Boolean Result
 *
 * @author diegorubin
 */
public class BooleanResult extends TypedResult<Boolean> {

  /**
   * Create a operation result with boolean as content
   *
   * @param value the boolean result
   */
  public BooleanResult(Boolean value) {
    super(value, Keyword.BOOLEAN);
  }
}
