package lang.sel.commons.operators.logic;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.interfaces.BinaryOperator;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * AND Operator
 * <p>
 * Usage BooleanOperator AND BooleanOperator
 *
 * @author diegorubin
 */
@Operator("AND")
public class AndOperator extends OverloadOperator implements BinaryOperator {

  @Override
  public OperationResult execute(final OperatorArgument arg1, final OperatorArgument arg2) {
    return execute(arg1, arg2, "operatorAnd");
  }
}

