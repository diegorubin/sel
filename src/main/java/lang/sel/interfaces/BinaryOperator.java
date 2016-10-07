package lang.sel.interfaces;

/**
 * Binary Operator Interface
 * <p>
 * Represents a binary operator. In parser binary operator is called when this pattern is found.
 * <p>
 * argument OPERATOR argument.
 *
 * @author diegorubin
 */
public interface BinaryOperator extends Operator {

  /**
   * Execute the binary operator logic.
   *
   * @param arg1 the first argument
   * @param arg2 the second argument
   *
   * @return the operation result
   */
  OperationResult execute(OperatorArgument arg1, OperatorArgument arg2);

}
