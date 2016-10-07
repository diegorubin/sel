package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.results.StringResult;
import lang.sel.core.Keyword;
import lang.sel.interfaces.AbstractFunction;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * @author diegorubin
 */
@Function("sum")
public class SumFunction extends AbstractFunction {
  @Override
  public OperationResult execute(OperatorArgument... args) {
    Double dbl1 = getContent(args[0]);
    Double dbl2 = getContent(args[1]);
    Double result = dbl1 + dbl2;
    return new StringResult(result.toString());
  }

  public Double getContent(OperatorArgument argument) {
    if (argument.getArgumentType() == Keyword.FLOAT) {
      return (Double) argument.getContent();
    }
    return new Double(((Long) argument.getContent()));
  }
}
