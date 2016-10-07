package lang.sel.commons.operators.math;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.interfaces.BinaryOperator;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * Performs addition of two numbers
 * <p>
 * Used with number PLUS number
 *
 * @author diegorubin
 */
@Operator("PLUS")
public class PlusOperator extends OverloadOperator implements BinaryOperator {

  @Override
  public OperationResult execute(final OperatorArgument arg1, final OperatorArgument arg2) {
    return execute(arg1, arg2, "operatorPlus");
  }
}
