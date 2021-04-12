package lang.sel.interfaces;

import lang.sel.commons.results.TypedResult;

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
    TypedResult execute(TypedResult argument);
}
