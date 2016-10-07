package lang.sel.commons.operators.math;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.interfaces.BinaryOperator;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * Less Than Operator
 *
 * @author diegorubin
 */
@Operator("LT")
public class LessThanOperator extends OverloadOperator implements BinaryOperator {

  /**
   * Implements the less than operator (&lt;)
   *
   * @param arg1 the argument in double value
   * @param arg2 the argument in double value
   *
   * @return the operation result as boolean
   */
  @Override
  public OperationResult execute(final OperatorArgument arg1, final OperatorArgument arg2) {
    return execute(arg1, arg2, "operatorLt");
  }

}
