package lang.sel.interfaces;

/**
 * Unary Operator Interface
 * <p>
 * Represents an unary operator. In parser unary operator is called when this pattern is found.
 * <p>
 * OPERATOR argument.
 *
 * @author diegorubin
 */
public interface UnaryOperator extends Operator {

  /**
   * Execute the operator logic
   *
   * @param argument the argument for unary operator
   *
   * @return the operation result
   */
  OperationResult execute(OperatorArgument argument);
}
