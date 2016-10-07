package lang.sel.commons.operators.logic;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.interfaces.BinaryOperator;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * Or operator
 *
 * @author diegorubin
 */
@Operator("OR")
public class OrOperator extends OverloadOperator implements BinaryOperator {

  @Override
  public OperationResult execute(final OperatorArgument arg1, final OperatorArgument arg2) {
    return execute(arg1, arg2, "operatorOr");
  }

}

