package lang.sel.commons.operators.math;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.interfaces.BinaryOperator;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * Greater Than Equals Operator
 *
 * @author diegorubin
 */
@Operator("GTE")
public class GreaterThanEqualsOperator extends OverloadOperator implements BinaryOperator {

  /**
   * Implements the greater than or equals operator (&gt;=)
   *
   * @param arg1 the argument in double value
   * @param arg2 the argument in double value
   *
   * @return the operation result as boolean
   */
  @Override
  public OperationResult execute(final OperatorArgument arg1, final OperatorArgument arg2) {
    return execute(arg1, arg2, "operatorGte");
  }

}
