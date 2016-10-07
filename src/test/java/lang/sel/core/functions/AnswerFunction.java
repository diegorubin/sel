package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.results.IntegerResult;
import lang.sel.interfaces.AbstractFunction;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * @author diegorubin
 */
@Function(value = "answer", numberOfArguments = "0")
public class AnswerFunction extends AbstractFunction {
  @Override
  public OperationResult execute(OperatorArgument... args) {
    return new IntegerResult(42L);
  }
}
