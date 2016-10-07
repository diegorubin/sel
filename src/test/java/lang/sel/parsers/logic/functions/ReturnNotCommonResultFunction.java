package lang.sel.parsers.logic.functions;

import lang.sel.interfaces.AbstractFunction;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;
import lang.sel.parsers.logic.results.NotCommonResult;

/**
 * Used to test when operator argument not has respondTo for AND operator
 * @author diegorubin
 */
public class ReturnNotCommonResultFunction extends AbstractFunction {
  @Override
  public OperationResult execute(OperatorArgument... args) {
    return new NotCommonResult();
  }
}
