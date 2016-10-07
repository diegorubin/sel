package lang.sel.commons.operators.logic;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;
import lang.sel.interfaces.UnaryOperator;

/**
 * Not operator
 *
 * @author diegorubin
 */
@Operator("NOT")
public class NotOperator extends OverloadOperator implements UnaryOperator {
  @Override
  public OperationResult execute(final OperatorArgument argument) {
    return execute(argument, "operatorNot");
  }
}
