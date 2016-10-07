package lang.sel.commons.constants.logic;

import lang.sel.commons.results.BooleanResult;
import lang.sel.interfaces.SimpleConstant;

/**
 * False Constant
 * <p>
 * Returns BooleanResult with false in value.
 * <p>
 * The class that represents the constant must implement the interface SimpleConstant
 *
 * @author diegorubin
 */
public class FalseConstant implements SimpleConstant<BooleanResult> {
  @Override
  public BooleanResult getValue() {
    return new BooleanResult(false);
  }
}
