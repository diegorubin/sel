package lang.sel.commons.constants.logic;

import lang.sel.annotations.Constant;
import lang.sel.commons.results.BooleanResult;
import lang.sel.interfaces.SimpleConstant;

/**
 * True Constant
 *
 * Returns BooleanResult with true in value.
 *
 * @author diegorubin
 */
@Constant("TRUE")
public class TrueConstant implements SimpleConstant<BooleanResult> {
  @Override
  public BooleanResult getValue() {
    return new BooleanResult(true);
  }
}
