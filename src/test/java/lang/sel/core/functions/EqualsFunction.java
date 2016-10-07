package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.results.BooleanResult;
import lang.sel.interfaces.AbstractFunction;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * @author diegorubin
 */
@Function("equals")
public class EqualsFunction extends AbstractFunction {
  @Override
  public OperationResult execute(OperatorArgument... args) {

    return new BooleanResult(((String) args[0].getContent()).equals((String) args[1].getContent()));
  }
}
